package com.diyishuai.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * DB Config
 *
 * @author Cliff Ma
 */
@Configuration
public class DBConfig {

    @Autowired
    private Environment env;
    
    @Bean(name="dataSource")
    public DruidDataSource dataSource() {
        final String url = Preconditions.checkNotNull(env.getProperty("ms.db.url"));
        final String username = Preconditions.checkNotNull(env.getProperty("ms.db.username"));
        final String password = env.getProperty("ms.db.password");
        final int maxActive = Integer.parseInt(env.getProperty("ms.db.maxActive", "200"));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        
        return dataSource;
    }
    
    
}

