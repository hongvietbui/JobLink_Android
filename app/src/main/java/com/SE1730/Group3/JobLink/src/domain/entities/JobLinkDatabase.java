package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.SE1730.Group3.JobLink.src.domain.converters.LocalDateTimeConverter;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.INotificationDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;

@Database(entities = {User.class, Message.class, Notification.class}
        , version = 5)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class JobLinkDatabase extends RoomDatabase {
    public abstract IUserDAO userDAO();
    public abstract IMessageDAO messageDAO();
    public abstract INotificationDAO notificationDAO();
}
