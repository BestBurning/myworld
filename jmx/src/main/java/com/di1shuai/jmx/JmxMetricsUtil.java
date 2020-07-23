package com.di1shuai.jmx;

import java.lang.management.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
/**
 * @author: Shea
 * @date: 2020/7/23
 * @description:
 */
public class JmxMetricsUtil {

    /**
     * 单位转换
     */
    public static class Unit {

        public static final double KB = 1024D;
        public static final double MB = 1024D * KB;
        public static final double GB = 1024D * MB;

        public static double kb(double b) {
            return b / KB;
        }

        public static double mb(double b) {
            return b / MB;
        }

        public static double gb(double b) {
            return b / GB;
        }
    }

    /**
     * 类加载信息
     */
    public static class ClassLoading {

        private static final ClassLoadingMXBean classLoading = ManagementFactory.getClassLoadingMXBean();

        /**
         * 已加载类总数
         */
        public static long getTotalLoadedClassCount() {
            return classLoading.getTotalLoadedClassCount();
        }


        /**
         * 已加载当前类
         */
        public static int getLoadedClassCount() {
            return classLoading.getLoadedClassCount();
        }


        /**
         * 已卸载类总数
         */
        public static long getUnloadedClassCount() {
            return classLoading.getUnloadedClassCount();
        }

    }

    /**
     * 编译信息
     */
    public static class Compilation {

        private static final CompilationMXBean compilation = ManagementFactory.getCompilationMXBean();

        /**
         * JIT编译器名称
         */
        public static String getName() {
            return compilation.getName();
        }

        /**
         * 判断jvm是否支持编译时间的监控
         */
        public static boolean isCompilationTimeMonitoringSupported() {
            return compilation.isCompilationTimeMonitoringSupported();
        }

        /**
         * 总编译时间
         */
        public static long getTotalCompilationTime() {
            if (!isCompilationTimeMonitoringSupported()) {
                return -1L;
            }
            return compilation.getTotalCompilationTime();
        }

    }

    /**
     * 操作系统信息
     */
    public static class OperatingSystem {

        private static final OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();

        private static final boolean isOperatingSystemImpl;

        static {
            isOperatingSystemImpl = "sun.management.OperatingSystemImpl".equals(system.getClass().getName());
        }

        /**
         * 系统名称
         * <p>
         * 相当于 System.getProperty("os.name")
         */
        public static String getName() {
            return system.getName();
        }

        /**
         * 系统版本
         * <p>
         * 相当于System.getProperty("os.version")
         */
        public static String getVersion() {
            return system.getVersion();
        }

        /**
         * 操作系统的架构
         * <p>
         * 相当于System.getProperty("os.arch")
         */
        public static String getArch() {
            return system.getArch();
        }

        /**
         * 可用的内核数
         * <p>
         * 相当于 Runtime.availableProcessors()
         */
        public static int getAvailableProcessors() {
            return system.getAvailableProcessors();
        }

        /**
         * 获取系统负载平均值
         *
         * @since 1.6
         */
        public static double getSystemLoadAverage() {
            return system.getSystemLoadAverage();
        }

        /**
         * public native long getCommittedVirtualMemorySize();
         * <p>
         * public native long getFreeSwapSpaceSize();
         * <p>
         * public native long getFreePhysicalMemorySize();
         * <p>
         * public native long getMaxFileDescriptorCount();
         * <p>
         * public native long getOpenFileDescriptorCount();
         * <p>
         * public native long getProcessCpuTime();
         * <p>
         * public native double getProcessCpuLoad();
         * <p>
         * public native double getSystemCpuLoad();
         * <p>
         * public native long getTotalPhysicalMemorySize();
         * <p>
         * public native long getTotalSwapSpaceSize();
         */
        public static class Impl {


            public static boolean isOperatingSystemImpl() {
                return isOperatingSystemImpl;
            }

            private static long getLong(String methodName) {
                try {
                    final Method method = OperatingSystem.system.getClass().getMethod(methodName, (Class<?>[]) null);
                    method.setAccessible(true);
                    return (long) method.invoke(OperatingSystem.system, (Object[]) null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    // Do Noting
                }
                return -1L;
            }

            private static double getDouble(String methodName) {
                try {
                    final Method method = OperatingSystem.system.getClass().getMethod(methodName, (Class<?>[]) null);
                    method.setAccessible(true);
                    return (double) method.invoke(OperatingSystem.system, (Object[]) null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    // Do Noting
                }
                return -1D;
            }

            public static long getCommittedVirtualMemorySize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getCommittedVirtualMemorySize");
            }

            public static long getTotalSwapSpaceSize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getTotalSwapSpaceSize");
            }

