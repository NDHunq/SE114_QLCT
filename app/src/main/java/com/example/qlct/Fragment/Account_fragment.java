package com.example.qlct.Fragment;

import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;
import com.example.qlct.Home.Home_My_wallets;
import com.example.qlct.Login_Signin;
import com.example.qlct.My_categories;
import com.example.qlct.R;
import com.example.qlct.Setting;
import com.github.mikephil.charting.data.LineRadarDataSet;

public class Account_fragment extends Fragment {

    LinearLayout mywallet;
    LinearLayout logout;
    LinearLayout mycate;
    LinearLayout setting;
    ImageButton editname;
    TextView phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_account_fragment, container, false);
        mywallet=view.findViewById(R.id.mywallet);
        mycate=view.findViewById(R.id.mycates);
        setting=view.findViewById(R.id.setting);
        editname=view.findViewById(R.id.editname);
        phone=view.findViewById(R.id.phone);
        logout=view.findViewById(R.id.logout);
        LoginResponse loginResponse = SharedDaTa.getInstance().getLoginResponse();
        String phoneNumber = loginResponse.getData().getUser().getPhone_number();
        phoneNumber="0"+ phoneNumber.substring(3);
        phone.setText(phoneNumber);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(getActivity(), Login_Signin.class);
                startActivity(myintent);
            }
        });
        mywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(getActivity(),Home_My_wallets.class);
                startActivity(myintent);
            }
        });
        mycate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(getActivity(), My_categories.class);
                startActivity(myintent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(getActivity(), Setting.class);
                startActivity(myintent);

            }
        });

        return view;
    }


}