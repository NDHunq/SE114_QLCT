package com.example.qlct;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Home_Wallet_Information extends AppCompatActivity {

    ListView listView;
   ArrayList<Home_The_member> theMemberList;
  Home_The_Member_Adapter theMemberAdap;
    private  void Anhxa()
    {

        theMemberList = new ArrayList<>();
        theMemberList.add(new Home_The_member(R.drawable.wallet,"username1","username1@gmail.com"));
        theMemberList.add(new Home_The_member(R.drawable.wallet,"username1","username1@gmail.com"));
        theMemberList.add(new Home_The_member(R.drawable.wallet,"username1","username1@gmail.com"));
        theMemberList.add(new Home_The_member(R.drawable.wallet,"username1","username1@gmail.com"));
        theMemberList.add(new Home_The_member(R.drawable.wallet,"username1","username1@gmail.com"));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_wallet_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        listView = this.findViewById(R.id.listviewmember);
        listView.setVisibility(View.GONE);
        theMemberAdap = new Home_The_Member_Adapter(this,R.layout.home_dong_member,theMemberList);
        listView.setAdapter(theMemberAdap);
        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView more = this.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getVisibility()==View.GONE)
                {
                    int height=0;
                    height+=theMemberList.size()*150;

                    ListView listViewMember = findViewById(R.id.listviewmember);
                    ViewGroup.LayoutParams params = listViewMember.getLayoutParams();
                    params.height = height;
                    listViewMember.setLayoutParams(params);
                    listView.setVisibility(View.VISIBLE);
                    more.setBackgroundResource(R.drawable.up);


                }
                else
                {
                    ListView listViewMember = findViewById(R.id.listviewmember);

                    listView.setVisibility(View.GONE);
                    more.setBackgroundResource(R.drawable.upp);
                }
            }
        });


    }
}