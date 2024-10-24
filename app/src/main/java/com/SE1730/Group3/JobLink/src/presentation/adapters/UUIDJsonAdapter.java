package com.SE1730.Group3.JobLink.src.presentation.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.UUID;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UUIDJsonAdapter {
    @ToJson
    public String toJson(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @FromJson
    public UUID fromJson(String uuidStr) {
        return uuidStr != null ? UUID.fromString(uuidStr) : null;
    }
}
