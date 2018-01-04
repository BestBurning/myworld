package com.diyishuai.rpc.client;

import com.diyishuai.rpc.common.RpcDecoder;
import com.diyishuai.rpc.common.RpcEncode;
import com.diyishuai.rpc.common.RpcRequest;
import com.diyishuai.rpc.common.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class RpcClient extends SimpleChannelInboundHandler<RpcResponse>{
    public static Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private String host;
    private int port;
    private RpcResponse response;
    private final Object obj = new Object();

    public RpcResponse getResponse() {
        return response;
    }

    public void setResponse(RpcResponse response) {
        this.response = response;
    }

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RpcResponse send(RpcRequest request){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncode(RpcRequest.class))
                                    .addLast(new RpcDecoder(RpcResponse.class))
                                    .addLast(RpcClient.this);
                        }
                    }).option(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync();
            channelFuture.channel().writeAndFlush(request).sync();
            //用线程等待的方式决定是否关闭连接
            //其意义是：先再次阻塞，等待获取到服务端的返回后，被唤醒，从而关闭网络连接
            logger.debug("client start synchronized");
            synchronized (obj){
                obj.wait();
            }
            if (response != null){
                channelFuture.channel().closeFuture().sync();
            }
            return response;
        } catch (InterruptedException e) {
            response.setError(e);
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        logger.debug("channel will be flush response :" + rpcResponse.getResult());
        this.response = rpcResponse;
        synchronized (obj){
            this.obj.notify();
        }
        channelHandlerContext.writeAndFlush(this.response).addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("client caught exception", cause);
        ctx.close();
    }
}
