package com.medisec.hospitalservice.web.config.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.medisec.hospitalservice.firewall",
        mongoTemplateRef = "secondaryMongoTemplate")
public class FireWallLogConfig {
}
