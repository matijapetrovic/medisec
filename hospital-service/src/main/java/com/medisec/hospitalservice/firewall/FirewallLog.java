package com.medisec.hospitalservice.firewall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "firewall_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirewallLog {
    @Id
    @Field("id")
    @JsonIgnore
    private Long id;

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

    public String getPathResource() {
        String resource = path.split(" ")[1];
        resource = resource.split("\\?")[0].substring(1);
        return resource;
    }

    public String getUsername() {
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
