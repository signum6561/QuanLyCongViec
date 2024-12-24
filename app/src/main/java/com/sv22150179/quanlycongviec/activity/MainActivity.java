package com.sv22150179.quanlycongviec.activity;

import android.os.Bundle;

import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sv22150179.quanlycongviec.R;
import com.sv22150179.quanlycongviec.fragments.CompletedFragment;
import com.sv22150179.quanlycongviec.fragments.TodoFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    boolean status = false;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        loadFragment(new TodoFragment());
        navView.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.nav_tasks);
    }

    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        if (item.getItemId() == R.id.nav_tasks) {
            fragment = new TodoFragment();
            loadFragment(fragment);
            return true;
        } else if (item.getItemId() == R.id.nav_setting) {
            fragment = new CompletedFragment();
            loadFragment(fragment);
            return true;
        }
        return false;
    };
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}