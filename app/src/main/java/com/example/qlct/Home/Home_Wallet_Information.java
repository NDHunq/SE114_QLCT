package com.example.qlct.Home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.R;
import com.example.qlct.Share_Wallet;

import java.util.ArrayList;

public class Home_Wallet_Information extends AppCompatActivity {

    int chon1=0;
    int chon2=0;
    int chon1in=0;
    int chon2in=0;
    String currency;
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
        LinearLayout addmember =this.findViewById(R.id.addnewmember);
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event;
                event = new Intent(Home_Wallet_Information.this, Share_Wallet.class);
                startActivity(event);
            }
        });

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
        LinearLayout membercan = findViewById(R.id.membercan);
membercan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showDialog2();
            }
        });

        ImageView more = this.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getVisibility()==View.GONE)
                {
                    View listItem = theMemberAdap.getView(0, null, listView);
                    listItem.measure(0, 0);

// Get the height of the item
                    int itemHeight = listItem.getMeasuredHeight();
                    int height=0;
                    height+=theMemberList.size()*itemHeight;

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
        if(txt1.getText().toString().equals("$"))
        {
            bo1.setBackgroundResource(R.drawable.nenluachon);
        }
        else if(txt1.getText().toString().equals("đ"))
        {
            bo2.setBackgroundResource(R.drawable.nenluachon);
        }
        else if(txt1.getText().toString().equals("€"))
        {
            bo3.setBackgroundResource(R.drawable.nenluachon);
        }
        else if(txt1.getText().toString().equals("¥"))
        {
            bo4.setBackgroundResource(R.drawable.nenluachon);
        }

        bo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               currency = "$";
                bo1.setBackgroundResource(R.drawable.nenluachon);
                bo2.setBackgroundResource(0);
                bo3.setBackgroundResource(0);
                bo4.setBackgroundResource(0);

            }
        });
        bo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currency = "đ";
                bo2.setBackgroundResource(R.drawable.nenluachon);
                bo1.setBackgroundResource(0);
                bo3.setBackgroundResource(0);
                bo4.setBackgroundResource(0);
            }
        });

        bo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currency = "€";
                bo3.setBackgroundResource(R.drawable.nenluachon);
                bo2.setBackgroundResource(0);
                bo1.setBackgroundResource(0);
                bo4.setBackgroundResource(0);
            }
        });
        bo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currency = "¥";
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
        TextView txtmember= this.findViewById(R.id.txtmembercan);
        TextView apply = dialog.findViewById(R.id.apply);
        ImageView check1 = dialog.findViewById(R.id.check1);
        ImageView check2 = dialog.findViewById(R.id.check2);
        check1.setVisibility(View.GONE);
        check2.setVisibility(View.GONE);
        if(txtmember.getText().toString().equals("can view"))
        {
            chon1in=0;
            chon2in=0;

        }
        else if(txtmember.getText().toString().equals("transaction"))
        {
            bo1.setBackgroundResource(R.drawable.nenluachon);
            check1.setVisibility(View.VISIBLE);
            chon1in=1;
            chon2in=0;
        }
        else if(txtmember.getText().toString().equals("add member"))
        {
            bo2.setBackgroundResource(R.drawable.nenluachon);
            check2.setVisibility(View.VISIBLE);
            chon1in=0;
            chon2in=1;
        }
        else if(txtmember.getText().toString().equals("transaction and add member"))
        {
            bo1.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(R.drawable.nenluachon);
            check1.setVisibility(View.VISIBLE);
            check2.setVisibility(View.VISIBLE);
            chon1in=1;
            chon2in=1;
        }
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
                TextView txt = findViewById(R.id.txtmembercan);
                if(chon1in==0&&chon2in==0)
                {
                    txt.setText("can view");
                }
                else if(chon1in==1&&chon2in==0)
                {
                    txt.setText("transaction");
                }
                else if(chon1in==0&&chon2in==1)
                {
                    txt.setText("add member");
                }
                else if(chon1in==1&&chon2in==1)
                {
                    txt.setText("transaction and add member");
                }
                dialog.dismiss();
            }
        });

    }
}