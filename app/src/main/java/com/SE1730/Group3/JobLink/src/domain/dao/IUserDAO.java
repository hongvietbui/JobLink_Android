package com.SE1730.Group3.JobLink.src.domain.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.SE1730.Group3.JobLink.src.domain.entities.User;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface IUserDAO extends IDAO<User>{
    @Query("SELECT * FROM Users")
    List<User> getAll();

    @Query("SELECT * FROM Users LIMIT 1")
    Single<User> getCurrentUser();

    @Query("SELECT EXISTS(SELECT 1 FROM Users WHERE id = :userId)")
    Boolean isUserExisted(UUID userId);

    @Query("DELETE FROM Users")
    void deleteAllUsers();
}