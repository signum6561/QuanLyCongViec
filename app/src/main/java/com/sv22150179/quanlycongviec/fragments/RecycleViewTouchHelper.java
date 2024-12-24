package com.sv22150179.quanlycongviec.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.sv22150179.quanlycongviec.R;
import com.sv22150179.quanlycongviec.adapter.TaskAdapter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import org.jetbrains.annotations.NotNull;

public class RecycleViewTouchHelper extends ItemTouchHelper.SimpleCallback {

    TaskAdapter taskAdapter;

    public RecycleViewTouchHelper(TaskAdapter taskAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.taskAdapter = taskAdapter;
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int i) {
        final int position = viewHolder.getAdapterPosition();
        taskAdapter.notifyItemChanged(position);
        if(i == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(taskAdapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    taskAdapter.deleteTask(position);
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    taskAdapter.notifyItemChanged(position);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            taskAdapter.editTask(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(ContextCompat.getColor(taskAdapter.getContext(), R.color.red))
                .addSwipeRightActionIcon(R.drawable.ic_bin)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(taskAdapter.getContext(), R.color.green))
                .addSwipeLeftActionIcon(R.drawable.ic_pen)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
