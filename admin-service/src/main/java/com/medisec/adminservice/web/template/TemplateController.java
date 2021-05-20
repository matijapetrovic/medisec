package com.medisec.adminservice.web.template;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/templates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateController {


    @PostMapping
    public void create() {

    }


}
