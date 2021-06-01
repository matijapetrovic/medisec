package com.medisec.hospitalservice.web.config.mongo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

import javax.annotation.PostConstruct;

@Data
@ConfigurationProperties(prefix = "mongodb")
@AllArgsConstructor
@RequiredArgsConstructor
public class MultipleMongoProperties {

    private final MongoProperties primary = new MongoProperties();
    private final MongoProperties secondary = new MongoProperties();

    @Value("${spring.data.mongodb.prefix}")
    private String prefix;

    @Value("${spring.data.mongodb.host}")
    private String dbHost;

    @Value("${spring.data.mongodb.port}")
    private int dbPort;

    @Value("${spring.data.mongodb.primary.database}")
    private String firstDB;

    @Value("${spring.data.mongodb.secondary.database}")
    private String secondDB;


    @PostConstruct
    public void init() {
        primary.setHost(getDbHost());
        primary.setPort(getDbPort());
        primary.setDatabase(getFirstDB());

        secondary.setHost(getDbHost());
        secondary.setPort(getDbPort());
        secondary.setDatabase(getSecondDB());
    }

}