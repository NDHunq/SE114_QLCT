package com.example.qlct.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;


import com.bumptech.glide.Glide;
import com.example.qlct.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
       String url=theGiaoDich.getHinhAnh();
        Glide.with(context).load(url).into(imageView);
        ten.setText(theGiaoDich.getTenGiaoDich());
        String currency = theGiaoDich.getCurrencyUnit();
        switch (currency){
            case "đ":
                currency = "VND";
                break;
            case "$":
                currency = "USD";
                break;
            case "¥":
                currency = "CNY";
                break;
            case "€":
                currency = "EUR";
                break;
        }
        String amount = formatCurrency(Double.parseDouble(theGiaoDich.getSoTien()), currency);
        sotien.setText(amount+""+theGiaoDich.getCurrencyUnit());
        ngaythang.setText(theGiaoDich.getNgayThang());
        ghichu.setText(theGiaoDich.getGhiChu());
        vitien.setText(theGiaoDich.getViTien());

        return view;
    }

    public static String formatCurrency(double amount, String currency) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        // Set the thousands separator depending on the currency
        if ("VND".equals(currency)) {
            symbols.setGroupingSeparator('.');
        } else {
            symbols.setGroupingSeparator(',');
        }

        // Always use a dot for the decimal separator
        symbols.setDecimalSeparator('.');

        // Create a DecimalFormat with the desired symbols and format the amount
        DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);
        return formatter.format(amount);
    }

}
