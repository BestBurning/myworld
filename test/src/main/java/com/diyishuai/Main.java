package com.diyishuai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Bruce
 * @date 10/13/16
 */
public class Main {


    public static void main(String[] args) throws Exception{
        if (args.length == 0 ){
            throw new Exception("没有传入文件参数");
        }
        File file = new File(args[0]);
        if(!file.exists()){
            throw new Exception("File not exist，path:"+file.getAbsolutePath());
        }
        System.out.println(file.getName()+"文件存在,内容如下：");
        System.out.println("===================================================================");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        while (bufferedReader.ready()){
            System.out.println(bufferedReader.readLine());
        }
        bufferedReader.close();
        System.out.println("===================================================================");
        System.out.println("成功读取文件："+file.getAbsolutePath());

    }
}
