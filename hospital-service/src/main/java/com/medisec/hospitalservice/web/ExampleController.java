package com.medisec.hospitalservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping("/api/patients")
    public String getDoctor() {
        return "Welcome doctor";
    }

    @GetMapping("admin")
    public String getAdmin() {
        return "Welcome admin";
    }
}
