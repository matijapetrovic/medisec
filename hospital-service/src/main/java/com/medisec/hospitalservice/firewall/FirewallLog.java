package com.medisec.hospitalservice.firewall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "firewall_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirewallLog {
    @Id
    @Field("id")
    @JsonIgnore
    private Long id;

    @Field("message")
    private String message;
}
