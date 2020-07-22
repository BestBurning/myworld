package com.di1shuai.io.bio;

import com.di1shuai.io.AbstractServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Shea
 * @date: 2020/7/22
 * @description:
 */
public class BIOServer extends AbstractServer {
    //默认的端口号
    private int DEFAULT_PORT = 8082;
    //线程池 懒汉式的单例
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public volatile boolean open = false;

    private ServerSocket serverSocket = null;

    @Override
    public void start() {
        open = true;
        try {
            System.out.println("监听 >" + DEFAULT_PORT + "< 端口");
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while (open) {
                Socket socket = serverSocket.accept();
                //当然业务处理过程可以交给一个线程（这里可以使用线程池）,并且线程的创建是很耗资源的。
                //最终改变不了.accept()只能一个一个接受socket的情况,并且被阻塞的情况
                ServerRequestHandle serverRequestHandle = new ServerRequestHandle(socket);
                executorService.execute(serverRequestHandle);
            }
        } catch (Exception e) {

        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        //这个wait不涉及到具体的实验逻辑，只是为了保证守护线程在启动所有线程后，进入等待状态
        synchronized (BIOServer.class) {
            try {
                BIOServer.class.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        open = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(executorService!=null && !executorService.isShutdown()){
            executorService.shutdown();
        }

    }

    public static void main(String[] args) {
        BIOServer bioServer = new BIOServer();
        bioServer.start();
    }

}

class ServerRequestHandle implements Runnable {
    private Socket socket;

    public ServerRequestHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            //下面我们收取信息
            in = socket.getInputStream();
            out = socket.getOutputStream();
            Integer sourcePort = socket.getPort();
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            //使用线程，同样无法解决read方法的阻塞问题，
            //也就是说read方法处同样会被阻塞，直到操作系统有数据准备好
            int realLen = in.read(contextBytes, 0, maxLen);
            //读取信息
            String message = new String(contextBytes, 0, realLen);

            //下面打印信息
            System.out.println("Server 收到信息 -> " + message + "  -> port : > " + sourcePort);

            //下面开始发送信息
            out.write("回发响应信息！".getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //试图关闭
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
