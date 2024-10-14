package com.SE1730.Group3.JobLink.src.domain.entities.baseEntities;

import org.threeten.bp.LocalDateTime;

import lombok.Data;

@Data
public class BaseEntity<TKey> {
    private TKey id;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private String deletedBy;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
