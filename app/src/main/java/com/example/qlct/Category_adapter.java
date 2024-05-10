package com.example.qlct;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Category_adapter extends BaseAdapter {
    private Context context;

    private int layout;

    private List<Category> categoryList;

    public Category_adapter(Context context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        ImageView categoryIcon = convertView.findViewById(R.id.category_icon);
        TextView categoryName = convertView.findViewById(R.id.category_txtview);
        MaterialCardView cardView = convertView.findViewById(R.id.category_icon_cardview);

        Category category = categoryList.get(position);
        categoryIcon.setImageResource(category.getImage());
        categoryName.setText(category.getCategory_name());
        switch (category.getCategory_type()){
            case 1:
            {
                categoryName.setTextColor(context.getResources().getColor(R.color.xanhdam));
                categoryIcon.setColorFilter(context.getResources().getColor(R.color.xanhdam));
                cardView.setStrokeColor(context.getResources().getColor(R.color.xanhdam));
            }break;
            case 2: {
                categoryName.setTextColor(context.getResources().getColor(R.color.red));
                categoryIcon.setColorFilter(context.getResources().getColor(R.color.red));
                cardView.setStrokeColor(context.getResources().getColor(R.color.red));
            }break;
            case 3: {
                categoryName.setTextColor(context.getResources().getColor(R.color.xanhnen));
                categoryIcon.setColorFilter(context.getResources().getColor(R.color.xanhnen));
                cardView.setStrokeColor(context.getResources().getColor(R.color.xanhnen));
            }break;
        }

        return convertView;
    }
}
