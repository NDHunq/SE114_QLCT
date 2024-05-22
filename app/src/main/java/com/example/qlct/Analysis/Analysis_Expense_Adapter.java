package com.example.qlct.Analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Analysis_Expense_Adapter extends BaseAdapter {
    List<AnalysisExpense> list;
    Context context;
    int layout;

    public Analysis_Expense_Adapter(List<AnalysisExpense> list, Context context, int layout) {
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
        AnalysisExpense analysisExpense=list.get(position);
        //ánh xạ
        ImageView color=convertView.findViewById(R.id.color);
        ImageView avt=convertView.findViewById(R.id.avt);
        TextView name=convertView.findViewById(R.id.name);
        TextView percent=convertView.findViewById(R.id.percent);
        TextView money=convertView.findViewById(R.id.money);
        //gán giá trị
        color.setBackgroundColor(analysisExpense.getColor());
        //color.setBackgroundColor(analysisExpense.getColor());
        String url=analysisExpense.getAvt();
        Picasso.get().load(url).fit().into(avt);
        name.setText(analysisExpense.getName());
        percent.setText(String.valueOf(analysisExpense.getPercent())+"%");
        money.setText((analysisExpense.getMoney())+" "+analysisExpense.getCurrency());
        return convertView;
    }
}
