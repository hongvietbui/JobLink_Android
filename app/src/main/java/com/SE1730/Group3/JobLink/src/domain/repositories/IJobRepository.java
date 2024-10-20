package com.SE1730.Group3.JobLink.src.domain.repositories;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
public interface IJobRepository
{
    CompletableFuture<ApiResp<String>> getJobs(int pageIndex, int pageSize, String sortBy, boolean isDescending, String filter) throws IOException;

}
