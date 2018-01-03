package com.diyishuai.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup eventLoopGroup = null;
        try {
            //server端引导类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //连接池处理类
            eventLoopGroup = new NioEventLoopGroup();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)//指定通道类型为NioServerSocket
                    .localAddress("localhost",port)//设置InetSocketAddress让服务器监听端口
                    .childHandler(new ChannelInitializer<Channel>() {//设置childhandler执行所有的连接请求
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new EchoServerHandler());//注册handler
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("开始监听，端口为：" + channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws Exception {
        new EchoServer(20000).start();
    }


}
