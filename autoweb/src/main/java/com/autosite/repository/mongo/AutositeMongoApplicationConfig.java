package com.autosite.repository.mongo;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

@Configuration
@ComponentScan
// It seems by default the repository pakcages should be underneath. Not totally out side of this package. I dont know. 
@EnableMongoRepositories
public class AutositeMongoApplicationConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "autosite";
	}

	@Override
	public Mongo mongo() throws Exception {

		Mongo mongo = new Mongo();
		mongo.setWriteConcern(WriteConcern.SAFE);

		return mongo;
	}

}
