package com.example.qlct;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Home_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        // Tìm ImageView unseen
        ImageView unseen = view.findViewById(R.id.unseen);
        TextView text = view.findViewById(R.id.total_blance);

        // Đặt OnClickListener cho unseen
        unseen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().equals("**********"))
                {
                    unseen.setBackgroundResource(R.drawable.eye);
                    text.setText("1000000 đ");
                }

               else
                {
                    text.setText("**********");
                    unseen.setBackgroundResource(R.drawable.eyedown);
                }


            }
        });

        return view;
    }
}