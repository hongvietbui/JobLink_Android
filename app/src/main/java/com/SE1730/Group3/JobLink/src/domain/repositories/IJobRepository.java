package com.SE1730.Group3.JobLink.src.domain.repositories;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
public interface IJobRepository
{
    CompletableFuture<ApiResp<Pagination<JobDTO>>> getJobs(int pageIndex, int pageSize, String sortBy, boolean isDescending, String filter) throws IOException;

}
