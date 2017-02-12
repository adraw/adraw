package io.github.adraw.framework.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfig{
	
	private ConversionService conversionService = new DefaultConversionService(); 
	
	@Bean(name="dataSource")
	public DynamicDataSource dynamicDataSource(Environment env) {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		
		DataSource defaultTargetDataSource = initDefaultDataSource(env);
		Map<String, DataSource> customDataSources = initCustomDataSources(env);
		
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		
		targetDataSources.put("dataSource", defaultTargetDataSource);
		targetDataSources.putAll(customDataSources);
		
		
		for (String key : customDataSources.keySet()) {
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
		}
		
		dynamicDataSource.setDefaultTargetDataSource(defaultTargetDataSource);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		
		return dynamicDataSource;
	}
	
	
	private void dataBinder(DataSource dataSource, Environment env,String prefix){
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);//false
		dataBinder.setIgnoreInvalidFields(false);//false
		dataBinder.setIgnoreUnknownFields(true);//true
		
		Map<String, Object> rpr = new RelaxedPropertyResolver(env, prefix).getSubProperties(".");
		Map<String, Object> values = new HashMap<>(rpr);
		PropertyValues dataSourcePropertyValues = new MutablePropertyValues(values);;
		dataBinder.bind(dataSourcePropertyValues);
	}
	
	@SuppressWarnings("unchecked")
	private DataSource buildDataSource(Object type) {
		
		DataSourceBuilder dataSourceBuilder  = DataSourceBuilder.create();
		
		try {
			if(type!=null){
				Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
				dataSourceBuilder = dataSourceBuilder.type(dataSourceType);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dataSourceBuilder.build();
	}
	
	private Map<String, DataSource> initCustomDataSources(Environment env) {
		
		Map<String, DataSource> customDataSources = new HashMap<>();
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
		String dsPrefixs = propertyResolver.getProperty("names");
		for (String dsPrefix : dsPrefixs.split(",")) {
			String type = propertyResolver.getProperty(dsPrefix + ".type");
			DataSource customDataSource = buildDataSource(type);
			dataBinder(customDataSource,env,"custom.datasource."+dsPrefix);
			customDataSources.put(dsPrefix, customDataSource);
		}
		
		return customDataSources;
	}
	


	private DataSource initDefaultDataSource(Environment env) {
		// 读取主数据源
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		Object type = propertyResolver.getProperty("type");
		DataSource defaultDataSource = buildDataSource(type);
		dataBinder(defaultDataSource,env,"spring.datasource");
		
		return defaultDataSource;
	}
	
	
	
}
