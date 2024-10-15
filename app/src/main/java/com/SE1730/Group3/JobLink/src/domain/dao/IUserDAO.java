package com.SE1730.Group3.JobLink.src.domain.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.SE1730.Group3.JobLink.src.domain.entities.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface IUserDAO extends IDAO<User>{
    @Query("SELECT * FROM Users")
    List<User> getAll();

    @Query("SELECT * FROM Users WHERE id = :userId")
    User getUserById(UUID userId);
}
