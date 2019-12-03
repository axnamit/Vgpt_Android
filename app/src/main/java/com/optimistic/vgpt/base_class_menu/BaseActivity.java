package com.optimistic.vgpt.base_class_menu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.optimistic.vgpt.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
