package com.diyishuai.hadoop.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

public class HdfsClientDemo {


    FileSystem fs = null;

    Configuration conf = null;

    @Before
    public void init() throws Exception{
        conf = new Configuration();
//        conf.set("fs.defaultFS","hdfs://server01:9000");

        fs = FileSystem.get(new URI("hdfs://server01:9000"),conf,"root");
    }

    @Test
    public void testUpload() throws IOException {
        fs.copyFromLocalFile(new Path("C:\\Users\\Administrator\\Downloads\\apache-maven-3.5.2-bin.zip"),new Path("/hihi.txt"));
    }

    @Test
    public void testDownload() throws IOException {
        fs.copyToLocalFile(new Path("/hihi.txt"),new Path("C:\\Users\\Administrator\\Downloads\\hihi-from-down.txt"));
    }

    @Test
    public void testDefaultEnv(){
        Iterator<Map.Entry<String, String>> iterator = conf.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }

    @Test
    public void testMkdir() throws IOException {
        boolean mkdirs = fs.mkdirs(new Path("/test/hello/hadoop"));
        System.out.println(mkdirs);
    }

    @Test
    public void testDeleteDir() throws IOException {
        boolean delete = fs.delete(new Path("/test/hello"), true);
        System.out.println(delete);
    }

    @Test
    public void testModifyDir() throws IOException {
        boolean rename = fs.rename(new Path("/test"), new Path("/test123"));
        System.out.println(rename);
    }

    @Test
    public void testLsFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);
        while (files.hasNext()){
            LocatedFileStatus file = files.next();
            System.out.println(file.getBlockSize());
            System.out.println(file.getPath());
            System.out.println(file.getReplication());
            System.out.println(file.getOwner());
            System.out.println("=========");
        }
    }

    @Test
    public void testLsFilesAndDirs() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println("path: "+ fileStatus.getPath());
            System.out.println("name: "+ fileStatus.getPath().getName());
            System.out.println("Owner : "+fileStatus.getOwner());
            System.out.println("Permission : "+fileStatus.getPermission());
            System.out.println("Replication : "+fileStatus.getReplication());
            System.out.println("Group : "+fileStatus.getGroup());
            System.out.println("======");
        }
    }

}