            public static long getFreeSwapSpaceSize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getFreeSwapSpaceSize");
            }

            public static long getProcessCpuTime() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getProcessCpuTime");
            }

            public static long getFreePhysicalMemorySize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getFreePhysicalMemorySize");
            }

            public static long getTotalPhysicalMemorySize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getTotalPhysicalMemorySize");
            }

            public static long getOpenFileDescriptorCount() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getOpenFileDescriptorCount");
            }

            public static long getMaxFileDescriptorCount() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getMaxFileDescriptorCount");
            }

            public static double getSystemCpuLoad() {
                if (!isOperatingSystemImpl()) {
                    return -1D;
                }
                return getDouble("getSystemCpuLoad");
            }

            public static double getProcessCpuLoad() {
                if (!isOperatingSystemImpl()) {
                    return -1D;
                }
                return getDouble("getProcessCpuLoad");
            }

        }
    }

    /**
     * 运行时信息
     */
    public static class Runtime {

        private static final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();

        /**
         * pid@主机名 = vmId
         */
        public static String getName() {
            return runtime.getName();
        }

        /**
         * 进程ID
         */
        public static String getPid() {
            return runtime.getName().split("@")[0];
        }


        /**
         * 引导类路径
         */
        public static String getBootClassPath() {
            return runtime.getBootClassPath();
        }

        /**
         * 库路径
         */
        public static String getLibraryPath() {
            return runtime.getLibraryPath();
        }

        /**
         * 类路径
         */
        public static String getClassPath() {
            return runtime.getClassPath();
        }

        /**
         * jvm规范名称
         */
        public static String getSpecName() {
            return runtime.getSpecName();
        }

        /**
         * jvm规范运营商
         */
        public static String getSpecVendor() {
            return runtime.getSpecVendor();
        }

        /**
         * jvm规范版本
         * <p>
         * 1.8
         */
        public static String getSpecVersion() {
            return runtime.getSpecVersion();
        }

        /**
         * jvm名称
         * <p>
         * 相当于System.getProperty("java.vm.name")
         */
        public static String getVmName() {
            return runtime.getVmName();
        }

        /**
         * jvm运营商
         * <p>
         * 相当于System.getProperty("java.vm.vendor")
         */
        public static String getVmVendor() {
            return runtime.getVmVendor();
        }

        /**
         * jvm实现版本
         * <p>
         * 相当于System.getProperty("java.vm.version")
         * <p>
         * 25.131-b11
         */
        public static String getVmVersion() {
            return runtime.getVmVersion();
        }

        public static String getManagementSpecVersion() {
            return runtime.getManagementSpecVersion();
        }

        /**
         * jvm启动时间（毫秒）
         */
        public static long getStartTime() {
            return runtime.getStartTime();
        }

        /**
         * jvm正常运行时间（毫秒）
         */
        public static long getUptime() {
            return runtime.getUptime();
        }

        /**
         * 获取系统属性
         */
        public static Map<String, String> getSystemProperties() {
            return runtime.getSystemProperties();
        }

        /**
         * JVM 启动参数
         */
        public static List<String> getInputArguments() {
            return runtime.getInputArguments();
        }


    }

    /**
     * 线程
     */
    public static class Thread {

        private static final ThreadMXBean thread = ManagementFactory.getThreadMXBean();

        /**
         * 返回当前活动线程数，包括守护和非守护线程
         */
        public static int getThreadCount() {
            return thread.getThreadCount();
        }

        /**
         * 峰值活动线程数
         */
        public static int getPeakThreadCount() {
            return thread.getPeakThreadCount();
        }

        /**
         * JVM 启动以来创建的线程总数
         */
        public static long getTotalStartedThreadCount() {
            return thread.getTotalStartedThreadCount();
        }

        /**
         * 守护线程数
         */
        public static long getDaemonThreadCount() {
            return thread.getDaemonThreadCount();
        }

        /**
         * 获取所有的线程信息
         */
        public static Map<Long, ThreadInfo> getAllThreadInfo() {
            Map<Long, ThreadInfo> allThreadInfo = new HashMap<>();
            long[] threadIds = thread.getAllThreadIds();
            for (long threadId : threadIds) {
                ThreadInfo threadInfo = thread.getThreadInfo(threadId);
                allThreadInfo.put(threadId, threadInfo);
            }
            return allThreadInfo;
        }

        /**
         * 所有的死锁线程
         */
        public static Map<Long, ThreadInfo> getAllDeadlockedThreadInfo() {
            Map<Long, ThreadInfo> allDeadlockedThreadInfo = new HashMap<>();
            long[] threadIds = thread.findDeadlockedThreads();
            for (long threadId : threadIds) {
                ThreadInfo threadInfo = thread.getThreadInfo(threadId);
                allDeadlockedThreadInfo.put(threadId, threadInfo);
            }
            return allDeadlockedThreadInfo;
        }

    }


    /**
     * MemoryManager
     */
    public static class Memory {

        /**
         * 内存信息
         */
        private static final MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

        /**
         * 内存区域
         */
        private static final Map<String, MemoryPoolMXBean> pools = new HashMap<>();

        static {
            List<MemoryPoolMXBean> memoryPool = ManagementFactory.getMemoryPoolMXBeans();
            for (MemoryPoolMXBean pool : memoryPool) {
                pools.put(pool.getName(), pool);
            }
        }

        /**
         * 垃圾收集器
         */
        private static final Map<String, GarbageCollectorMXBean> collectors = new HashMap<>();

        static {
            List<GarbageCollectorMXBean> garbageCollectors = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean garbageCollector : garbageCollectors) {
                collectors.put(garbageCollector.getName(), garbageCollector);
            }
        }

        /**
         * 内存区域
         */
        public enum PoolEnum {


            /**
             * -XX:+UseSerialGC
             */
            EDEN_SPACE("Eden Space"),
            /**
             * -XX:+UseSerialGC
             */
            SURVIVOR_SPACE("Survivor Space"),
            /**
             * -XX:+UseSerialGC
             */
            TENURED_GEN("Tenured Gen"),

            /**
             *
             */
            PS_EDEN_SPACE("PS Eden Space"),
            /**
             *
             */
            PS_SURVIVOR_SPACE("PS Survivor Space"),
            /**
             *
             */
            PS_OLD_GEN("PS Old Gen"),


            /**
             * -XX:+UseConcMarkSweepGC
             */
            PAR_EDEN_SPACE("Par Eden Space"),
            /**
             * -XX:+UseConcMarkSweepGC
             */
            PAR_SURVIVOR_SPACE("Par Survivor Space"),
            /**
             * -XX:+UseConcMarkSweepGC
             */
            CMS_OLD_GEN("CMS Old Gen"),


            /**
             * -XX:+UseG1GC
             */
            G1_EDEN_SPACE("G1 Eden Space"),
            /**
             * -XX:+UseG1GC
             */
            G1_SURVIVOR_SPACE("G1 Survivor Space"),
            /**
             * -XX:+UseG1GC
             */
            G1_OLD_GEN("G1 Old Gen"),


            /**
             *
             */
            META_SPACE("Metaspace"),
            /**
             *
             */
            CODE_CACHE("Code Cache"),
            /**
             *
             */
            COMPRESSED_CLASS_SPACE("Compressed Class Space"),
            ;

            private String poolName;

            PoolEnum(String poolName) {
                this.poolName = poolName;
            }

            public String getPoolName() {
                return poolName;
            }

            public static PoolEnum enumOf(String name) {
                PoolEnum[] values = PoolEnum.values();
                for (PoolEnum value : values) {
                    if (Objects.equals(value.poolName, name)) {
                        return value;
                    }
                }
                return null;
            }
        }

        /**
         * 垃圾收集名称
         */
        public enum CollectorEnum {

            /**
             * -XX:+UseSerialGC
             */
            COPY("Copy"),

            /**
             * -XX:+UseSerialGC
             */
            MARK_SWEEP_COMPACT("MarkSweepCompact"),

            /**
             * -XX:+UseParallelOldGC
             * -XX:+UseParallelGC
             */
            PS_SCAVENGE("PS Scavenge"),

            /**
             * -XX:+UseParallelOldGC
             * -XX:+UseParallelGC
             */
            PS_MARKSWEEP("PS MarkSweep"),

            /**
             * -XX:+UseConcMarkSweepGC
             * -XX:+UseParNewGC
             */
            Par_New("ParNew"),

            /**
             * -XX:+UseConcMarkSweepGC
             * -XX:+UseParNewGC
             */
            Concurrent_Mark_Sweep("ConcurrentMarkSweep"),

            /**
             * -XX:+UseG1GC
             */
            G1_Young("G1 Young Generation"),

            /**
             * -XX:+UseG1GC
             */
            G1_Old("G1 Old Generation"),;

            private String collectorName;

            CollectorEnum(String collectorName) {
                this.collectorName = collectorName;
            }

            public String getCollectorName() {
                return collectorName;
            }

            public static CollectorEnum enumOf(String name) {
                CollectorEnum[] values = CollectorEnum.values();
                for (CollectorEnum value : values) {
                    if (Objects.equals(value.collectorName, name)) {
                        return value;
                    }
                }
                return null;
            }


        }


        /**
         * 堆内内存使用量
         * <pre>
         *        +----------------------------------------------+
         *        +////////////////           |                  +
         *        +////////////////           |                  +
         *        +----------------------------------------------+
         *
         *        |--------|
         *           init
         *        |---------------|
         *               used
         *        |---------------------------|
         *                  committed
         *        |----------------------------------------------|
         *                            max
         * </pre>
         */
        public static MemoryUsage getHeapMemoryUsage() {
            return memory.getHeapMemoryUsage();
        }

        /**
         * 堆外内存使用量
         * <pre>
         *        +----------------------------------------------+
         *        +////////////////           |                  +
         *        +////////////////           |                  +
         *        +----------------------------------------------+
         *
         *        |--------|
         *           init
         *        |---------------|
         *               used
         *        |---------------------------|
         *                  committed
         *        |----------------------------------------------|
         *                            max
         * </pre>
         */
        public static MemoryUsage getNonHeapMemoryUsage() {
            return memory.getNonHeapMemoryUsage();
        }

        /**
         * 当前 JVM 的内存区域名称
         */
        public static Set<String> getPoolNames() {
            return pools.keySet();
        }

        /**
         * 对应的内存区域是否存在
         */
        public static boolean existMemoryPool(PoolEnum name) {
            return pools.containsKey(name.getPoolName());
        }

        public static MemoryUsage getPoolUsage(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return nullUsage(null);
            }
            return nullUsage(pools.get(name.getPoolName()).getUsage());
        }

        public static MemoryType getPoolType(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return null;
            }
            return pools.get(name.getPoolName()).getType();
        }

        /**
         * 返回 JVM 最近在此内存池中回收未使用的对象 所花费的内存使用量
         */
        public static MemoryUsage getPoolCollectionUsage(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return nullUsage(null);
            }
            return nullUsage(pools.get(name.getPoolName()).getCollectionUsage());
        }

        /**
         * 峰值内存使用量
         */
        public static MemoryUsage getPoolPeakUsage(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return nullUsage(null);
            }
            return nullUsage(pools.get(name.getPoolName()).getPeakUsage());
        }


        /**
         * 以字节为单位返回此内存池的使用阈值
         */
        public static long getPoolUsageThreshold(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return pools.get(name.getPoolName()).getUsageThreshold();
        }

        public static long getPoolUsageThresholdCount(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return pools.get(name.getPoolName()).getUsageThresholdCount();
        }

        public static long getPoolCollectionUsageThreshold(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return pools.get(name.getPoolName()).getCollectionUsageThreshold();
        }

        public static long getPoolCollectionUsageThresholdCount(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return pools.get(name.getPoolName()).getCollectionUsageThresholdCount();
        }

        private static MemoryUsage nullUsage(MemoryUsage usage) {
            return null == usage ? new MemoryUsage(-1L, 0L, 0L, -1L) : usage;
        }

        /**
         * 当前 JVM 的垃圾收集器
         */
        public static Set<String> getCollectorNames() {
            return collectors.keySet();
        }

        /**
         * 是否存在对应的垃圾收集器
         */
        public static boolean existCollector(CollectorEnum collector) {
            return collectors.containsKey(collector.getCollectorName());
        }

        /**
         * 垃圾收集器次数
         */
        public static long getGarbageCollectionCount(CollectorEnum collector) {
            if (!existCollector(collector)) {
                return -1;
            }
            return collectors.get(collector.getCollectorName()).getCollectionCount();
        }

        /**
         * 垃圾回收期总耗时
         */
        public static long getGarbageCollectionTime(CollectorEnum collector) {
            if (!existCollector(collector)) {
                return -1;
            }
            return collectors.get(collector.getCollectorName()).getCollectionTime();
        }

        /**
         * 垃圾回收器 可回收的区域名称
         */
        public static List<String> getGarbageCollectionMemoryPoolNames(CollectorEnum collector) {
            if (!existCollector(collector)) {
                return new ArrayList<>();
            }
            String[] memoryPoolNames = collectors.get(collector.getCollectorName()).getMemoryPoolNames();
            return new ArrayList<>(Arrays.asList(memoryPoolNames));
        }

    }

}