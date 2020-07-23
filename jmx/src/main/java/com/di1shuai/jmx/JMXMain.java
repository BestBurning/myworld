package com.di1shuai.jmx;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.javac.util.Convert;
import sun.management.ConnectorAddressLink;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.management.*;
import java.util.List;


/**
 * @author: Shea
 * @date: 2020/7/23
 * @description:
 */
public class JMXMain {


    /**
     * {@link ManagementFactory}
     * ClassLoadingMXBean	用于 Java 虚拟机的类加载系统的管理接口。
     * CompilationMXBean	用于 Java 虚拟机的编译系统的管理接口。
     * GarbageCollectorMXBean	用于 Java 虚拟机的垃圾回收的管理接口。
     * MemoryManagerMXBean	内存管理器的管理接口。
     * MemoryMXBean	Java 虚拟机的内存系统的管理接口。
     * MemoryPoolMXBean	内存池的管理接口。
     * OperatingSystemMXBean	用于操作系统的管理接口，Java 虚拟机在此操作系统上运行。
     * RuntimeMXBean	Java 虚拟机的运行时系统的管理接口。
     * ThreadMXBean	Java 虚拟机线程系统的管理接口。
     *
     * @param args
     * @cmd start : javaw -jar -Xms10M -Dapplication=simpleDemo simpleDemo-1.0-SNAPSHOT.jar
     * stop : taskkill /F /PID 1201
     */
    public static void main(String[] args) throws Exception {

        // 获取本机信息
        showJvmInfo();
        System.out.println("=========");
        showMemoryInfo();
        System.out.println("=========");
        showSystem();
        System.out.println("=========");
        showClassLoading();
        System.out.println("=========");
        showCompilation();
        System.out.println("=========");
        showThread();
        System.out.println("=========");
        showGarbageCollector();
        System.out.println("=========");
        showMemoryManager();
        System.out.println("=========");
        showMemoryPool();
        System.out.println("=========");

        // 代理获取 远程 进程信息
       /* String tag = "simpleDemo";
        int pid = AbstractProjectCommander.getInstance().getPid(tag);*/
        int pid = 12861;
        Class clazz = ThreadMXBean.class;

        ThreadMXBean threadMXBean = (ThreadMXBean) visitMBean(pid, clazz);
        long[] threadIds = threadMXBean.getAllThreadIds();
        System.out.println("ThreadCount: " + threadMXBean.getThreadCount());
        System.out.println("AllThreadIds: " + threadMXBean.getAllThreadIds());
        System.out.println("CurrentThreadUserTime: " + threadMXBean.getCurrentThreadUserTime());
        for (int i = 0; i < threadIds.length; i++) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadIds[i]);
            System.out.println(info.getThreadName());
        }

        List<GarbageCollectorMXBean> collectorMXBeanList2 = visitMBeans(pid, GarbageCollectorMXBean.class);
        System.out.println(collectorMXBeanList2);
        for (GarbageCollectorMXBean GarbageCollectorMXBean : collectorMXBeanList2) {
            System.out.println("gc name:" + GarbageCollectorMXBean.getName());
            System.out.println("CollectionCount:" + GarbageCollectorMXBean.getCollectionCount());
            System.out.println("CollectionTime" + GarbageCollectorMXBean.getCollectionTime());
        }

    }

    /**
     * Java 虚拟机的运行时系统
     */
    public static void showJvmInfo() {
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        String vendor = mxbean.getVmVendor();
        System.out.println("jvm name:" + mxbean.getVmName());
        System.out.println("jvm version:" + mxbean.getVmVersion());
        System.out.println("jvm bootClassPath:" + mxbean.getBootClassPath());
        System.out.println("jvm start time:" + mxbean.getStartTime());
    }

    /**
     * Java 虚拟机的内存系统
     */
    public static void showMemoryInfo() {
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        System.out.println("Heap committed:" + heap.getCommitted() + " init:" + heap.getInit() + " max:"
                + heap.getMax() + " used:" + heap.getUsed());
    }

    /**
     * Java 虚拟机在其上运行的操作系统
     */
    public static void showSystem() {
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Architecture: " + op.getArch());
        System.out.println("Processors: " + op.getAvailableProcessors());
        System.out.println("System name: " + op.getName());
        System.out.println("System version: " + op.getVersion());
        System.out.println("Last minute load: " + op.getSystemLoadAverage());
    }

    /**
     * Java 虚拟机的类加载系统
     */
    public static void showClassLoading() {
        ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
        System.out.println("TotalLoadedClassCount: " + cl.getTotalLoadedClassCount());
        System.out.println("LoadedClassCount" + cl.getLoadedClassCount());
        System.out.println("UnloadedClassCount:" + cl.getUnloadedClassCount());
    }

    /**
     * Java 虚拟机的编译系统
     */
    public static void showCompilation() {
        CompilationMXBean com = ManagementFactory.getCompilationMXBean();
        System.out.println("TotalCompilationTime:" + com.getTotalCompilationTime());
        System.out.println("name:" + com.getName());
    }

    /**
     * Java 虚拟机的线程系统
     */
    public static void showThread() {
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        System.out.println("ThreadCount: " + thread.getThreadCount());
        System.out.println("AllThreadIds: " + thread.getAllThreadIds());
        System.out.println("CurrentThreadUserTime: " + thread.getCurrentThreadUserTime());
        //......还有其他很多信息
    }

    /**
     * Java 虚拟机中的垃圾回收器。
     */
    public static void showGarbageCollector() {
        List<GarbageCollectorMXBean> gc = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean GarbageCollectorMXBean : gc) {
            System.out.println("GC name:" + GarbageCollectorMXBean.getName());
            System.out.println("CollectionCount:" + GarbageCollectorMXBean.getCollectionCount());
            System.out.println("CollectionTime" + GarbageCollectorMXBean.getCollectionTime());
        }
    }

    /**
     * Java 虚拟机中的内存管理器
     */
    public static void showMemoryManager() {
        List<MemoryManagerMXBean> mm = ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean eachmm : mm) {
            System.out.println("name:" + eachmm.getName());
            System.out.println("MemoryPoolNames:" + eachmm.getMemoryPoolNames().toString());
        }
    }

    /**
     * Java 虚拟机中的内存池
     */
    public static void showMemoryPool() {
        List<MemoryPoolMXBean> mps = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mp : mps) {
            System.out.println("name:" + mp.getName());
            System.out.println("CollectionUsage:" + mp.getCollectionUsage());
            System.out.println("type:" + mp.getType());
        }
    }

    /**
     * 访问指定程序的 MXBean
     */
    public static <T extends PlatformManagedObject> T visitMBean(int pid, Class<T> clazz) throws Exception {
        //第一种直接调用同一 Java 虚拟机内的 MXBean 中的方法。
//        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
//        String vendor1 = mxbean.getVmVendor();
//        System.out.println("vendor1:" + vendor1);

        //第二种使用 MXBean 代理
        VirtualMachine virtualMachine = VirtualMachine.attach(Integer.toString(pid));
        JMXServiceURL jmxServiceURL = getJMXServiceURL(virtualMachine);
        if (jmxServiceURL == null) {
            return null;
        }
        JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
//        return ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
//        ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, mxBeanName, clazz);
        return ManagementFactory.getPlatformMXBean(mBeanServerConnection, clazz);

    }

    /**
     * 访问指定程序的 MXBean
     */
    public static <T extends PlatformManagedObject> List<T> visitMBeans(int pid, Class<T> clazz) throws Exception {
        //第一种直接调用同一 Java 虚拟机内的 MXBean 中的方法。
//        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
//        String vendor1 = mxbean.getVmVendor();
//        System.out.println("vendor1:" + vendor1);

        //第二种使用 MXBean 代理
        VirtualMachine virtualMachine = VirtualMachine.attach(Integer.toString(pid));
        JMXServiceURL jmxServiceURL = getJMXServiceURL(virtualMachine);
        if (jmxServiceURL == null) {
            return null;
        }
        JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
        return ManagementFactory.getPlatformMXBeans(mBeanServerConnection, clazz);

    }

    public static JMXServiceURL getJMXServiceURL(VirtualMachine virtualMachine) throws IOException, AgentLoadException, AgentInitializationException {
        String address = virtualMachine.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
        if (address != null) {
            return new JMXServiceURL(address);
        }
        int pid = Integer.parseInt(virtualMachine.id());
        address = ConnectorAddressLink.importFrom(pid);
        if (address != null) {
            return new JMXServiceURL(address);
        }
        String javaHome = virtualMachine.getSystemProperties().getProperty("java.home");
        String agentPath = javaHome + File.separator + "jre" + File.separator + "lib" + File.separator + "management-agent.jar";
        File file = new File(agentPath);
        if (!file.exists()) {
            agentPath = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
            file = new File(agentPath);
            if (!file.exists()) {
                throw new IOException("Management agent not found");
            }
        }
        agentPath = file.getCanonicalPath();
        virtualMachine.loadAgent(agentPath, "com.sun.management.jmxremote");

        address = virtualMachine.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
        if (address != null) {
            return new JMXServiceURL(address);
        }
        return null;
    }

}
