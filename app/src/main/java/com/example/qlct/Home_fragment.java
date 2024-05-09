package com.example.qlct;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    ListView listView;
    ArrayList<Home_TheGiaoDich>  theGiaoDichList;
    Home_TheGiaoDich_Adapter theGiaoDichAdap;
    ArrayList<Home_TheTopSpent> theTopSpentList;
    Home_TheTopSpent_Adapter theTopSpentAdap;
    private  void Anhxa2()
    {
        theTopSpentList = new ArrayList<>();
        theTopSpentList.add(new Home_TheTopSpent(R.drawable.budget,"Ăn uống","5000000 đ","20%"));
        theTopSpentList.add(new Home_TheTopSpent(R.drawable.budget,"Ăn uống","5000000 đ","20%"));
        theTopSpentList.add(new Home_TheTopSpent(R.drawable.budget,"Ăn uống","5000000 đ","20%"));
    }
    private  void Anhxa()
    {

        theGiaoDichList = new ArrayList<>();
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));
        theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.budget,"Tiền lương","5000000 đ","20/10/2021","Tiền lương tháng 10","Ví tiền"));


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Anhxa();
        Anhxa2();
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
                Intent intent = new Intent(getActivity(),Notificaiton.class);
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
        TextView month= view.findViewById(R.id.month);
        TextView year= view.findViewById(R.id.year);
        ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
        month.setBackgroundTintList(colorStateList);
        ColorStateList colorStateList2= ColorStateList.valueOf(Color.parseColor("#ACACAC"));
        year.setBackgroundTintList(colorStateList2);

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                month.setBackgroundTintList(colorStateList);
                ColorStateList colorStateList2= ColorStateList.valueOf(Color.parseColor("#ACACAC"));
                year.setBackgroundTintList(colorStateList2);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                year.setBackgroundTintList(colorStateList);
                ColorStateList colorStateList2= ColorStateList.valueOf(Color.parseColor("#ACACAC"));
                month.setBackgroundTintList(colorStateList2);
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
       theGiaoDichAdap= new Home_TheGiaoDich_Adapter(getContext(),R.layout.home_dong_giao_dich,theGiaoDichList);
        listView.setAdapter(theGiaoDichAdap);
        setListViewHeightBasedOnChildren(listView);
        ListView listView2 = view.findViewById(R.id.listView2);
        theTopSpentAdap = new Home_TheTopSpent_Adapter(getContext(),R.layout.home_dong_topspent,theTopSpentList);
        listView2.setAdapter(theTopSpentAdap);
        setListViewHeightBasedOnChildren(listView2);

        return view;



    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}