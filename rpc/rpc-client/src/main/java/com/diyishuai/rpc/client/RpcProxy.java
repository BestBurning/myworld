package com.diyishuai.rpc.client;

import com.diyishuai.rpc.common.RpcRequest;
import com.diyishuai.rpc.common.RpcResponse;
import com.diyishuai.rpc.registry.ServiceDiscover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcProxy {
    public static Logger logger = LoggerFactory.getLogger(RpcProxy.class);
    private String serverAddress;
    private ServiceDiscover serviceDiscover;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    public <T> T create(Class<?> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //创建RpcRequest,封装被代理类的属性
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);
                        //查找服务
                        if(serviceDiscover != null){
                            serverAddress = serviceDiscover.discover();
                        }
                        //随机获取服务地址
                        String[] array = serverAddress.split(":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);
                        logger.debug("client will connect to ====>host:"+host+" || port:"+port);
                        //创建Netty实现的RpcClient，连接服务端
                        RpcClient client = new RpcClient(host,port);
                        RpcResponse response = client.send(request);
                        logger.debug("client had get response :" + response.getResult());
                        if (response.isError()){
                            throw response.getError();
                        }else {
                            return response.getResult();
                        }
                    }
                });
    }
}
