package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;
import com.example.qlct.API_Entity.UserProfile;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.example.qlct.Fragment.Account_fragment;

public class Setting extends AppCompatActivity {
LinearLayout changemail;
LinearLayout currency;
TextView phone;
LinearLayout changepass;
ImageButton backsetting;
TextView currency1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        UserAPiUtil userAPiUtil = new UserAPiUtil();
        UserProfile userProfile=userAPiUtil.getUserProfile();

        currency1=findViewById(R.id.currency1);
        currency=findViewById(R.id.currency);
        phone=findViewById(R.id.phone);
//        changemail=findViewById(R.id.changemail);
        changepass=findViewById(R.id.changpass);
        backsetting=findViewById(R.id.backsetting);
        String phoneNumber = userProfile.getData().getPhone_number();
       phoneNumber="0"+ phoneNumber.substring(3);
        phone.setText(phoneNumber);
        currency1.setText(userProfile.getData().getCurrency_unit());
//        currency.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.d("Setting", "onCreate: ");
//                Intent myintent=new Intent(Setting.this,Currency.class);
//                startActivity(myintent);
//                Log.d("Setting", "onCreate1: ");
//            }
//        });
        backsetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
//        changemail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myintent=new Intent(Setting.this, verification.class);
//                startActivity(myintent);
//            }
//        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Setting.this,NewPassword.class);
                startActivity(myintent);
            }
        });
    }
}