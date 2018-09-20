package com.example.SBdemo.configuration.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource{
	  private final Logger logger = LoggerFactory.getLogger(this.getClass());

	    @Override
	    protected Object determineCurrentLookupKey() {
	        logger.info("Current DataSource is [{}]", DynamicDataSourceContextHolder.getDataSourceKey());
	        return DynamicDataSourceContextHolder.getDataSourceKey();
	    }

}
