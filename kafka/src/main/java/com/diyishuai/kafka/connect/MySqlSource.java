package com.diyishuai.kafka.connect;

import io.confluent.connect.jdbc.JdbcSourceConnector;
import io.confluent.connect.jdbc.source.JdbcSourceConnectorConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Bruce
 * @date 16/9/27
 */
public class MySqlSource {

    public static void main(String[] args) {
        Map connProps = new HashMap();
        connProps.put(JdbcSourceConnectorConfig.CONNECTION_URL_CONFIG, "jdbc:mysql://localhost:3306/test?user=root&password=admin");
        connProps.put(JdbcSourceConnectorConfig.MODE_CONFIG, JdbcSourceConnectorConfig.MODE_BULK);
        connProps.put(JdbcSourceConnectorConfig.TOPIC_PREFIX_CONFIG, "mysql-");
        connProps.put(JdbcSourceConnectorConfig.MODE_CONFIG, JdbcSourceConnectorConfig.MODE_BULK);
        JdbcSourceConnector connector = new JdbcSourceConnector();
        connector.start(connProps);
        List<Map<String, String>> maps = connector.taskConfigs(1);
        maps.stream()
                .forEach(map -> System.out.println(map));
        connector.stop();
    }

}
