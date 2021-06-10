package com.medisec.hospitalservice.firewall;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@NoArgsConstructor
public class FirewallLogRequest {
    @NotBlank
    private String sourceIp;

    @NotBlank
    private String destIp;

    @NotBlank
    private String path;

    @NotBlank
    private String protocol;

    @NotBlank
    private int status;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:SS")
    private Date time;

    @PositiveOrZero
    private int packetSize;

    @NotBlank
    private String type;

}
