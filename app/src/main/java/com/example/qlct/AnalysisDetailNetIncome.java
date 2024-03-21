package com.example.qlct;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AnalysisDetailNetIncome extends AppCompatActivity {
    ListView listView;
    List<AnalysisNetIcome> arraylist;
    ImageView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_detail_net_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView=this.findViewById(R.id.listvieww);
        exit=this.findViewById(R.id.exit_netIcome);
        AnhXa();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Analysis_NetIncome_Adapter adapter=new Analysis_NetIncome_Adapter(arraylist,this,R.layout.analysis_net_income_list_item);
        listView.setAdapter(adapter);

    }
    void AnhXa(){

        arraylist=new ArrayList<>();
        arraylist.add(new AnalysisNetIcome(2019,10000000,200000));
        arraylist.add(new AnalysisNetIcome(2020,10000000,200000));
        arraylist.add(new AnalysisNetIcome(2021,10000000,200000));
        arraylist.add(new AnalysisNetIcome(2022,10000000,200000));
        arraylist.add(new AnalysisNetIcome(2023,10000000,200000));
        arraylist.add(new AnalysisNetIcome(2024,10000000,200000));


    }
}