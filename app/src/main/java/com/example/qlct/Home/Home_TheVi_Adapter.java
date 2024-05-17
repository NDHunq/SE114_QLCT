package com.example.qlct.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Home_TheVi_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Home_TheVi> theViList;
    private Activity activity;


    public Home_TheVi_Adapter(Context context, int layout, ArrayList<Home_TheVi> theViList) {
        this.context = context;
        this.layout = layout;
        this.theViList = theViList;

    }


    @Override
    public int getCount() {
        return theViList.size();
    }

    @Override
    public Object getItem(int i) {
        return theViList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.hinhanhVi);
        TextView ten = (TextView) view.findViewById(R.id.tenVi_theVi);
        TextView sotien = (TextView) view.findViewById(R.id.tienVi_theVi);
        Home_TheVi theVi = theViList.get(i);
        imageView.setImageResource(theVi.getHinhAnh());
        ten.setText(theVi.getTenVi());
        sotien.setText(theVi.getSoTien());
        if(theVi.duocchon==1)
        {
            LinearLayout main = (LinearLayout) view.findViewById(R.id.main);
            main.setBackgroundResource(R.drawable.the12dpvienxanh);
        }
        ImageView optionsVi = (ImageView) view.findViewById(R.id.optionsVi); // Assuming you have optionsVi ImageView in your item layout
        optionsVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home_Wallet_Information.class);
                Home_TheVi theViItem = theViList.get(i);
                intent.putExtra("name",theViItem.item.name);
              intent.putExtra("ammount",theViItem.item.amount);
                intent.putExtra("currency",theViItem.item.currency_unit);
                intent.putExtra("id",theViItem.item.id);
                intent.putExtra("start",theViItem.item.create_at);
                intent.putExtra("update",theViItem.item.update_at);
                intent.putExtra("duocchon",theViItem.viduocchon);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
