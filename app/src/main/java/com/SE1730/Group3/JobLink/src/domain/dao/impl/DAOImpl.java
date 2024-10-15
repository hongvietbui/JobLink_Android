package com.SE1730.Group3.JobLink.src.domain.dao.impl;

import com.SE1730.Group3.JobLink.src.domain.dao.IDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DAOImpl<T, Dao> implements IDAO<T> {
    private final Dao dao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public DAOImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void insert(T entity) {
        executorService.execute(() -> {
            try{
                dao.getClass().getMethod("insert", entity.getClass()).invoke(dao, entity);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void update(T entity) {
        executorService.execute(() -> {
            try{
                dao.getClass().getMethod("update", entity.getClass()).invoke(dao, entity);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void delete(T entity) {
        executorService.execute(() -> {
            try {
                dao.getClass().getMethod("delete", entity.getClass()).invoke(dao, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
