package com.diyishuai.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class SimpleZkClient {

    private static String connStr =  "server01:2181,server02:2181,server03:2181";
    private static int timeout = 2000;

    private static ZooKeeper zooKeeper =  null;

    //建立客户端连接
    public static void init() throws IOException {
        zooKeeper = new ZooKeeper(connStr, timeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType()+"  ---  " + watchedEvent.getPath());
                try {
                    zooKeeper.getChildren("/",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        init();
//        createNode();
//        exist();
//        getChildren();
//        deleteNode();
//        exist();
        getData();
    }

    //创建节点
    public static void createNode() throws KeeperException, InterruptedException {
        String s = zooKeeper.create("/diyishuai", "zs".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }

    //判断节点是否存在
    public static void exist() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists("/diyishuai", false);
        System.out.println(stat==null?"not exist":"exist");
    }

    //获取子节点
    public static void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", true);
        children.stream().forEach(
                child -> System.out.println(child)
        );
        Thread.sleep(Long.MAX_VALUE);
    }
    //删除
    public static void deleteNode() throws KeeperException, InterruptedException {
        zooKeeper.delete("/diyishuai",-1);
    }

    //获取节点数据
    public static void getData() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/diyishuai", true, null);
        System.out.println(new String(data));
    }

    //更改节点数据
    public static void setData() throws KeeperException, InterruptedException {
        zooKeeper.setData("/diyishuai","zuishuai".getBytes(),2);
    }


}
