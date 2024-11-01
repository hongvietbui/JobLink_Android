package com.SE1730.Group3.JobLink.src.domain.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface IDAO<T> {
    @Insert
    void insert(T entity);
    @Update
    void update(T entity);
    @Delete
    void delete(T entity);
}
