package com.sv22150179.quanlycongviec.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sv22150179.quanlycongviec.R;
import com.sv22150179.quanlycongviec.activity.CreateTaskActivity;
import com.sv22150179.quanlycongviec.adapter.TaskAdapter;
import com.sv22150179.quanlycongviec.common.Priority;
import com.sv22150179.quanlycongviec.db.TaskDB;
import com.sv22150179.quanlycongviec.entity.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoFragment extends Fragment {

    Button btn_add;
    TaskDB taskDB;
    RecyclerView rv_tasks;
    TaskAdapter taskAdapter;
    List<Task> taskList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todo, container, false);
        taskDB = TaskDB.getInstance(getContext());
        btn_add = v.findViewById(R.id.btn_add);
        rv_tasks = v.findViewById(R.id.rv_tasks);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, getContext());
        rv_tasks.setAdapter(taskAdapter);
        rv_tasks.setLayoutManager(new LinearLayoutManager(getContext()));

        taskDB.taskDao().getAllTodo().observe(this, tasks -> {
            taskAdapter.setTaskList(tasks);
        });

        btn_add.setOnClickListener(e -> {
            Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
            startActivity(intent);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecycleViewTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(rv_tasks);
        return v;
    }
}