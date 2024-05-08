package com.example.qlct.Home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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

import java.util.ArrayList;

public class Home_My_wallets extends AppCompatActivity {
    int up=0;
    String txt2=new String();
    LinearLayout linearLayout;
    ListView listView;
    ArrayList<Home_TheVi>  theViList;
    Home_TheVi theVi;
    private  void Anhxa()
    {

        theViList = new ArrayList<>();
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));
        theViList.add(new Home_TheVi(R.drawable.wallet,"Ví tiền","5000000 đ"));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_wallets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView add = findViewById(R.id.addnew);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event;


                Intent intent = new Intent(Home_My_wallets.this  , Home_New_wallet.class);

                // Bắt đầu Activity mới
                startActivity(intent);
            }
        });
       listView = this.<ListView>findViewById(R.id.listView_wallets);
        Anhxa();
        Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(this,R.layout.home_dong_vi,theViList);
        listView.setAdapter(theViAdap);

        ImageView backArrow = findViewById(R.id.backarrow);

        // Đặt OnClickListener cho backarrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kết thúc Activity hiện tại và quay lại Activity cũ
                finish();
            }
        });
        linearLayout = findViewById(R.id.sortbutton);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    int upin=1;
    private  void showDialog()
    {
        upin=up;
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_sapxepvi);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        ImageView upDownImage = this.findViewById(R.id.UP_DOWN);
        LinearLayout ascLayout = dialog.findViewById(R.id.ASC);

        LinearLayout descLayout = dialog.findViewById(R.id.DESC);
        // Get the current background of upDownImage

        // If the current background is up_arrow, set the background of ascLayout to nenchonbentrai
        if (up==0) {
            ascLayout.setBackgroundResource(R.drawable.nutchonbentrai);
            TextView textView= dialog.findViewById(R.id.ASCtxt);
            textView.setTextColor(Color.parseColor("#5CC2F2"));
        }
        else {

        descLayout.setBackgroundResource(R.drawable.nutchonbenphai);
            TextView textView= dialog.findViewById(R.id.DESCtxt);
            textView.setTextColor(Color.parseColor("#5CC2F2"));
        }
        LinearLayout namelayout = dialog.findViewById(R.id.name);
        LinearLayout recentlayout = dialog.findViewById(R.id.recent);
        LinearLayout totallayout = dialog.findViewById(R.id.total_blance);
        TextView textView = dialog.findViewById(R.id.apply);
        TextView txtcu = findViewById(R.id.sortbutton_text);

         txt2=txtcu.getText().toString();
        namelayout.setBackgroundResource(R.drawable.nenluachon);
         if(txt2=="Name")
         {
             namelayout.setBackgroundResource(R.drawable.nenluachon);

         }
         else if(txt2=="Recently Used")
         {
             recentlayout.setBackgroundResource(R.drawable.nenluachon);
             namelayout.setBackgroundResource(0);
         }
        else if(txt2=="Total Balance")
         {
             totallayout.setBackgroundResource(R.drawable.nenluachon);
             namelayout.setBackgroundResource(0);
         }

        // Initialize the views


        dialog.show();



        ascLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = dialog.findViewById(R.id.ASCtxt);

                if (upin == 1) {
                    ascLayout.setBackgroundResource(R.drawable.nutchonbentrai);
                    descLayout.setBackgroundResource(R.drawable.nutchuachonbenphai);
                    TextView textView2 = dialog.findViewById(R.id.DESCtxt);
                    textView.setTextColor(Color.parseColor("#5CC2F2"));
                    textView2.setTextColor(Color.BLACK);
                    upin=0;

                }
            }
        });

        descLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upin==0)
                {
                    TextView textView = dialog.findViewById(R.id.ASCtxt);
                    TextView textView2 = dialog.findViewById(R.id.DESCtxt);
                    ascLayout.setBackgroundResource(R.drawable.nutchuachonbentrai);
                    textView.setTextColor(Color.BLACK);
                    upin = 1;
                    descLayout.setBackgroundResource(R.drawable.nutchonbenphai);

                    textView2.setTextColor(Color.parseColor("#5CC2F2"));


                }
                else
                {
                    up=0;
                }


            }
        });
        namelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.sortbutton_text);
                txt2 = "Name";
                namelayout.setBackgroundResource(R.drawable.nenluachon);
                recentlayout.setBackgroundResource(0);
                totallayout.setBackgroundResource(0);

            }
        });
        recentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.sortbutton_text);
               txt2 = "Recently Used";

                recentlayout.setBackgroundResource(R.drawable.nenluachon);
                totallayout.setBackgroundResource(0);
                namelayout.setBackgroundResource(0);


            }
        });
        totallayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.sortbutton_text);

                txt2 = "Total Balance";
                recentlayout.setBackgroundResource(0);
                namelayout.setBackgroundResource(0);
              totallayout.setBackgroundResource(R.drawable.nenluachon);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.sortbutton_text);
                textView.setText(txt2);
                dialog.dismiss();
                up=upin;
                if(up==0)
                {
                    upDownImage.setBackgroundResource(R.drawable.up_arrow);
                }
                else
                {
                    upDownImage.setBackgroundResource(R.drawable.arrow_down);
                }
            }
        });
    }
}