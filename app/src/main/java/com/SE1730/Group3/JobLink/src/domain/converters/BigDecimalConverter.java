package com.SE1730.Group3.JobLink.src.domain.converters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BigDecimalConverter {
    @ToJson
    String toJson(BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }

    @FromJson
    BigDecimal fromJson(String value) {
        return new BigDecimal(value);
    }
}
