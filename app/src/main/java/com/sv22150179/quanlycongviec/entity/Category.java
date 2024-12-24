package com.sv22150179.quanlycongviec.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category extends BaseEntity {
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "color")
    public String color;
}
