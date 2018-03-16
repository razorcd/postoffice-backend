package com.postbox.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

public class Helper {

    public static String pickOne(List<String> list) {
        return list.get((int)(Math.random() * list.size()));
    }

    public static Instant randomInstant(Instant min, Instant max) {
        Long randomMs = new Random().longs(min.toEpochMilli(), max.toEpochMilli())
                .findFirst().getAsLong();
        return Instant.ofEpochMilli(randomMs);
    }
}
