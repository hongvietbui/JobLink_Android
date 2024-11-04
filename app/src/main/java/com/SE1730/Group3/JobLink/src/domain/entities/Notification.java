package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity(tableName = "Notification")
@Data
@Builder
@AllArgsConstructor
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String message;
    private String timestamp;
    private boolean isRead;
}
