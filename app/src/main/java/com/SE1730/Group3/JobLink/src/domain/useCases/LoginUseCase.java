package com.SE1730.Group3.JobLink.src.domain.useCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.SE1730.Group3.JobLink.src.data.mappers.IUserMapper;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class LoginUseCase {
    private final IUserRepository userRepository;
    private final IUserDAO userDAO;

    private final SharedPreferences.Editor editor;

    @Inject
    public LoginUseCase(IUserRepository userRepository, IUserDAO userDAO, SharedPreferences sharedPreferences) {
        this.userRepository = userRepository;
        this.userDAO = userDAO;
        this.editor = sharedPreferences.edit();
    }

    public Observable<Boolean> execute(String username, String password) throws IOException {
        LoginReqDTO request = LoginReqDTO.builder()
                .username(username)
                .password(password)
                .build();

        return userRepository.loginUser(request)
            .concatMap(resp -> {

                if (resp.getStatus() == 200) {
                    LoginRespDTO loginRespDTO = resp.getData();

                    editor.putString("accessToken", loginRespDTO.getAccessToken());
                    editor.apply();

                    return userRepository.getCurrentUser()
                            .map(userResp -> {

                                if (userResp.getStatus() == 200) {
                                    if(!userDAO.isUserExisted(userResp.getData().getId())){
                                        userDAO.insert(IUserMapper.INSTANCE.toUser(userResp.getData()));
                                    }
                                    return true;
                                } else {
                                    return false;
                                }
                            })
                            .onErrorReturn(throwable -> false);

                } else {
                    return Observable.just(false);
                }
            })
            .onErrorReturn(throwable -> false);
    }
}
