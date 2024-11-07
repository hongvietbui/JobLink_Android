package com.SE1730.Group3.JobLink.src.domain.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.SE1730.Group3.JobLink.src.domain.entities.Message;

import java.util.List;
import java.util.UUID;

@Dao
public interface IMessageDAO extends IDAO<Message>{

    @Query("SELECT * FROM Message WHERE senderId = :senderId AND receiverId = :receiverId")
    LiveData<List<Message>> getAllMessageBetweenTwoUser(UUID senderId, UUID receiverId);
}