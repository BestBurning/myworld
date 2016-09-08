package com.diyishuai.config;

import com.jf.page.interceptor.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * MyBatis mapper confgi
 *
 * @author Cliff Ma
 */

@Configuration
public class MyBatisConfig {

	@Autowired
	private DataSource dataSource;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPlugins(new Interceptor[]{new PageInterceptor()});
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:com/diyishuai/domain/*.xml"));
		return sessionFactory;
	}

}
