package com.example.qlct;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class My_categories extends AppCompatActivity {
    ListView lvcate;
    ArrayList<cate_item> arraycate;
    categories_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_categories);
       Anhxa();
       adapter=new categories_Adapter(this,R.layout.categories_item,arraycate);
       lvcate.setAdapter(adapter);
    }
    private void Anhxa()
    {
        lvcate=(ListView)findViewById(R.id.mylv);
        arraycate=new ArrayList<>();
        arraycate.add(new cate_item(R.drawable.circle_black,"Clothing",R.drawable.show_more));
        arraycate.add(new cate_item(R.drawable.circle_black,"Clothing",R.drawable.show_more));
        arraycate.add(new cate_item(R.drawable.circle_black,"Clothing",R.drawable.show_more));
        arraycate.add(new cate_item(R.drawable.circle_black,"Clothing",R.drawable.show_more));
        arraycate.add(new cate_item(R.drawable.circle_black,"Clothing",R.drawable.show_more));

    }
}