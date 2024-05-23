package com.example.qlct.Transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qlct.R;
import com.example.qlct.doitiente;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        if(listHeader != null){
            return listHeader.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(listHeader != null && listChild != null){
            return listChild.get(listHeader.get(groupPosition)).size();
        }
        return 0;
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
        doitiente doi_tien_te = new doitiente();
        TextView totalIncome = convertView.findViewById(R.id.total_income);
        String total_income = doi_tien_te.formatValue(Double.parseDouble(formatDouble(headerTitle.getTotalIncome()))) + " đ";
        totalIncome.setText(total_income);
        TextView totalExpense = convertView.findViewById(R.id.total_expense);
        String total_expense = doi_tien_te.formatValue(Double.parseDouble(formatDouble(headerTitle.getTotalExpense()))) + " đ";
        totalExpense.setText(total_expense);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        TransactionDetail_TheGiaoDich item = (TransactionDetail_TheGiaoDich) getChild(groupPosition, childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(item.getLoaiGiaoDich().equals("TRANSFER")){
            convertView = inflater.inflate(R.layout.transaction_detail_transfer, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.hinhanh);
            TextView fromwallet = (TextView) convertView.findViewById(R.id.from_wallet_name);
            TextView sotien = (TextView) convertView.findViewById(R.id.sotien);
            TextView ngaythang = (TextView) convertView.findViewById(R.id.ngaythang);
            TextView ghichu = (TextView) convertView.findViewById(R.id.GhiChu);
            TextView targetwallet = (TextView) convertView.findViewById(R.id.target_wallet_name);

            if(item != null){
                if(item.getHinhAnh() != null){
                    if(item.getHinhAnh().matches("^-?\\\\d+$")){
                        imageView.setImageResource(Integer.parseInt(item.getHinhAnh()));
                    }
                }

                switch (item.getDonViTien()) {
                    case "VND":
                        String amount = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " đ";
                        sotien.setText(amount);
                        break;
                    case "USD":
                        String amountUSD = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " $";
                        sotien.setText(amountUSD);
                        break;
                    case "EUR":
                        String amountEUR = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " €";
                        sotien.setText(amountEUR);
                        break;
                    case "CNY":
                        String amountCNY = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " ¥";
                        sotien.setText(amountCNY);
                        break;
                }

                ngaythang.setText(convertDateFormat(item.getNgayThang()));
                ghichu.setText(item.getGhiChu());
                if(item.getViTien() != null) {
                    fromwallet.setText(item.getViTien().name);
                }
                if(item.getViDuocNhanTien() != null){
                    targetwallet.setText(item.getViDuocNhanTien().name);
                }
                sotien.setTextColor(context.getResources().getColor(R.color.xanhnen));
            }
            return convertView;
        }
        else{
            convertView = inflater.inflate(R.layout.transaction_detail_income_expense, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.hinhanh);
            TextView ten = (TextView) convertView.findViewById(R.id.tendanhmuc);
            TextView sotien = (TextView) convertView.findViewById(R.id.sotien);
            TextView ngaythang = (TextView) convertView.findViewById(R.id.ngaythang);
            TextView ghichu = (TextView) convertView.findViewById(R.id.GhiChu);
            TextView vitien = (TextView) convertView.findViewById(R.id.tenvi);
            if(item != null){
                if(item.getHinhAnh() != null){
                    if(item.getHinhAnh().matches("^-?\\\\d+$")){
                        imageView.setImageResource(Integer.parseInt(item.getHinhAnh()));
                    }
                }
                if(item.getDanhMuc() != null){
                    ten.setText(item.getDanhMuc().name);
                }

                switch (item.getDonViTien()) {
                    case "VND":
                        String amount = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " đ";
                        sotien.setText(amount);
                        break;
                    case "USD":
                        String amountUSD = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " $";
                        sotien.setText(amountUSD);
                        break;
                    case "EUR":
                        String amountEUR = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " €";
                        sotien.setText(amountEUR);
                        break;
                    case "CNY":
                        String amountCNY = formatCurrency(Double.parseDouble(item.getSoTien()), item.getDonViTien()) + " ¥";
                        sotien.setText(amountCNY);
                        break;
                }

                Glide.with(context).load(item.getHinhAnh()).into(imageView);
                ngaythang.setText(convertDateFormat(item.getNgayThang()));
                ghichu.setText(item.getGhiChu());
                if(item.getViTien() != null) {
                    vitien.setText(item.getViTien().name);
                }
                try{
                    switch (item.getLoaiGiaoDich()){
                        case "INCOME":
                        {
                            sotien.setTextColor(context.getResources().getColor(R.color.xanhdam));
                        }break;
                        case "EXPENSE":
                        {
                            sotien.setTextColor(context.getResources().getColor(R.color.red));
                        }break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return convertView;
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String formatDouble(double number) {
        DecimalFormat format = new DecimalFormat("0.##");
        return format.format(number);
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

    public String convertDateFormat(String inputDate) {
        DateTimeFormatter inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormat = DateTimeFormat.forPattern("dd-MM-yyy");

        DateTime dateTime = inputFormat.parseDateTime(inputDate);
        return outputFormat.print(dateTime);
    }
}
