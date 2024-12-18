package com.SE1730.Group3.JobLink.src.domain.converters;

import androidx.room.TypeConverter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocalDateTimeConverter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @ToJson
    public String toJson(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(FORMATTER) : null;
    }

    @FromJson
    public LocalDateTime fromJson(String dateTimeString) {
        return dateTimeString != null ? LocalDateTime.parse(dateTimeString, FORMATTER) : null;
    }

    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }

    @TypeConverter
    public static LocalDateTime toLocalDateTime(String dateTimeString) {
        return dateTimeString != null ? LocalDateTime.parse(dateTimeString, FORMATTER) : null;
    }
}
