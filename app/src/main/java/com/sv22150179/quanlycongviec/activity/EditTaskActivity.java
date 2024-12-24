package com.sv22150179.quanlycongviec.activity;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sv22150179.quanlycongviec.R;
import com.sv22150179.quanlycongviec.common.Priority;
import com.sv22150179.quanlycongviec.db.TaskDB;
import com.sv22150179.quanlycongviec.entity.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    TextInputEditText txt_taskName, txt_dueTo;
    TextInputLayout tl_dueTo;
    AutoCompleteTextView sl_priority;
    Button btn_save;
    DateFormat dateFormatter;
    TaskDB taskDB;
    Date dueToDate;
    Priority selectedPriority;
    MaterialDatePicker<Long> dpkInstance;
    String editTaskId;
    boolean taskStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();
    }

    private void init() {
        sl_priority = findViewById(R.id.sl_priority);
        txt_taskName = findViewById(R.id.txt_taskname);
        txt_dueTo = findViewById(R.id.txt_dueTo);
        btn_save = findViewById(R.id.btn_save);
        tl_dueTo = findViewById(R.id.tl_dueTo);

        ArrayAdapter<Priority> adapterItems = new ArrayAdapter<>(this, R.layout.list_item, Priority.values());
        sl_priority.setAdapter(adapterItems);
        editTaskId = getIntent().getStringExtra("editId");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        taskDB = TaskDB.getInstance(getApplicationContext());

        dpkInstance = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Due To")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        dpkInstance.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long value) {
                dueToDate = new Date(value);
                String date = dateFormatter.format(dueToDate);
                txt_dueTo.setText(date);
            }
        });

        tl_dueTo.setEndIconOnClickListener(e -> {
            if(dpkInstance.isVisible()) {
                return;
            }
            dpkInstance.show(getSupportFragmentManager(), "tag");
        });

        taskDB.taskDao().getTaskById(editTaskId).observe(this, task -> {
            dueToDate = task.dueTo;
            txt_dueTo.setText(dateFormatter.format(dueToDate));
            selectedPriority = task.priority;
            sl_priority.setText(selectedPriority.toString(), false);
            selectPriorityFlag(selectedPriority.getIcon());
            txt_taskName.setText(task.taskName);
            taskStatus = task.completed;
        });

        sl_priority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPriority = (Priority) adapterView.getItemAtPosition(i);
                selectPriorityFlag(selectedPriority.getIcon());
            }
        });

        btn_save.setOnClickListener(e -> {
            saveTask();
        });
    }

    private void selectPriorityFlag(int icon) {
        sl_priority.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTask() {
        if(txt_taskName.getText().toString().isEmpty()) {
            txt_taskName.setError("Please fill an empty field");
            return;
        }

        Thread thread = new Thread(() -> {
            Task task = new Task(
                    editTaskId,
                    txt_taskName.getText().toString(),
                    selectedPriority,
                    taskStatus,
                    dueToDate
            );
            taskDB.taskDao().update(task);
        });
        thread.start();
        finish();
    }
}