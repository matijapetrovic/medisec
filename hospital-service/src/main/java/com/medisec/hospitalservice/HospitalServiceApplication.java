package com.medisec.hospitalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class HospitalServiceApplication {

	public static void main(String[] args) {SpringApplication.run(HospitalServiceApplication.class, args);}
}

