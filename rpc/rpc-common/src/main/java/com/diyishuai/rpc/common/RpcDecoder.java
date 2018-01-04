package com.diyishuai.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.util.SerializationUtils;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;

    //构造函数传入想反序列化的class
    public RpcDecoder(Class<?> genericClass){
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4){
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if(dataLength < 0){
            ctx.close();
        }
        if(in.readableBytes() < dataLength){
            in.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object obj = SerializationUtils.deserialize(data);
        out.add(obj);
    }
}
