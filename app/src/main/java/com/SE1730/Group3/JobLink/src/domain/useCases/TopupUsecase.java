package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.TopupReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class TopupUsecase {
    private final IUserRepository userRepository;

    @Inject
    public TopupUsecase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<ApiResp<List<TopUpDTO>>> execute(Date fromDate, Date toDate) throws IOException {
        TopupReqDTO request = TopupReqDTO.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        return userRepository.getUserTransaction(request);
    }
}
