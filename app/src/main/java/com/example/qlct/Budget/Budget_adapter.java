package com.example.qlct.Budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.R;

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
        //ánh xạ view
        ImageView image=convertView.findViewById(R.id.image);
        TextView category=convertView.findViewById(R.id.category);
        TextView vi=convertView.findViewById(R.id.vi);
        TextView fromday=convertView.findViewById(R.id.fromday);
        TextView today=convertView.findViewById(R.id.today);
        TextView maxmoney=convertView.findViewById(R.id.maxmoney);
        //gán giá trị
        Budget budget=listBudget.get(position);
        image.setImageResource(budget.getImage());
        category.setText(budget.getCategory());
        vi.setText(budget.getVi());
        fromday.setText(budget.getFromDate());
        today.setText(budget.getToDate());
        maxmoney.setText( String.valueOf(budget.getMax_money()));

        return convertView;
    }
}
