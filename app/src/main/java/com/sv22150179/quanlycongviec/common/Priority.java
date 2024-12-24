package com.sv22150179.quanlycongviec.common;

import com.sv22150179.quanlycongviec.R;

public enum Priority {
    LOW(R.drawable.ic_flag_low),
    NORMAL(R.drawable.ic_flag_normal),
    HIGH(R.drawable.ic_flag_high),
    URGENT(R.drawable.ic_flag_urgent);

    private final int icon;

    private Priority(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }
}


