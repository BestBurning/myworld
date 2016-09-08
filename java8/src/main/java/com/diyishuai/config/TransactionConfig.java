package com.diyishuai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * Transaction Config
 *
 * @author Cliff Ma
 */
@Configuration
public class TransactionConfig implements TransactionManagementConfigurer{

	@Autowired
	private DataSource dataSource;

	@Bean(name="transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
    }
	

}
