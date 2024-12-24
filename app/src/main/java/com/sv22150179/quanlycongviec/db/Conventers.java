package com.sv22150179.quanlycongviec.db;

import androidx.room.TypeConverter;
import com.sv22150179.quanlycongviec.common.Priority;

import java.util.Date;

public class Conventers {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Priority fromInt(int value) {
        return Priority.values()[value];
    }

    @TypeConverter
    public static int toInt(Priority priority) {
        return priority.ordinal();
    }
}
