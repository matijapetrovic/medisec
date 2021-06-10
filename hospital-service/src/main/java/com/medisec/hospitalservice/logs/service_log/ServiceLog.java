package com.medisec.hospitalservice.logs.service_log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Document(collection = "service_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceLog {
    @Id
    @Field("id")
    @GeneratedValue
    private String id;

    @Field("source_ip")
    private String sourceIp;

    @Field("dest_ip")
    private String destIp;

    @Field("path")
    private String path;

    @Field("protocol")
    private String protocol;

    @Field("status")
    private int status;

    @Field("time")
    private Date time;

    @Field("packet_size")
    private int packetSize;

    @Field("type")
    private LogType type;

    public String parsePathResource() {
        String resource = path.split(" ")[1];
        resource = resource.split("\\?")[0].substring(1);
        return resource;
    }

    public String parseUsernameParam() {
        String resource = path.split(" ")[1];
        resource = resource.split("\\?")[1];
        String[] params =  resource.split("&");
        for(String param: params) {
            if (param.split("=")[0].equals("username"))
                return param.split("=")[1];
        }
        return resource;
    }

}

enum LogType {
    INFORMATION, WARNING, ERROR
}
