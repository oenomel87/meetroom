package io.dane.meetroom.tool;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTool {

    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm";

    public static String convertDateToString(LocalDateTime date) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern(DATEFORMAT));
    }

    public static LocalDateTime convertStringToDate(String date) {
        return StringUtils.hasText(date) ? LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATEFORMAT)) : null;
    }
}
