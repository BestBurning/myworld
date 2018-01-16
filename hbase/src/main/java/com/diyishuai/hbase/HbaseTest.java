package com.diyishuai.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HbaseTest {

    static Configuration config = null;
    private Connection connection = null;
    private Table table = null;
    private Admin admin = null;

    @Before
    public void init() throws IOException {
        config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "server01,server02,server03");
        connection = ConnectionFactory.createConnection(config);
        admin = connection.getAdmin();
    }


    @Test
    public void createTable() throws IOException {
        TableName tableName = TableName.valueOf("test");
        HTableDescriptor desc = new HTableDescriptor(tableName);
        desc.addFamily(new HColumnDescriptor("info"));
        desc.addFamily(new HColumnDescriptor("info2"));
        admin.createTable(desc);
    }

    @Test
    public void disableTable() throws IOException {
        String strName = "test";
        TableName tableName = TableName.valueOf(strName);
        System.out.println(strName+" is enable ?: "+admin.isTableEnabled(tableName));
        admin.disableTable(tableName);
        System.out.println(strName + " is enable ? : " + admin.isTableEnabled(tableName));
        admin.enableTable(tableName);
        System.out.println(strName + " is enable ?: " + admin.isTableEnabled(tableName));
    }

    @Test
    public void deleteTable() throws IOException {
        String strName = "test";
        TableName tableName = TableName.valueOf(strName);
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    @Test
    public void list() throws IOException {
        TableName[] tableNames = admin.listTableNames();
        for (TableName tableName : tableNames) {
            System.out.println(new String(tableName.getName()));
        }
    }

    @Test
    public void insertData() throws IOException {
        Put put = new Put(Bytes.toBytes("xiaoming"));
        put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("name"),Bytes.toBytes("xiaoming2"));
        put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("age"),Bytes.toBytes(28));
        put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("sex"),Bytes.toBytes(1));
        put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("address"),Bytes.toBytes("shanghai"));
        Table table = connection.getTable(TableName.valueOf("test"));
        table.put(put);
        }

    @Test
    public void scan() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        ResultScanner scanner = table.getScanner(new Scan());
        for (Result result : scanner){
            byte[] name = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"));
            byte[] sex = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex"));
            byte[] address = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("address"));
            byte[] age = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
            System.out.println(Bytes.toString(name));
            System.out.println(Bytes.toInt(sex));
            System.out.println(Bytes.toString(address));
            System.out.println(Bytes.toInt(age));
        }
    }

    @Test
    public void deleteData() throws IOException {
        Delete delete = new Delete(Bytes.toBytes("xiaoming"));
        delete.addFamily(Bytes.toBytes("info"));
        Table table = connection.getTable(TableName.valueOf("test"));
        table.delete(delete);
    }

    @Test
    public void queryData() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        Get get = new Get(Bytes.toBytes("xiaoming"));
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));
        Result result = table.get(get);
        byte[] name = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"));
//        byte[] sex = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex"));
//        byte[] address = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("address"));
//        byte[] age = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
        System.out.println(Bytes.toString(name));
//        System.out.println(Bytes.toInt(sex));
//        System.out.println(Bytes.toString(address));
//        System.out.println(Bytes.toInt(age));


    }
}
