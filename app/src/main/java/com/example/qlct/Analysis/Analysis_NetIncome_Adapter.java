package com.example.qlct.Analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlct.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Analysis_NetIncome_Adapter extends BaseAdapter {
    List<AnalysisNetIcome> list;
    Context context;
    int layout;
    String currency;

    public Analysis_NetIncome_Adapter(List<AnalysisNetIcome> list, Context context, int layout,String currency) {
        this.list = list;
        this.context = context;
        this.layout = layout;
        this.currency=currency;
    }

    @Override
    public int getCount() {
        return list.size();
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
        //Ánh xạ
        TextView year=convertView.findViewById(R.id.year);
        TextView income=convertView.findViewById(R.id.income);
        TextView expense=convertView.findViewById(R.id.expense);
        TextView total=convertView.findViewById(R.id.total);
        //Gán giá trị
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));

        AnalysisNetIcome netIcome=list.get(position);
        year.setText(String.valueOf(netIcome.getYear()));
        income.setText(format.format(netIcome.getIncome())+" "+currency);
        expense.setText(format.format(netIcome.getExpense())+" "+currency);
        double total1=netIcome.getIncome()-netIcome.getExpense();
        total.setText(format.format(total1)+" "+currency);
        return convertView;
    }
}
