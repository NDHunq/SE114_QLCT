package com.example.qlct.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qlct.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Category_adapter extends BaseAdapter {
    private Context context;

    private int layout;

    private List<Category_hdp> categoryList;

    public Category_adapter(Context context, int layout, List<Category_hdp> categoryList) {
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


        Category_hdp category = categoryList.get(position);
        Glide.with(context).load(category.getImageURL()).into(categoryIcon);

        categoryName.setText(category.getCategory_name());
        return convertView;
    }
}
