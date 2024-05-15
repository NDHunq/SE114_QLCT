package com.example.qlct.Transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.R;
import com.example.qlct.Transaction.TransactionDetail_TheGiaoDich;

import java.util.ArrayList;

public class TransactionDetail_TheGiaoDich_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TransactionDetail_TheGiaoDich> theGiaoDichList;

    public TransactionDetail_TheGiaoDich_Adapter(Context context, int layout, ArrayList<TransactionDetail_TheGiaoDich> theGiaoDichList) {
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
        TransactionDetail_TheGiaoDich theGiaoDich = theGiaoDichList.get(i);

        imageView.setImageResource(theGiaoDich.getHinhAnh());
        ten.setText(theGiaoDich.getGiaoDich().getCategory_name());
        sotien.setText(theGiaoDich.getSoTien());
        ngaythang.setText(theGiaoDich.getNgayThang());
        ghichu.setText(theGiaoDich.getGhiChu());
        vitien.setText(theGiaoDich.getViTien());
        return view;
    }
}
