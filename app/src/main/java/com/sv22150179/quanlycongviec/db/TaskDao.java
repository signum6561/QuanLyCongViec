package com.sv22150179.quanlycongviec.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.sv22150179.quanlycongviec.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("select * from task where completed = 0 order by priority desc")
    LiveData<List<Task>> getAllTodo();

    @Query("select * from task where completed = 1 order by priority desc")
    LiveData<List<Task>> getAllCompleted();

    @Query("select * from task where id = :id")
    LiveData<Task> getTaskById(String id);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Query("update task set completed = :status where id = :id")
    void updateStatus(String id, boolean status);

    @Delete
    void delete(Task task);

    @Query("delete from task where completed = 1")
    void deleteAllCompleted();
}
