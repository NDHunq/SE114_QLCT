package com.example.qlct.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.R;

import java.util.ArrayList;

public class Home_TheGiaoDich_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Home_TheGiaoDich> theGiaoDichList;

    public Home_TheGiaoDich_Adapter(Context context, int layout, ArrayList<Home_TheGiaoDich> theGiaoDichList) {
        this.context = context;
        this.layout = layout;
        this.theGiaoDichList = theGiaoDichList;
    }

    @Override
    public int getCount() {
        return theGiaoDichList.size();
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
        ImageView imageView = (ImageView) view.findViewById(R.id.hinhanh);
        TextView ten = (TextView) view.findViewById(R.id.tendanhmuc);
        TextView sotien = (TextView) view.findViewById(R.id.sotien);
        TextView ngaythang = (TextView) view.findViewById(R.id.ngaythang);
        TextView ghichu = (TextView) view.findViewById(R.id.GhiChu);
        TextView vitien = (TextView) view.findViewById(R.id.tenvi);
        Home_TheGiaoDich theGiaoDich = theGiaoDichList.get(i);
        if(theGiaoDich.Typee.equals("EXPENSE")){
            sotien.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(theGiaoDich.Typee.equals("INCOME")){
            sotien.setTextColor(context.getResources().getColor(R.color.xanhdam));
        }
        else if(theGiaoDich.Typee.equals("TRANSFER")){
            sotien.setTextColor(context.getResources().getColor(R.color.xanhnendam));

        }
        imageView.setImageResource(theGiaoDich.getHinhAnh());
        ten.setText(theGiaoDich.getTenGiaoDich());
        sotien.setText(theGiaoDich.getSoTien()+""+theGiaoDich.getCurrencyUnit());
        ngaythang.setText(theGiaoDich.getNgayThang());
        ghichu.setText(theGiaoDich.getGhiChu());
        vitien.setText(theGiaoDich.getViTien());

        return view;
    }
}
