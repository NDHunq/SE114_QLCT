package com.example.qlct.Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Entity.UpdateWalletEntity;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.R;
import com.example.qlct.Share_Wallet;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Home_Wallet_Information extends AppCompatActivity {

    int chon1=0;
  GetAllWalletsEntity wallet;

    int chon2=0;
    int chon1in=0;
    int chon2in=0;
    String currency;
    ListView listView;
    String exname ;
    String exid;
    String exammount;
    String excurrency;
    String create;
    String exupdate;
    String exduochon;


  Home_The_Member_Adapter theMemberAdap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        exid = intent.getStringExtra("id");
       exname= intent.getStringExtra("name");
         exammount = intent.getStringExtra("ammount");
            excurrency = intent.getStringExtra("currency");
            create = intent.getStringExtra("start");
            exupdate = intent.getStringExtra("update");
            exduochon = intent.getStringExtra("duocchon");

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_wallet_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //save
        TextView save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText nameview= findViewById(R.id.namewallet);
                TextInputEditText ammountview = findViewById(R.id.ammountinf);
                WalletAPIUtil walletAPIUtil = new WalletAPIUtil();
                UpdateWalletEntity updateWalletEntity = new UpdateWalletEntity( nameview.getText().toString(), Double.parseDouble(ammountview.getText().toString()),currency);

                walletAPIUtil.updateWalletAPI(exid,updateWalletEntity);
                Intent intent = new Intent(Home_Wallet_Information.this, Home_My_wallets.class);
                intent.putExtra("viduocchon",exduochon);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        ConstraintLayout delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Home_Wallet_Information.this)
                        .setTitle("Delete Wallet")
                        .setMessage("Are you sure you want to delete this wallet?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                WalletAPIUtil walletAPIUtil = new WalletAPIUtil();
                                walletAPIUtil.deleteWallet(exid);
                                Intent intent = new Intent(Home_Wallet_Information.this, Home_My_wallets.class);
                                TextInputEditText nameview= findViewById(R.id.namewallet);
                                if(exduochon.equals(nameview.getText().toString()))
                                {
                                    exduochon = "Total";
                                }
                                intent.putExtra("viduocchon",exduochon);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                // Finish the activity
                               finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        TextInputEditText nameview= findViewById(R.id.namewallet);
        nameview.setText(exname);
        TextInputEditText ammountview = findViewById(R.id.ammountinf);
ammountview.setText(exammount);

        TextView txt1 = findViewById(R.id.txt);
        txt1.setText(excurrency);



        TextView startview = findViewById(R.id.start);
        startview.setText("Start day: "+create);
        TextView updateview = findViewById(R.id.update);
        updateview.setText("Last update: "+exupdate);








        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        LinearLayout linearLayout = findViewById(R.id.currency);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });



    }
    private  void showDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_currency);
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        LinearLayout bo1 = dialog.findViewById(R.id.bo1);
        LinearLayout bo2 = dialog.findViewById(R.id.bo2);
        LinearLayout bo3 = dialog.findViewById(R.id.bo3);
        LinearLayout bo4 = dialog.findViewById(R.id.bo4);
        TextView txt1 = findViewById(R.id.txt);
        if(txt1.getText().toString().equals("USD"))
        {
            bo1.setBackgroundResource(R.drawable.nenluachon);
        }
        else if(txt1.getText().toString().equals("VND"))
        {
            bo2.setBackgroundResource(R.drawable.nenluachon);
        }
        else if(txt1.getText().toString().equals("EUR"))
        {
            bo3.setBackgroundResource(R.drawable.nenluachon);
        }
        else if(txt1.getText().toString().equals("CNY"))
        {
            bo4.setBackgroundResource(R.drawable.nenluachon);
        }

        bo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               currency = "USD";
                bo1.setBackgroundResource(R.drawable.nenluachon);
                bo2.setBackgroundResource(0);
                bo3.setBackgroundResource(0);
                bo4.setBackgroundResource(0);

            }
        });
        bo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currency = "VND";
                bo2.setBackgroundResource(R.drawable.nenluachon);
                bo1.setBackgroundResource(0);
                bo3.setBackgroundResource(0);
                bo4.setBackgroundResource(0);
            }
        });

        bo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currency = "EUR";
                bo3.setBackgroundResource(R.drawable.nenluachon);
                bo2.setBackgroundResource(0);
                bo1.setBackgroundResource(0);
                bo4.setBackgroundResource(0);
            }
        });
        bo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currency = "CNY";
                bo4.setBackgroundResource(R.drawable.nenluachon);
                bo2.setBackgroundResource(0);
                bo3.setBackgroundResource(0);
                bo1.setBackgroundResource(0);
            }
        });
        TextView ok = dialog.findViewById(R.id.apply);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = findViewById(R.id.txt);
                txt.setText(currency);
                dialog.dismiss();
            }
        });

    }
    private  void showDialog2 ()
    {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_member_can);
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        LinearLayout bo1 = dialog.findViewById(R.id.bo1);
        LinearLayout bo2 = dialog.findViewById(R.id.bo2);

        TextView apply = dialog.findViewById(R.id.apply);
        ImageView check1 = dialog.findViewById(R.id.check1);
        ImageView check2 = dialog.findViewById(R.id.check2);
        check1.setVisibility(View.GONE);
        check2.setVisibility(View.GONE);

        bo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chon1in==0)
                {
                    chon1in=1;
                    check1.setVisibility(View.VISIBLE);

                    bo1.setBackgroundResource(R.drawable.nenluachon);
                }
                else
                {
                    chon1in=0;
                    check1.setVisibility(View.GONE);
                    bo1.setBackgroundResource(0);
                }


            }
        });
        bo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chon2in==0)
                {
                    check2.setVisibility(View.VISIBLE);
                    chon2in=1;
                    bo2.setBackgroundResource(R.drawable.nenluachon);
                }
                else
                {
                    chon2in=0;
                    check2.setVisibility(View.GONE);
                    bo2.setBackgroundResource(0);
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });

    }
}