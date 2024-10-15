package com.SE1730.Group3.JobLink.src.domain.dao.impl;

import androidx.room.RoomDatabase;

import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;

import java.io.IOException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnitOfWorkImpl implements IUnitOfWork {
    private final RoomDatabase database;

    @Override
    public <T, Dao> DAOImpl<T, Dao> getDao(Dao dao) {
        return new DAOImpl<>(dao);
    }

    @Override
    public void executeTransaction(Runnable action) {
        database.runInTransaction(action);
    }


    @Override
    public void close() throws IOException {

    }
}
