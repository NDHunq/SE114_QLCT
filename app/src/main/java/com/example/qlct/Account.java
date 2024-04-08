package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Account extends AppCompatActivity {
ImageButton mywallet;
ImageButton mycate;
LinearLayout setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        mywallet=findViewById(R.id.mywallet);
        mycate=findViewById(R.id.mycates);
        setting=findViewById(R.id.setting);
        mywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Account.this,Home_My_wallets.class);
                startActivity(myintent);
            }
        });
        mycate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Account.this, My_categories.class);
                startActivity(myintent);
            }
        });

setting.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myintent=new Intent(Account.this, Setting.class);
        startActivity(myintent);

    }
});
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}