package com.diyishuai.rpc.registry;

import io.netty.util.internal.ThreadLocalRandom;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServiceDiscover {
    private Logger logger = LoggerFactory.getLogger(ServiceDiscover.class);
    private volatile List<String> dataList = new ArrayList<String>();
    private CountDownLatch latch = new CountDownLatch(1);
    private String registryAddress;

    public ServiceDiscover(String registryAddress) {
        this.registryAddress = registryAddress;
        ZooKeeper zk = connectServer();
        if(zk != null){
            watchNode(zk);
        }
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList){
                //获取节点中的服务器地址
                byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH+"/"+node,false,null);
                //存储到list中
                dataList.add(new String(bytes));
            }
            logger.debug("node data : ",dataList);
            this.dataList = dataList;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected)
                        latch.countDown();
                }
            });
            latch.await();
        }catch (Exception e){
            logger.error("",e);
        }
        return zk;
    }

    /**
     * 发现新节点
     * @return
     */
    public String discover() {
        String data = null;
        int size = dataList.size();
        //存在新节点,使用即可
        if(size > 0){
            if(size == 1){
                data = dataList.get(0);

            }else {
                data = dataList.get(ThreadLocalRandom.current().nextInt());
            }
        }
        return data;
    }
}
