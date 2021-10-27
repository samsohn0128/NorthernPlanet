package com.pyhu.northernplanet;

import com.pyhu.northernplanet.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(AppConfig.class)
public class NorthernplanetApplication {

  public static void main(String[] args) {
    SpringApplication.run(NorthernplanetApplication.class, args);
  }

}
