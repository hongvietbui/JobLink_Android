package com.SE1730.Group3.JobLink.src.domain.entities.baseEntities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity<TKey> {
    @PrimaryKey
    @NonNull
    private TKey id;

    private Boolean isDeleted;
    private String deletedAt;
    private String deletedBy;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
}
