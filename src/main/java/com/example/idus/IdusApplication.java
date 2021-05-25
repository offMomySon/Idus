package com.example.idus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Slf4j
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class IdusApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("now time - " + LocalDateTime.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(IdusApplication.class, args);
    }
}
