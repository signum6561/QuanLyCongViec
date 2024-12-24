package com.sv22150179.quanlycongviec.entity;

import androidx.room.ColumnInfo;

import java.time.LocalDateTime;

public class BaseEntity {
    @ColumnInfo(name = "created_at")
    public LocalDateTime createdAt;

    @ColumnInfo(name = "updated_at")
    public LocalDateTime updateAt;
}
