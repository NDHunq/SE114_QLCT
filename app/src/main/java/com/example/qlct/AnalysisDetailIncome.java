package com.example.qlct;

import android.graphics.Color;
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

public class AnalysisDetailIncome extends AppCompatActivity {
    ImageView exit;
    List<AnalysisExpense> list;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_detail_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        exit=this.findViewById(R.id.exit_Income);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list=new ArrayList<>();
        AnhXa();
        Analysis_Expense_Adapter adapter=new Analysis_Expense_Adapter(list,this,R.layout.analysis_expense_list_item);
        listView.setAdapter(adapter);
    }
    void AnhXa(){
        listView=this.findViewById(R.id.listvieww);
        list.add(new AnalysisExpense(Color.BLACK,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.YELLOW,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.RED,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.BLUE,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.GREEN,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.BLACK,R.drawable.dish,"Food",20,2000000));

    }
}