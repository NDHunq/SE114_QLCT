package com.example.qlct;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class Account_fragment extends Fragment {

    LinearLayout mywallet;
    LinearLayout mycate;
    LinearLayout setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_account_fragment, container, false);
        mywallet=view.findViewById(R.id.mywallet);
        mycate=view.findViewById(R.id.mycates);
        setting=view.findViewById(R.id.setting);
        mywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(getActivity(),Notificaiton.class);
                startActivity(myintent);
                System.out.println("CCCCCCC");
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



        return inflater.inflate(R.layout.fragment_account_fragment, container, false);
    }


}