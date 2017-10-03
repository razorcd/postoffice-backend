package com.postbox.utils;

import java.util.List;

public class Helper {

    public static String pickOne(List<String> list) {
        return list.get((int)(Math.random() * list.size()));
    }
}
