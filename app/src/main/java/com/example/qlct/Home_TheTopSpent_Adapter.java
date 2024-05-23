package com.example.qlct;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home_TheTopSpent_Adapter  extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Home_TheTopSpent> theTopSpentList;

    public Home_TheTopSpent_Adapter(Context context, int layout, ArrayList<Home_TheTopSpent> theTopSpentList) {
        this.context = context;
        this.layout = layout;
        this.theTopSpentList = theTopSpentList;
    }

    @Override
    public int getCount() {
        return theTopSpentList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        ImageView image = view.findViewById(R.id.hinhanhVi_Thetop);
        TextView ten = view.findViewById(R.id.tenVi_Thetop);
        TextView sotien = view.findViewById(R.id.tienVi_Thetop);
        TextView percent = view.findViewById(R.id.phantramVi_Thetop);
        Home_TheTopSpent theTopSpent = theTopSpentList.get(i);
        String url =theTopSpent.getHinhAnh();
        Glide.with(context).load(url).into(image);

        ten.setText(theTopSpent.getTenCategory());
        sotien.setText(theTopSpent.getSoTien());
        percent.setText(theTopSpent.getPercent());


        return view;
    }
}
