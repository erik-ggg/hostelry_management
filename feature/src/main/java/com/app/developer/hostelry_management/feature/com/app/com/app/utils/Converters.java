package com.app.developer.hostelry_management.feature.com.app.com.app.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long toTimestamp(Date date) {
        if (date == null) {
            return  null;
        } else {
            return date.getTime();
        }
    }
}
