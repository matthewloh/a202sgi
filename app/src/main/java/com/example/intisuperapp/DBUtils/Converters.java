package com.example.intisuperapp.DBUtils;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return DATE_FORMAT.parse(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        return date == null ? null : DATE_FORMAT.format(date);
    }
}
