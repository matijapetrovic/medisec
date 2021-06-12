package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.exception.InvalidRuleException;
import com.medisec.hospitalservice.rules.RuleRequest;
import com.medisec.hospitalservice.rules.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/rule")
public class RuleControler {
    private final RuleService ruleService;

    @GetMapping("/template")
    public ResponseEntity<String> getTemplate() throws IOException {
        return ResponseEntity.ok(ruleService.getTemplate());
    }

    @PostMapping("")
    public ResponseEntity<Void> createRule(@RequestBody RuleRequest ruleRequest){
        try{
            ruleService.save(ruleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (InvalidRuleException | IOException ex){
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
