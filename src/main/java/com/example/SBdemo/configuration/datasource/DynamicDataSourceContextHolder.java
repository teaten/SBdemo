package com.example.SBdemo.configuration.datasource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DynamicDataSourceContextHolder {
	  private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

	    private static int counter = 0;

	    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.write::name);

	    public static List<Object> dataSourceKeys = new ArrayList<>();

	    public static List<Object> readDataSourceKeys = new ArrayList<>();
	    
	    public static List<Object> selectDataSourceKeys = new ArrayList<>();

	    public static void setDataSourceKey(String key) {
	        CONTEXT_HOLDER.set(key);
	    }

	    public static void useWriteDataSource() {
	        CONTEXT_HOLDER.set(DataSourceKey.write.name());
	    }

	    public static void useReadDataSource() {
	            int datasourceKeyIndex = counter % readDataSourceKeys.size();
	            CONTEXT_HOLDER.set(String.valueOf(readDataSourceKeys.get(datasourceKeyIndex)));
	            counter++;
	    }
	    
	    public static void useSelectDataSource() {
            int datasourceKeyIndex = counter % readDataSourceKeys.size();
            CONTEXT_HOLDER.set(String.valueOf(selectDataSourceKeys.get(datasourceKeyIndex)));
            counter++;
	    }

	    public static String getDataSourceKey() {
	        return CONTEXT_HOLDER.get();
	    }

	    public static void clearDataSourceKey() {
	        CONTEXT_HOLDER.remove();
	    }

	    public static boolean containDataSourceKey(String key) {
	        return dataSourceKeys.contains(key);
	    }
}
