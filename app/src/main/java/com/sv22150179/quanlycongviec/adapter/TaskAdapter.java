package com.sv22150179.quanlycongviec.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sv22150179.quanlycongviec.R;
import com.sv22150179.quanlycongviec.activity.EditTaskActivity;
import com.sv22150179.quanlycongviec.db.TaskDB;
import com.sv22150179.quanlycongviec.entity.Task;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> taskList;
    private final Context context;
    private final DateFormat dateFormatter;
    private final TaskDB taskDB;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
        this.taskDB = TaskDB.getInstance(context);
        this.dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    }

    public Context getContext() {
        return context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.task_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public void editTask(int position) {
        Task task = taskList.get(position);
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra("editId", task.id);
        context.startActivity(intent);
    }

    public void deleteTask(int position) {
        Thread thread = new Thread(() -> {
            taskDB.taskDao().delete(taskList.get(position));
        });
        thread.start();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int i) {
        Task task = taskList.get(i);
        viewHolder.cb_complete.setChecked(task.completed);
        viewHolder.txt_taskName.setText(task.taskName);
        viewHolder.img_flag.setImageResource(task.priority.getIcon());
        viewHolder.txt_dueTo.setText(dateFormatter.format(task.dueTo));

        viewHolder.cb_complete.setOnClickListener(e -> {
            Thread thread = new Thread(() -> {
                String id = taskList.get(i).id;
                taskDB.taskDao().updateStatus(id, viewHolder.cb_complete.isChecked());
            });
            thread.start();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb_complete;
        ImageView img_flag;
        TextView txt_taskName, txt_dueTo;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cb_complete = itemView.findViewById(R.id.cb_complete);
            img_flag = itemView.findViewById(R.id.img_flag);
            txt_dueTo = itemView.findViewById(R.id.txt_dueTo);
            txt_taskName = itemView.findViewById(R.id.txt_taskname);
        }
    }

}
