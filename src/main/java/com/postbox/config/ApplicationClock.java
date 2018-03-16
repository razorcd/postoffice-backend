package com.postbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class ApplicationClock {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
//        return Clock.fixed(Instant.EPOCH, ZoneId.systemDefault());
    }
}
