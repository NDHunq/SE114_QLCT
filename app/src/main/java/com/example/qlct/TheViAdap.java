package com.example.qlct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TheViAdap extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TheVi> theViList;

    public TheViAdap(Context context, int layout, ArrayList<TheVi> theViList) {
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
        ImageView imageView = (ImageView) view.findViewById(R.id.hinhanhVi);
        TextView ten = (TextView) view.findViewById(R.id.tenVi_theVi);
        TextView sotien = (TextView) view.findViewById(R.id.tienVi_theVi);
        TheVi theVi = theViList.get(i);
        imageView.setImageResource(theVi.getHinhAnh());
        ten.setText(theVi.getTenVi());
        sotien.setText(theVi.getSoTien());
        return view;
    }
}
