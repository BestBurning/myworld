package com.diyishuai.rpc.server;

import com.diyishuai.rpc.common.RpcDecoder;
import com.diyishuai.rpc.common.RpcEncode;
import com.diyishuai.rpc.common.RpcRequest;
import com.diyishuai.rpc.common.RpcResponse;
import com.diyishuai.rpc.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class RpcServer implements ApplicationContextAware,InitializingBean{
//public class RpcServer implements InitializingBean{

    public static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    //用于存储业务接口和实现类的实例对象
    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    private String serverAddress;

    private ServiceRegistry serviceRegistry;

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * 通过注解获取标注了rpc服务注解的业务类---接口及impl对象，将它放到handlerMap中
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        for (Object serviceBean: beansMap.values()){
            String interfaceName = serviceBean.getClass().getInterfaces()[0].getName();
            handlerMap.put(interfaceName,serviceBean);
        }
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names){
            logger.debug(name);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel
                                    .pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class)) //注册解码 IN-1
                                    .addLast(new RpcEncode(RpcResponse.class)) //注册编码 OUT
                                    .addLast(new RpcHandler(handlerMap));//注册RpcHandler IN-2

                        }
                    }).option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);

            ChannelFuture future = serverBootstrap.bind(host,port).sync();
            logger.debug("server started on port {} ",port);
            if (serviceRegistry !=null )
                serviceRegistry.register(serverAddress);

            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
