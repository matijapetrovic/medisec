package com.medisec.hospitalservice.rules;

import com.medisec.hospitalservice.exception.InvalidRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleHelperService ruleHelperService;

    @Value("${template.rule.path}")
    private String templateRulePath;

    @Value("${rules.path}")
    private String rulesPath;

    public String getTemplate() throws IOException {
        Path path = Paths.get(templateRulePath);
        return Files.readString(path, StandardCharsets.US_ASCII);
    }

    public void save(RuleRequest ruleRequest) throws Exception {
        String name = extractRuleName(ruleRequest.getContent());
        String path = String.format("%s%s.drl",rulesPath, name.trim().replaceAll("\\s+","-"));
        ruleHelperService.saveRule(ruleRequest.getContent(), path);

    }

    private String extractRuleName(String ruleContent) throws InvalidRuleException {

        int ruleStart = ruleContent.indexOf("rule");
        int ruleNameStart = ruleContent.indexOf("\"", ruleStart);
        int ruleNameEnd = ruleContent.indexOf("\"", ruleNameStart+1);

        try {
            return ruleContent.substring(ruleNameStart+1, ruleNameEnd).trim();
        } catch (IndexOutOfBoundsException ex) {
            throw new InvalidRuleException("Failed to extract rule name");
        }

    }
}
