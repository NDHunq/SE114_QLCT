package com.example.qlct;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class categories_Adapter extends BaseAdapter {
    private Context context;
   private  int layout;
    private List<cate_item> listcase;
    public categories_Adapter(Context context, int layout, List<cate_item> listcate) {
        this.layout=layout;
        this.context=context;
       this.listcase=listcate;
    }
    @Override
    public int getCount() {
        return listcase.size();
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
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        ImageView imagecate= (ImageView) view.findViewById(R.id.circle_black);
        TextView textname= (TextView) view.findViewById(R.id.category);
        ImageView imagedelete= (ImageView) view.findViewById(R.id.delete);
        cate_item cateItem=listcase.get(i);
        imagecate.setImageResource(cateItem.getImagecate());
        imagedelete.setImageResource(cateItem.getImagedelete());
        textname.setText(cateItem.getName());

        imagedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listcase.remove(i);
                notifyDataSetChanged();
            }
        });

        return  view;
    }


}
