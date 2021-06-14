package com.medisec.adminservice.web.template;

import com.medisec.adminservice.domain.template.TemplateDTO;
import com.medisec.adminservice.domain.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/templates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("")
    public ResponseEntity<Void> create(@RequestBody CreateTemplateRequest request) {
        templateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<TemplateDTO>> findAll() {
        return ResponseEntity.ok(templateService.findAll());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        templateService.delete(id);
    }


}
