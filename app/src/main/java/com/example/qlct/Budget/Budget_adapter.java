package com.example.qlct.Budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qlct.R;

import java.text.DecimalFormat;
import java.util.List;

public class Budget_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Budget> listBudget;

    public Budget_adapter(Context context, int layout, List<Budget> listBudget) {
        this.context = context;
        this.layout = layout;
        this.listBudget = listBudget;
    }

    @Override
    public int getCount() {
        return listBudget.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(layout,null);
        DecimalFormat df = new DecimalFormat("#,###");
        //ánh xạ view
        ImageView image=convertView.findViewById(R.id.image);
        TextView category=convertView.findViewById(R.id.category);
        TextView fromday=convertView.findViewById(R.id.fromday);
        TextView today=convertView.findViewById(R.id.today);
        TextView maxmoney=convertView.findViewById(R.id.maxmoney);
        TextView expense=convertView.findViewById(R.id.expense);
        SeekBar seekBar=convertView.findViewById(R.id.seekBar);
        //gán giá trị
        Budget budget=listBudget.get(position);
        //image.setImageResource(budget.getImage());
        Glide.with(convertView).load(budget.getImage()).into(image);
        category.setText(budget.getCategory());
        fromday.setText(budget.getFromDate());
        today.setText(budget.getToDate());
        maxmoney.setText(df.format((budget.getMax_money())) + " " + currency(budget.getCurrency()));
        expense.setText(df.format((budget.getCurent_money())) + " " +currency( budget.getCurrency()));
        seekBar.setMax((int) budget.getMax_money());
        seekBar.setProgress((int) budget.getCurent_money());
        seekBar.setEnabled(false);
        return convertView;
    }
    String currency(String currency){
        if(currency.equals("VND")){
            return "đ";
        }
        else if(currency.equals("USD")){
            return "$";
        }
        else if(currency.equals("EUR")){
            return "€";
        }
        else if(currency.equals("CNY")){
            return "¥";
        }
        return "";
    }
}
