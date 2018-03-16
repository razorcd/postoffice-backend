package com.postbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class ApplicationClock {

    /**
     * Application clock. Inject it when current time is needed then mock it in tests.
     */
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
//        return Clock.fixed(Instant.EPOCH, ZoneId.systemDefault());
    }
}
