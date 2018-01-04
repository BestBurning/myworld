package com.diyishuai.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.util.SerializationUtils;

public class RpcEncode extends MessageToByteEncoder{

    private Class<?> genericClass;

    //构造函数传入想序列化的class
    public RpcEncode(Class<?> genericClass){
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if(genericClass.isInstance(in)){
            byte[] data = SerializationUtils.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
