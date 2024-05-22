package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Utils.CategoryAPIUtil;

import java.util.ArrayList;
import java.util.List;

public class  My_categories extends AppCompatActivity {
    boolean isIncome = true;
    ImageButton backmycate;
    ListView lvcate;
    ArrayList<cate_item> arraycate;
    ArrayList<GetAllCategoryEntity> List;
    categories_Adapter adapter;
    TextView addnew;

    LinearLayout income;
    LinearLayout expense;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_categories);
        CategoryAPIUtil categoryAPIUtil = new CategoryAPIUtil();
        List= categoryAPIUtil.getAllCategory();
        Log.d("List", List.toString());


       backmycate=findViewById(R.id.backmycate);
       backmycate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              finish();
           }
       });
       income=findViewById(R.id.income);
       expense=findViewById(R.id.expense);
       addnew=findViewById(R.id.addnew);
        lvcate=(ListView)findViewById(R.id.mylv);
        Anhxa();
       addnew.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent myintent=new Intent(My_categories.this,Category_Add.class);
               startActivity(myintent);
           }
       });
         income.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                isIncome = true;
                Anhxa();
                  income.setBackgroundColor(getResources().getColor(R.color.xanhdam));
                    expense.setBackground(getResources().getDrawable(R.drawable.expense));
              }
         });
       expense.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               isIncome = false;
                Anhxa();
               income.setBackground(getResources().getDrawable(R.drawable.expense));
               expense.setBackgroundColor(getResources().getColor(R.color.red));
           }
       });
    }

    private void Anhxa()
    {



        arraycate=new ArrayList<>();
        for(int i=0;i<List.size();i++)
        {
           if(isIncome) {
               if(List.get(i).type.equals("INCOME"))
               {
                   arraycate.add(new cate_item(List.get(i).picture, List.get(i).name, R.drawable.delete));
               }

           }
           else {
                if(List.get(i).type.equals("EXPENSE"))
                {
                     arraycate.add(new cate_item(List.get(i).picture, List.get(i).name, R.drawable.delete));
                }
           }
        }
        adapter=new categories_Adapter(this,R.layout.categories_item,arraycate);
        lvcate.setAdapter(adapter);


    }

}