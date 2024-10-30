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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

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
                    Log.d("LoginDebug", "Login response status: " + resp.getStatus());

                    if (resp.getStatus() == 200) {
                        LoginRespDTO loginRespDTO = resp.getData();

                        Log.d("LoginDebug", "Received accessToken: " + loginRespDTO.getAccessToken());

                        editor.putString("accessToken", loginRespDTO.getAccessToken());
                        editor.apply();

                        return userRepository.getCurrentUser()
                                .map(userResp -> {
                                    Log.d("LoginDebug", "getCurrentUser response status: " + userResp.getStatus());

                                    if (userResp.getStatus() == 200) {
                                        Log.d("LoginDebug", "User data: " + userResp.getData().toString());

                                        userDAO.deleteAllUsers();
                                        userDAO.insert(IUserMapper.INSTANCE.toUser(userResp.getData()));
                                        return true;
                                    } else {
                                        Log.d("LoginDebug", "Failed to retrieve user with status: " + userResp.getStatus());
                                        return false;
                                    }
                                })
                                .onErrorReturn(throwable -> {
                                    Log.e("LoginDebug", "Error retrieving current user", throwable);
                                    return false;
                                });

                    } else {
                        Log.d("LoginDebug", "Login failed with status: " + resp.getStatus());
                        return Observable.just(false);
                    }
                })
                .onErrorReturn(throwable -> {
                    Log.e("LoginDebug", "Error during login process", throwable);
                    return false;
                });
    }

}
