package com.medisec.adminservice.email;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final Environment env;

    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject(subject);
        mail.setText(text);
        mailSender.send(mail);
    }

    @Async
    public void sendCertificate(String to, X509Certificate cert) throws MessagingException, CertificateEncodingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        helper.setTo(to);
        helper.setSubject("Issued certificate");
        helper.setText("Your certificate is attached.");

        StringBuilder builder = new StringBuilder();
        builder.append("-----BEGIN CERTIFICATE-----\n");
//        builder.append(Base64.getEncoder().encodeToString(cert.getEncoded()));
        builder.append(DatatypeConverter.printBase64Binary(cert.getEncoded()));
        builder.append("\n-----BEGIN CERTIFICATE-----");

        File tmpFile = File.createTempFile("tmp", ".crt");
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write(builder.toString());
        }

        helper.addAttachment("certificate.crt", tmpFile);
        mailSender.send(message);
    }
}
