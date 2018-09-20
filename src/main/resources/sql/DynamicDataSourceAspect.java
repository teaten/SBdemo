package com.example.SBdemo.configuration.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

	private final String[] QUERY_PREFIX = { "select" };

	/**
	 * Dao aspect.
	 */
	@Pointcut("execution( * com.example.SBdemo.dao.*.*(..))")
	public void daoAspect() {
	}

	/**
	 * Switch DataSource
	 *
	 * @param point
	 *            the point
	 */
	@Before("daoAspect()")
	public void switchDataSource(JoinPoint point, DataSource datasource) {
		if (null == datasource) {
			Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
			if (isQueryMethod) {
				DynamicDataSourceContextHolder.useReadDataSource();
				logger.debug("Switch DataSource to [{}] in Method [{}]",
						DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
			}
		} else {
			String datasourceName = datasource.value().name();
			if (!DynamicDataSourceContextHolder.containDataSourceKey(datasourceName)) {
				logger.error("========没有该数据源！！使用默认");
			}
			if (datasourceName.startsWith("read")) {
				DynamicDataSourceContextHolder.useReadDataSource();
				logger.debug("Switch DataSource to [{}] in Method [{}]",
						DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
			}
			if (datasourceName.startsWith("write")) {
				DynamicDataSourceContextHolder.useWriteDataSource();
				logger.debug("Switch DataSource to [{}] in Method [{}]",
						DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
			}
		}
	}

	/**
	 * Restore DataSource
	 *
	 * @param point
	 *            the point
	 */
	@After("daoAspect())")
	public void restoreDataSource(JoinPoint point) {
		DynamicDataSourceContextHolder.clearDataSourceKey();
		logger.debug("Restore DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(),
				point.getSignature());
	}

	/**
	 * Judge if method start with query prefix
	 *
	 * @param methodName
	 * @return
	 */
	private Boolean isQueryMethod(String methodName) {
		for (String prefix : QUERY_PREFIX) {
			if (methodName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

}
