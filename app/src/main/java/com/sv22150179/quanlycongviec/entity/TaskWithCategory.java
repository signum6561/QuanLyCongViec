package com.sv22150179.quanlycongviec.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TaskWithCategory {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "id",
            entityColumn = "category_id"
    )
    public Task task;
}
