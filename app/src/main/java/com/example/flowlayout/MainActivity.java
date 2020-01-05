package com.example.flowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flowlayout_lib.FlowLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlowLayout flowLayout = findViewById(R.id.flow_layout);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(this);
            textView.setTextSize(20);
            StringBuilder stringBuilder = new StringBuilder();
            int t = random.nextInt(3);
            for (int j = 0; j < t; j++) {
                stringBuilder.append("标题");
            }
            textView.setText(stringBuilder.toString() + random.nextInt(10000));
            textView.setBackgroundResource(R.color.gray);
            textView.setPadding(5, 5, 5, 5);
            FlowLayout.MarginLayoutParams params = new FlowLayout.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flowLayout.addView(textView, params);
        }
    }
}
