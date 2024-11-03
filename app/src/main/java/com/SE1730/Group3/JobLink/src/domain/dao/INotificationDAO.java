package com.SE1730.Group3.JobLink.src.domain.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.SE1730.Group3.JobLink.src.domain.entities.Notification;

import java.util.List;

@Dao
public interface INotificationDAO extends IDAO<Notification>{
    @Query("SELECT * FROM Notification ORDER BY id DESC")
    LiveData<List<Notification>> getAllNotifications();
}
