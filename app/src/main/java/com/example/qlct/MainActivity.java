package com.example.qlct;


import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;


import android.content.pm.PackageManager;
import android.os.Build;

import android.os.StrictMode;
import android.util.Log;


import android.util.Log;

import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Entity.SharedPrefManager;
import com.example.qlct.API_Entity.UpdateDeviceTokenEntity;
import com.example.qlct.API_Utils.DeviceTokenAPIUtil;

import com.example.qlct.API_Utils.WalletAPIUtil;



import com.example.qlct.Category.Category_Add;



import com.example.qlct.API_Entity.SharedPrefManager;

import com.example.qlct.Fragment.Account_fragment;
import com.example.qlct.Fragment.Analysis_fragment;
import com.example.qlct.Fragment.Budget_fragment;
import com.example.qlct.Fragment.Home_fragment;
import com.example.qlct.Transaction.AddTransaction;
import com.example.qlct.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    String tenvi = "Ví chính";
    Home_fragment homeFragment = new Home_fragment();
    double   ammount = 0;
    double tongsovi=0;
    String currency_unit = "đ";

    private void updateButtonBackgrounds(int selectedButtonId) {
        // Danh sách các button

        int[] buttonIds = new int[]{R.id.thehome, R.id.theanalysis, R.id.thebudget, R.id.theaccount};


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
    double TongTien=0;
    doitiente doitien = new doitiente();
    private void  setBlance()
    {
        try {

            ArrayList<GetAllWalletsEntity> parseAPIList = new WalletAPIUtil().getAllWalletAPI();
            tongsovi = parseAPIList.size();
            //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
            for (GetAllWalletsEntity item : parseAPIList) {
                Log.d("sdf",String.valueOf(TongTien));
                String donvi = "";
                double amount = Double.parseDouble(item.amount);
                if(item.currency_unit.equals("VND"))
                {donvi=" ₫";
                    TongTien+= amount;}
                if(item.currency_unit.equals("USD"))
                {donvi=" $";
                    String doi = String.valueOf(doitien.getUSDtoVND());
                    Log.d("sdf",String.valueOf(doitien.USDtoVND));
                    TongTien += amount*doitien.getUSDtoVND();}
                if(item.currency_unit.equals("EUR"))
                {donvi=" €";
                    TongTien += amount*doitien.getUERtoVND();}
                if(item.currency_unit.equals("CNY"))
                {donvi=" ¥";
                    TongTien += amount*doitien.getCNYtoVND();}



            }

        }
        catch (Exception e) {
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disable strict mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Bundle bundle = new Bundle();

        tenvi = getIntent().getStringExtra("tenvi");
        ammount = getIntent().getDoubleExtra("ammount",-1);
currency_unit = getIntent().getStringExtra("currency_unit");
tongsovi = getIntent().getDoubleExtra("tongsovi",0);

        double tongsovi2 = getIntent().getDoubleExtra("tongsovi",0);


        if(ammount == -1){
            setBlance();
            bundle.putString("tenvi", "Total");

            bundle.putDouble("ammount", TongTien);
            bundle.putString("currency_unit", " đ");
            bundle.putDouble("tongsovi", tongsovi);
            Log.d("Truyen du lieu", "loi roi");

        }
        else
        {
            Log.d("Truyen du lieu", String.valueOf(ammount));
        bundle.putString("tenvi", tenvi);
        bundle.putDouble("ammount", ammount);
            bundle.putDouble("tongsovi", tongsovi2);

        if(currency_unit.equals("VND"))
        {
            bundle.putString("currency_unit", " ₫");
        }
        if(currency_unit.equals("USD"))
        {
            bundle.putString("currency_unit", " $");
        }
        if(currency_unit.equals("EUR"))
        {
            bundle.putString("currency_unit", " €");
        }
        if(currency_unit.equals("CNY"))
        {
            bundle.putString("currency_unit", " ¥");
        }
            bundle.putDouble("tongsovi", tongsovi);
        }


        //Get device token and send to backend for notification service in the future
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Error_firebase_messaging", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("DEVICE_TOKEN", token);

                        new DeviceTokenAPIUtil().updateDeviceTokenAPI(new UpdateDeviceTokenEntity(token));
                    }
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

        homeFragment.setArguments(bundle);

        // Sử dụng FragmentManager và FragmentTransaction để thêm HomeFragment vào FrameLayout container
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, homeFragment).commit();
        LinearLayout homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonBackgrounds(R.id.thehome);
                // Tạo một instance mới của HomeFragment



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
                // Kiểm tra xem người dùng đã đăng nhập hay chưa
                try {
                    if (SharedPrefManager.getInstance(MainActivity.this).getMyVariable() != null) {
                        Log.d("Account", "User is logged in");
                        updateButtonBackgrounds(R.id.theaccount);
                        // Tạo một instance mới của HomeFragment
                        Account_fragment accountFragment = new Account_fragment();

                        // Sử dụng FragmentManager và FragmentTransaction để thay thế HomeFragment vào FrameLayout container
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, accountFragment).commit();
                    } else {
                        Log.d("Account", "User is not logged in");
                        // Nếu người dùng chưa đăng nhập, chuyển hướng họ đến LoginActivity
                        startActivity(new Intent(MainActivity.this, Login_Signin.class));
                    }
                } catch (Exception e) {
                    // Xử lý lỗi ở đây
                    Log.d("Error", e.toString());
                }
            }
        });


        FloatingActionButton addtrans = findViewById(R.id.add_transaction_button);
        addtrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddTransIntent = new Intent(MainActivity.this, AddTransaction.class);
                startActivity(AddTransIntent);
            }
        });

    }
}