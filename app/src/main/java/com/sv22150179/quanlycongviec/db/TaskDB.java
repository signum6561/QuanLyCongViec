package com.sv22150179.quanlycongviec.db;

import android.content.Context;
import androidx.room.*;
import com.sv22150179.quanlycongviec.entity.Task;

@Database(entities = {Task.class}, version = 2)
@TypeConverters(Conventers.class)
public abstract class TaskDB extends RoomDatabase {
    private static volatile TaskDB instance;

    public static TaskDB getInstance(Context context) {
        if (instance == null){
            synchronized (TaskDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDB.class,"MY_ROOM_DATABASE").build();
                }
            }
        }
        return instance;
    }
    public abstract TaskDao taskDao();
}
