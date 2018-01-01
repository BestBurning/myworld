package com.diyishuai.zk.zkdisk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * 分布式服务上下线感知 客户端
 * 1 获取服务在线情况
 * 2 监听父节点下的子节点增减 更新服务器在线情况
 *
 *
 */
public class DistributedClient {

    public static final String parentStr = "/servers";
    private static String connStr =  "server01:2181,server02:2181,server03:2181";
    private static int timeout = 2000;
    private static ZooKeeper zk = null;
    private static volatile List<String> children = null;

    public static ZooKeeper getConn() throws IOException {
        zk = new ZooKeeper(connStr, timeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType()+"  ---  " + watchedEvent.getPath());
                try {
                    children = zk.getChildren(parentStr, true);
                    System.out.println(children);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return zk;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        zk = getConn();
        System.out.println("client start working");
        Thread.sleep(Long.MAX_VALUE);
    }


}
