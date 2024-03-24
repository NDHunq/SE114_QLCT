package com.example.qlct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Analysis_NetIncome_Adapter extends BaseAdapter {
    List<AnalysisNetIcome> list;
    Context context;
    int layout;

    public Analysis_NetIncome_Adapter(List<AnalysisNetIcome> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
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
        AnalysisNetIcome netIcome=list.get(position);
        year.setText(String.valueOf(netIcome.getYear()));
        income.setText(String.valueOf(netIcome.getIncome()));
        expense.setText(String.valueOf(netIcome.getExpense()));
        total.setText(String.valueOf(netIcome.getIncome()-netIcome.getExpense()) +" đ");
        return convertView;
    }
}
