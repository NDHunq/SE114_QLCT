package com.example.qlct.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qlct.Home.Home_My_wallets;
import com.example.qlct.Home.Home_TheGiaoDich;
import com.example.qlct.Home.Home_TheGiaoDich_Adapter;
import com.example.qlct.Home.Home_Wallet_Information;
import com.example.qlct.Notification.Notificaiton;
import com.example.qlct.R;
import com.example.qlct.Transaction.*;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    ListView listView;
    ArrayList<Home_TheGiaoDich>  theGiaoDichList;
    Home_TheGiaoDich_Adapter theGiaoDichAdap;
    private  void Anhxa()
    {

        theGiaoDichList = new ArrayList<>();
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));

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
        ImageView noti = view.findViewById(R.id.noti);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notificaiton.class);
                startActivity(intent);
            }
        });
        ImageView optionVi = view.findViewById(R.id.optionsVi);
        optionVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Wallet_Information.class);
                startActivity(intent);
            }
        });

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


                Intent intent = new Intent(getActivity(), Home_My_wallets.class);

                // Bắt đầu Activity mới
                startActivity(intent);
            }
        });


        TextView showmore = view.findViewById(R.id.show_more);
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tao intent show more
                Intent ShowMoreIntent = new Intent(getActivity(), TransactionDetail.class);

                // Mo activity transaction details
                startActivity(ShowMoreIntent);
            }
        });
       theGiaoDichAdap= new Home_TheGiaoDich_Adapter(getContext(),R.layout.home_dong_giao_dich,theGiaoDichList);
        listView.setAdapter(theGiaoDichAdap);
        return view;


    }
}