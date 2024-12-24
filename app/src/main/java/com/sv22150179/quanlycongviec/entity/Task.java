package com.sv22150179.quanlycongviec.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.sv22150179.quanlycongviec.common.Priority;

import java.util.Date;

@Entity
public class Task {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "task_name")
    public String taskName;

    @ColumnInfo(name = "priority")
    public Priority priority;

    @ColumnInfo(name = "completed")
    public boolean completed;

    @ColumnInfo(name = "due_to")
    public Date dueTo;

    public Task() {
        id = "";
    }

    public Task(@NonNull String id, String taskName, Priority priority, boolean completed, Date dueTo) {
        this.id = id;
        this.taskName = taskName;
        this.priority = priority;
        this.completed = completed;
        this.dueTo = dueTo;
    }
}