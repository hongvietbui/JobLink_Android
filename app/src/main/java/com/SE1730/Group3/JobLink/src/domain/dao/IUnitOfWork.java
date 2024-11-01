package com.SE1730.Group3.JobLink.src.domain.dao;

import com.SE1730.Group3.JobLink.src.domain.dao.impl.DAOImpl;

import java.io.Closeable;

public interface IUnitOfWork extends Closeable {
    <T, Dao> DAOImpl<T, Dao> getDao(Dao dao);
    void executeTransaction(Runnable action);
}
