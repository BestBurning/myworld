package com.diyishuai.zk.zkdisk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * 分布式服务上下线感知 服务端
 * 目标 服务器上下线感知
 * 1 连接zk
 * 2 注册
 */
public class DistributedServer {

    public static final String parentStr = "/servers";
    private static String connStr =  "server01:2181,server02:2181,server03:2181";
    private static int timeout = 2000;
    public static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        if (args.length==0)
            throw new Exception("未指定服务器名称");
        //1 连接zk
        zk = getConn();
        //2 注册
        //2.1判断父目录知否存在
        Stat exists = zk.exists(parentStr, false);
        if (exists == null)
            zk.create(parentStr,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        //2.2 上线注册临时节点
        String s = zk.create(parentStr + "/" + args[0], args[0].getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(args[0]+" server start working");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static ZooKeeper getConn() throws IOException {
        zk = new ZooKeeper(connStr, timeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType()+"  ---  " + watchedEvent.getPath());
                try {
                    zk.getChildren("/",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return zk;
    }


}
