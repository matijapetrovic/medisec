package com.medisec.hospitalservice.web.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Connector;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SSLConfig {
    private final ResourceLoader resourceLoader;

    @Bean
    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(resourceLoader.getResource("classpath:clientkeystore").getURL()
                        ,"bongcloud".toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

//    @Bean
//    public ServletWebServerFactory servletContainer() throws IOException {
//        Connector httpsX509AuthConnector = getHttpsConnectorWithX509Authentication();
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(httpsX509AuthConnector);
//
//        return tomcat;
//    }
//    public Connector getHttpsConnectorWithX509Authentication() throws IOException {
//        String keyStorePath = resourceLoader.getResource("classpath:clientkeystore").getFile().getAbsolutePath();
//
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setPort(8481);
//        connector.setScheme("https");
//        connector.setSecure(true);
//        connector.setAttribute("SSLEnabled", "true");
//
//        SSLHostConfig sslHostConfig = new SSLHostConfig();
//
//        // VERIFICAATION AND REVOCATION CHECKING
//        sslHostConfig.setCertificateVerification("required");
//        sslHostConfig.setRevocationEnabled(true);
//        sslHostConfig.setSslProtocol("TLS");
//
//        // SERVER CERTIFICATE CONFIGURATION
//        sslHostConfig.setCertificateKeystoreFile(keyStorePath);
//        sslHostConfig.setCertificateKeystorePassword("bongcloud");
//        sslHostConfig.setCertificateKeyAlias("hospital@med.com");
//        sslHostConfig.setCertificateKeyPassword("attack");
//
//        // TRUST CONFIGURATION
//        sslHostConfig.setTrustManagerClassName("com.medisec.hospitalservice.web.config.SSLTrustManager");
//
//        // ADDITIONAL
//        sslHostConfig.setSslProtocol("TLS");
//        sslHostConfig.setDisableSessionTickets(true);
//        connector.addSslHostConfig(sslHostConfig);
//
//        return connector;
//    }
}
