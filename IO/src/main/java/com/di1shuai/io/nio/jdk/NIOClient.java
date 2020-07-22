package com.di1shuai.io.nio.jdk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: Shea
 * @date: 2020/7/22
 * @description:
 */
public class NIOClient {

    public static void main(String[] args) {
        //1. 获取通道
        SocketChannel sChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("localhost", 8083));
            //2. 切换非阻塞模式
            sChannel.configureBlocking(false);

            //3. 分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //4. 发送数据给服务端
            Scanner scan = new Scanner(System.in);

            while(scan.hasNext()){
                String str = scan.next();
                buf.put((new Date().toString() + "\n" + str).getBytes());
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

            //5. 关闭通道
            sChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
