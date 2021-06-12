package com.medisec.hospitalservice.rules;

import com.medisec.hospitalservice.exception.InvalidRuleException;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class RuleHelperService {

    public void saveRule(String ruleContent, String rulePath) throws Exception {
        validateRule(ruleContent);

        File myFile =  new File(rulePath);
        if(myFile.exists()) {
           throw new Exception();
        }
        myFile.createNewFile();
        FileWriter fw = new FileWriter(myFile);
        fw.write(ruleContent);
        fw.flush();
    }

    public void validateRule(String rule) throws InvalidRuleException {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(rule, ResourceType.DRL);
        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            StringBuilder sb = new StringBuilder();
            sb.append("Rule validation failed. ");
            for (Message message : messages) {
                sb.append(message.getText());
                sb.append("; ");
            }
            throw new InvalidRuleException(sb.toString());
        }
    }
}
