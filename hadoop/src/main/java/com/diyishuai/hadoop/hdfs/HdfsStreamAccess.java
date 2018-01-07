package com.diyishuai.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class HdfsStreamAccess {
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
        FSDataOutputStream outputStream = fs.create(new Path("/hdfsStream.go"), true);
        FileInputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Downloads\\apache-activemq-5.15.2\\README.txt");
        int copy = IOUtils.copy(inputStream, outputStream);
        System.out.println(copy);
    }

    @Test
    public void testDownload() throws IOException {
        FSDataInputStream inputStream = fs.open(new Path("/hdfsStream.go"));
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Administrator\\Downloads\\README.txt");
        int copy = IOUtils.copy(inputStream, outputStream);
        System.out.println(copy);
        }

    @Test
    public void testCat() throws IOException {
        FSDataInputStream inputStream = fs.open(new Path("/hdfsStream.go"));
        IOUtils.copy(inputStream,System.out);
    }

}
