package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Message")
@Builder
public class Message {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private UUID senderId;
    private UUID receiverId;
    private String message;
    private UUID jobId;
    private Boolean isWorker;
}
