package com.server.signalisation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class MongoConfig extends AbstractMongoClientConfiguration{

	@Value("${spring.data.mongodb.host}")
	private String host;
	
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
	@Override
	protected String getDatabaseName() {
		return database;
	}
	
	@Bean
	public GridFsTemplate gridFsTemplate() {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter(null, null, null));
	}

}
