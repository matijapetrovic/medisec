package com.medisec.hospitalservice.firewall;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FirewallLogRepository extends MongoRepository<FirewallLog, Long> {

}
