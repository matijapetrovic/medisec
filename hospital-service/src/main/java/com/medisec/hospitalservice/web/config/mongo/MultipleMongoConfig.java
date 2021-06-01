package com.medisec.hospitalservice.web.config.mongo;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import lombok.RequiredArgsConstructor;

@Data
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MultipleMongoProperties.class)
public class MultipleMongoConfig {

    private final MultipleMongoProperties mongoProperties;

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(primaryFactory(this.mongoProperties.getPrimary()));
    }

    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate() {
        return new MongoTemplate(secondaryFactory(this.mongoProperties.getSecondary()));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory primaryFactory(final MongoProperties mongo) {
        String connectionString = getMongoProperties().getPrefix() + "://" + mongo.getHost() + ":" + mongo.getPort() + "/" + mongo.getDatabase();
        return new SimpleMongoClientDatabaseFactory(connectionString);
    }

    @Bean
    public MongoDatabaseFactory secondaryFactory(final MongoProperties mongo) {
        String connectionString = getMongoProperties().getPrefix() + "://" + mongo.getHost() + ":" + mongo.getPort() + "/" + mongo.getDatabase();
        return new SimpleMongoClientDatabaseFactory(connectionString);
    }

}