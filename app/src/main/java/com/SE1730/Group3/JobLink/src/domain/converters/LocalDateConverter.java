package com.SE1730.Group3.JobLink.src.domain.converters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocalDateConverter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @ToJson
    public String toJson(LocalDate localDate) {
        return localDate != null ? localDate.format(FORMATTER) : null;
    }

    @FromJson
    public LocalDate fromJson(String dateString) {
        return dateString != null ? LocalDate.parse(dateString, FORMATTER) : null;
    }
}
