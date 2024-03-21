package com.example.qlct;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    ListView listView;
    ArrayList<TheGiaoDich>  theGiaoDichList;
    TheGiaoDichAdap theGiaoDichAdap;
    private  void Anhxa()
    {

        theGiaoDichList = new ArrayList<>();
        theGiaoDichList.add(new TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Anhxa();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        listView = view.findViewById(R.id.listView);
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
        // Tìm TextView Seeall
        TextView seeAll = view.findViewById(R.id.Seeall);

        // Đặt OnClickListener cho Seeall
        seeAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Tạo một Intent mới để mở MyWalletsActivity
                Intent event;


                Intent intent = new Intent(getActivity(), My_wallets.class);

                // Bắt đầu Activity mới
                startActivity(intent);
            }
        });
       theGiaoDichAdap= new TheGiaoDichAdap(getContext(),R.layout.dong_giao_dich,theGiaoDichList);
        listView.setAdapter(theGiaoDichAdap);
        return view;
    }
}