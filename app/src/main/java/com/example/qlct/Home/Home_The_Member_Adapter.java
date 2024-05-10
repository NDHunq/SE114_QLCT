package com.example.qlct.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlct.R;

import java.util.ArrayList;

public class Home_The_Member_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Home_The_member> theMemberList;

    public Home_The_Member_Adapter(Context context, int layout, ArrayList<Home_The_member> theMemberList) {
        this.context = context;
        this.layout = layout;
        this.theMemberList = theMemberList;
    }

    @Override
    public int getCount() {
        return theMemberList.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.hinhanh_member);
        TextView username = (TextView) view.findViewById(R.id.username_member);
        TextView email = (TextView) view.findViewById(R.id.email_member);
        Home_The_member theMember = theMemberList.get(i);
        imageView.setImageResource(theMember.getHinhAnh());
        username.setText(theMember.getUsername());
        email.setText(theMember.getEmail());

        return view;
    }
}
