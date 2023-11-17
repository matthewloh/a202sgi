package com.example.intisuperapp.DBUtils;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(TimeConstants.DATE_FORMAT, Locale.getDefault());

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
