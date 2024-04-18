package com.jasi.sga.util;

import java.text.SimpleDateFormat;

public abstract interface TimeUtils {
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }
}
