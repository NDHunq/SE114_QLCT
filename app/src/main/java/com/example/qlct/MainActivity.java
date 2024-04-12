package com.example.qlct;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.graphics.Color;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.qlct.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private void updateButtonBackgrounds(int selectedButtonId) {
        // Danh sách các button
        int[] buttonIds;

        buttonIds = new int[]{R.id.thehome, R.id.theanalysis, R.id.thebudget, R.id.theaccount};

        // Duyệt qua từng button
        for (int id : buttonIds) {
            LinearLayout button = findViewById(id);
            if (id == selectedButtonId) {
                // Nếu button được chọn, đặt background là rounded_rectangle
                button.setBackgroundResource(R.drawable.the10dp);
            } else {
                // Nếu button không được chọn, đặt background là màu trắng
                button.setBackgroundColor(Color.parseColor("#F7F3F3"));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ConstraintLayout button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một instance của Category_Add
                Intent intent;
                intent = new Intent(MainActivity.this, Category_Add.class);
                startActivity(intent);
            }
        });
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tạo một instance của HomeFragment
        Home_fragment homeFragment = new Home_fragment();

        // Sử dụng FragmentManager và FragmentTransaction để thêm HomeFragment vào FrameLayout container
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, homeFragment).commit();
        LinearLayout homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonBackgrounds(R.id.thehome);
                // Tạo một instance mới của HomeFragment
                Home_fragment homeFragment = new Home_fragment();

                // Sử dụng FragmentManager và FragmentTransaction để thay thế HomeFragment vào FrameLayout container
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, homeFragment).commit();
            }
        });
        LinearLayout anabutton = findViewById(R.id.analysis_button);
        anabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonBackgrounds(R.id.theanalysis);
                // Tạo một instance mới của HomeFragment
                Analysis_fragment analysisFragment = new Analysis_fragment();

                // Sử dụng FragmentManager và FragmentTransaction để thay thế HomeFragment vào FrameLayout container
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, analysisFragment).commit();
            }
        });
        LinearLayout bugbutton = findViewById(R.id.budget_button);
        bugbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonBackgrounds(R.id.thebudget);
                // Tạo một instance mới của HomeFragment
                Budget_fragment budgetFragment = new Budget_fragment();

                // Sử dụng FragmentManager và FragmentTransaction để thay thế HomeFragment vào FrameLayout container
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, budgetFragment).commit();
            }
        });
        LinearLayout accbutton = findViewById(R.id.account_button);
        accbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonBackgrounds(R.id.theaccount);
                // Tạo một instance mới của HomeFragment
                Account_fragment accountFragment = new Account_fragment();

                // Sử dụng FragmentManager và FragmentTransaction để thay thế HomeFragment vào FrameLayout container
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, accountFragment).commit();

            }
        });
    }
}