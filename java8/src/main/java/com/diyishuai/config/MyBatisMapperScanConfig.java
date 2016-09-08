package com.diyishuai.config;

import com.diyishuai.annotation.MyBatisRepository;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Scan config
 *
 * @author Cliff Ma
 */
@Configuration
public class MyBatisMapperScanConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.diyishuai.dao");
		mapperScannerConfigurer.setAnnotationClass(MyBatisRepository.class);
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}
}
