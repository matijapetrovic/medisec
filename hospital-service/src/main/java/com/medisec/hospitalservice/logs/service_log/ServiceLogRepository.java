package com.medisec.hospitalservice.logs.service_log;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface ServiceLogRepository extends MongoRepository<ServiceLog, String> {

    @Query("{$and:[" +

            "{$or: [{sourceIp: {$regex: ?0}}, {$expr: {$eq:[?0,'']}}]}," +
            "{$or: [{destIp: {$regex: ?1}}, {$expr: {$eq:[?1,'']}}]}," +
            "{$or: [{path: {$regex: ?2}}, {$expr: {$eq:[?2,'']}}]}," +
            "{$or: [{protocol: {$regex: ?3}}, {$expr: {$eq:[?3,'']}}]}," +
            "{$or: [{status: {$eq: ?4}}, {$expr: {$eq:[?4, -1]}}]}," +
            "{$or: [{time: {$gte: ?5}}, {$expr: {$eq:[?5, null]}}]}]}" +
            "]}")
    List<ServiceLog> search(
                            String sourceIp,
                            String destIp,
                            String path,
                            String protocol,
                            int status,
                            Date time
                            );
}
