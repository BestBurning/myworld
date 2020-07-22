package com.di1shuai.io.nio.jdk;

import com.di1shuai.io.AbstractServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Shea
 * @date: 2020/7/22
 * @description: 一、使用 NIO 完成网络通信的三个核心：
 * <p>
 * 1. 通道（Channel）：负责连接
 * <p>
 * java.nio.channels.Channel 接口：
 * |--SelectableChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 * 2. 缓冲区（Buffer）：负责数据的存取
 * <p>
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 */
public class NIOServer extends AbstractServer {

    ServerSocketChannel ssChannel = null;

    Integer port = 8083;

    static ExecutorService executorService = Executors.newFixedThreadPool(20);

    private volatile boolean open = false;

    @Override
    public void start() {
        open = true;
        try {
            //1. 获取通道
            ssChannel = ServerSocketChannel.open();
            //2. 切换非阻塞模式
            ssChannel.configureBlocking(false);
            //3. 绑定连接
            ssChannel.bind(new InetSocketAddress(port));
            //4. 获取选择器
            System.out.println("开始监听 >" + port + "<");
            Selector selector = Selector.open();
            System.out.println(selector.getClass().getName());
            //5. 将通道注册到选择器上, 并且指定“监听接收事件”
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (open) {
                //6. 轮询式的获取选择器上已经“准备就绪”的事件
                //使用 select() 来监听到达的事件，它会一直阻塞直到有至少一个事件到达。
                while (selector.select() > 0) {
                    //7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                    while (it.hasNext()) {
                        //8. 获取准备“就绪”的是事件
                        SelectionKey sk = it.next();

                        //9. 判断具体是什么事件准备就绪
                        if (sk.isAcceptable()) {
                            //10. 若“接收就绪”，获取客户端连接
                            SocketChannel sChannel = ssChannel.accept();

                            //11. 切换非阻塞模式
                            sChannel.configureBlocking(false);

                            //12. 将该通道注册到选择器上
                            sChannel.register(selector, SelectionKey.OP_READ);
                        } else if (sk.isReadable()) {
                            //13. 获取当前选择器上“读就绪”状态的通道
                            SocketChannel sChannel = (SocketChannel) sk.channel();
                            sChannel.configureBlocking(false);
                            //14. 读取数据
                            ByteBuffer buf = ByteBuffer.allocate(1024);

                            int len = 0;
                            while ((len = sChannel.read(buf)) > 0) {
                                buf.flip();
                                System.out.println(new String(buf.array(), 0, len));
                                buf.clear();
                            }
                            sChannel.register(selector, SelectionKey.OP_WRITE);
                        }
//                        else if (sk.isWritable()) {
//                            SocketChannel sChannel = (SocketChannel) sk.channel();
//                            sChannel.configureBlocking(false);
//                            String response = "Server ACK |" + LocalDateTime.now();
//                            //15. 写数据
//                            ByteBuffer buf = ByteBuffer.allocate(1024);
//                            for (byte aByte : response.getBytes()) {
//                                buf.put(aByte);
//                            }
//                            buf.flip();
//                            sChannel.write(buf);
//                            buf.clear();
//                        }

                        //15. 取消选择键 SelectionKey
                        //每一个“事件关键字”被处理后都必须移除，否则下一次轮询时，这个事件会被重复处理
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void stop() {

    }


    public static void main(String[] args) {
        NIOServer nioServer = new NIOServer();
        nioServer.start();

    }

}
