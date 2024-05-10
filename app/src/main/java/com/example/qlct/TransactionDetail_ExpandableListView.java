package com.example.qlct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class TransactionDetail_ExpandableListView extends BaseExpandableListAdapter {
    Context context;
    List<TransactionDetail_ExpandableListItems> listHeader;
    HashMap<TransactionDetail_ExpandableListItems, List<TransactionDetail_TheGiaoDich>> listChild;

    public TransactionDetail_ExpandableListView(Context context, List<TransactionDetail_ExpandableListItems> listHeader, HashMap<TransactionDetail_ExpandableListItems, List<TransactionDetail_TheGiaoDich>> listChild) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        TransactionDetail_ExpandableListItems headerTitle = (TransactionDetail_ExpandableListItems) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.transaction_detail_expandable_listview, null);
        TextView date = convertView.findViewById(R.id.date);
        date.setText(headerTitle.getDate_String());
        TextView month = convertView.findViewById(R.id.month);
        month.setText(headerTitle.getMonthName());
        TextView year = convertView.findViewById(R.id.year);
        year.setText(String.valueOf(headerTitle.getYear()));
        TextView totalIncome = convertView.findViewById(R.id.total_income);
        totalIncome.setText(headerTitle.getTotalIncome());
        TextView totalExpense = convertView.findViewById(R.id.total_expense);
        totalExpense.setText(headerTitle.getTotalExpense());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        TransactionDetail_TheGiaoDich item = (TransactionDetail_TheGiaoDich) getChild(groupPosition, childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.transaction_detail_dong_giao_dich, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.hinhanh);
        TextView ten = (TextView) convertView.findViewById(R.id.tendanhmuc);
        TextView sotien = (TextView) convertView.findViewById(R.id.sotien);
        TextView ngaythang = (TextView) convertView.findViewById(R.id.ngaythang);
        TextView ghichu = (TextView) convertView.findViewById(R.id.GhiChu);
        TextView vitien = (TextView) convertView.findViewById(R.id.tenvi);
        imageView.setImageResource(item.getHinhAnh());
        ten.setText(item.getTenGiaoDich());
        sotien.setText(item.getSoTien());
        ngaythang.setText(item.getNgayThang());
        ghichu.setText(item.getGhiChu());
        vitien.setText(item.getViTien());
        try{
            switch (item.getGiaoDich().getCategory_type()){
                case 1:
                {
                    sotien.setTextColor(context.getResources().getColor(R.color.xanhdam));
                }break;
                case 2:
                {
                    sotien.setTextColor(context.getResources().getColor(R.color.red));
                }break;
                case 3:
                {
                    sotien.setTextColor(context.getResources().getColor(R.color.xanhnen));
                }break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
