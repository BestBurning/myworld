package com.diyishuai.kafka.case2;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Bruce
 * @date 16/9/20
 */
public class CreateFile {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/Bruce/gitrepo/myworld/kafka/src/main/resources/file.txt");
        file.createNewFile();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            String tempString = null;
            for (int i = 1; i < 50001 ; i++) {
                tempString = new Date().toString()+ UUID.randomUUID();
                writer.write(tempString);
                writer.newLine();
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {}
            }
        }
    }
}
