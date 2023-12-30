package com.dailyon.wishcartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableFeignClients
@EnableMongoAuditing
@EnableMongoRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class WishCartServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WishCartServiceApplication.class, args);
  }

  @PostConstruct
  public void setTimezoneToSeoul() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
  }
}
