package com.diyishuai.hadoop.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

public class HdfsClientDemo {


    FileSystem fs = null;

    @Before
    public void init() throws Exception{
        Configuration conf = new Configuration();
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

}
