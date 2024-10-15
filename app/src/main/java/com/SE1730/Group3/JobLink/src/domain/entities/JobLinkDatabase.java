package com.SE1730.Group3.JobLink.src.domain.entities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.SE1730.Group3.JobLink.src.domain.dao.UserDAO;

@Database(entities = {User.class}, version = 1)
public abstract class JobLinkDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
