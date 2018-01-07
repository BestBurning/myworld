package com.diyishuai.hadoop.mr.wcdemo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * KEYIN:默认情况下，是mr框架所读到的一行文本的起始偏移量，Long
 * 但是在hadoop中有更精简的序列化接口，所以不直接用Long，而用LongWritable
 * VALUEIN:默认情况下，是mr框架所读到的一行文本内容，String，用Text
 * KEYOUT：用户自定义逻辑处理完之后输出数据中的Key，在此处是单词，String，用Text
 * VALUEOUT：用户自定义逻辑处理完成之后输出数据中的value，在此处是单词次数，Integer，用IntWritabel
 */
public class WordcountMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
    /**
     * map阶段的业务逻辑就写在自定义的map()方法中
     * maptask会对每一行输入数据调用一次自定义的map()方法
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将maptask传给文本内容先转换成String
        String line = value.toString();
        //根据空格将这一行分成单词
        String[] words = line.split(" ");
        //将单词输出为<单词，1>
        for (String word : words) {
            //将单词作为key，将次数1作为value，可以根据单词分发，以便于相同单词会到相同的reduce task
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
