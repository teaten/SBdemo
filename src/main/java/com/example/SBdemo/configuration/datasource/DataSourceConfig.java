package com.example.SBdemo.configuration.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfig {

	/**
	 * @Primary 注解用于标识默认使用的 DataSource Bean， 但不能用于 dynamicDataSource Bean, 否则会产生循环调用
	 * 
	 * @return data source
	 */
	@Bean("datasourceWrite")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.datasource-write")
	public DataSource datasourceWrite() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("datasourceReadA")
	@ConfigurationProperties(prefix = "spring.datasource.datasource-read-a")
	public DataSource datasourceReadA() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("datasourceReadB")
	@ConfigurationProperties(prefix = "spring.datasource.datasource-read-b")
	public DataSource datasourceReadB() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(DataSourceKey.write.name(), datasourceWrite());
		dataSourceMap.put(DataSourceKey.readA.name(), datasourceReadA());
		dataSourceMap.put(DataSourceKey.readB.name(), datasourceReadB());

		// 将 write 数据源作为默认指定的数据源
		dynamicRoutingDataSource.setDefaultTargetDataSource(datasourceWrite());
		// 将 write 和 readA,readB 数据源作为指定的数据源
		dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

		// 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
		DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

		// 将 read 数据源的 key 放在集合中，用于轮循
		DynamicDataSourceContextHolder.readDataSourceKeys.addAll(dataSourceMap.keySet());
		DynamicDataSourceContextHolder.readDataSourceKeys.remove(DataSourceKey.write.name());
		return dynamicRoutingDataSource;
	}

	@Bean
	@ConfigurationProperties(prefix = "mybatis")
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// Here is very important, if don't config this, will can't switch datasource
		// put all datasource into SqlSessionFactoryBean, then will autoconfig
		// SqlSessionFactory
		sqlSessionFactoryBean.setDataSource(dynamicDataSource());
		return sqlSessionFactoryBean;
	}

	/**
	 * Transaction manager platform transaction manager.
	 *
	 * @return the platform transaction manager
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}

}
