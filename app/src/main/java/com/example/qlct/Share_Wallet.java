package com.example.qlct;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.Home.Home_The_Share_Wallet;
import com.example.qlct.Home.Home_The_Share_Wallet_Apapter;

import java.util.ArrayList;

public class Share_Wallet extends AppCompatActivity {
    ListView listView;
    ArrayList<Home_The_Share_Wallet>  theShareWalletList;
    Home_The_Share_Wallet_Apapter theShareWalletApapter;
    private void Anhxa() {

        theShareWalletList = new ArrayList<>();
        theShareWalletList.add(new Home_The_Share_Wallet("", ""));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share_wallet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        listView= this.findViewById(R.id.listView_share_wallets);
        theShareWalletApapter = new Home_The_Share_Wallet_Apapter(this,R.layout.home_dong_share_wallet,theShareWalletList);
        listView.setAdapter(theShareWalletApapter);
        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> finish());


    }
}