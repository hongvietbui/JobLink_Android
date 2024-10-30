package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.SE1730.Group3.JobLink.src.domain.converters.DateTimeConverter;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;

@Database(entities = {User.class, Message.class}
        , version = 2)
@TypeConverters({DateTimeConverter.class})
public abstract class JobLinkDatabase extends RoomDatabase {
    public abstract IUserDAO userDAO();
    public abstract IMessageDAO messageDAO();
}
