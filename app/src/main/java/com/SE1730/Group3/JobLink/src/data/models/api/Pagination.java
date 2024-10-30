package com.SE1730.Group3.JobLink.src.data.models.api;

import java.util.List;

import lombok.Data;

@Data
public class Pagination<T> {
    private Integer totalItems;
    private Integer totalPages;
    private Integer pageSize;
    private Integer pageIndex;
    private List<T> items;
    private Boolean hasPreviousPage;
    private Boolean hasNextPage;
}
