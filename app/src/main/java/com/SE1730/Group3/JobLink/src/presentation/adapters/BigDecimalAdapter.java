package com.SE1730.Group3.JobLink.src.presentation.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.math.BigDecimal;

public class BigDecimalAdapter {
    @ToJson
    String toJson(BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }

    @FromJson
    BigDecimal fromJson(String value) {
        return new BigDecimal(value);
    }
}
