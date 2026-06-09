package com.xyzfood.Render.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private DateUtil() {
    }

    public static LocalDateTime combine(LocalDate date, String time) {
        return LocalDateTime.of(date, LocalTime.parse(time));
    }

    public static String formatDateTime(LocalDateTime value) {
        return value.format(DATE_TIME_FORMAT);
    }
}

