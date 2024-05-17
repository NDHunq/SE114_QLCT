package com.example.qlct.Home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Entity.UpdateWalletEntity;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.MainActivity;
import com.example.qlct.R;
import com.example.qlct.doitiente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home_My_wallets extends AppCompatActivity {
    int up=0;
    String txt2=new String();
    LinearLayout linearLayout;
    ListView listView;
    ArrayList<Home_TheVi>  theViList;
    Home_TheVi theVi;
    String viduocchon;
    double tienduocchon;
    String currencyduocchon;

    double TongTien=0;
    doitiente doitien = new doitiente();
    double tongsovi=0;

    private  void Anhxa()
    {

        TongTien=0;
        try {
            theViList = new ArrayList<>();
            ArrayList<GetAllWalletsEntity> parseAPIList = new WalletAPIUtil().getAllWalletAPI();
            tongsovi = parseAPIList.size();
            //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
            for (GetAllWalletsEntity item : parseAPIList) {

                String donvi = "";
                double amount = Double.parseDouble(item.amount);
                if(item.currency_unit.equals("VND"))
                {donvi=" ₫";
                    TongTien += amount;}
                if(item.currency_unit.equals("USD"))
                {donvi=" $";
                    TongTien += amount*doitien.getUSDtoVND();}
                if(item.currency_unit.equals("EUR"))
                {donvi=" €";
                    TongTien += amount*doitien.getUERtoVND();}
                if(item.currency_unit.equals("CNY"))
                {donvi=" ¥";
                    TongTien += amount*doitien.getCNYtoVND();}
                EditText searchbar = findViewById(R.id.searchbar);


                if(searchbar.getText().toString().equals("")) {
                    if (item.name.equals(viduocchon)) {
                        theViList.add(new Home_TheVi(item.id, R.drawable.wallet, item.name, item.amount + donvi, item, 1, viduocchon));
                        tienduocchon = Double.parseDouble(item.amount);
                        currencyduocchon = item.currency_unit;
                    } else {
                        theViList.add(new Home_TheVi(item.id, R.drawable.wallet, item.name, item.amount + donvi, item, 0, viduocchon));
                    }
                }
                else {

                    if(item.name.toLowerCase().contains(searchbar.getText().toString())) {
                        if (item.name.equals(viduocchon)) {
                            theViList.add(new Home_TheVi(item.id, R.drawable.wallet, item.name, item.amount + donvi, item, 1, viduocchon));
                            tienduocchon = Double.parseDouble(item.amount);
                            currencyduocchon = item.currency_unit;
                        } else {
                            theViList.add(new Home_TheVi(item.id, R.drawable.wallet, item.name, item.amount + donvi, item, 0, viduocchon));
                        }
                    }
                    else {}
                }



            }
            Log.d("Get_wallet_data_object", theViList.toString());
        }
        catch (Exception e) {
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
        }
        TextView tongtien = findViewById(R.id.tongammount);
        tongtien.setText(doitien.formatValue(TongTien)+ " ₫");
        // Sort theViList by item.name in ascending order

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
         EditText searchbar = findViewById(R.id.searchbar);

        viduocchon= getIntent().getStringExtra("viduocchon");
        if(viduocchon==null)
        {
            viduocchon="Total";
        }


        if(viduocchon.equals("Total"))
        {
           LinearLayout total_layout = findViewById(R.id.total_layout);
            total_layout.setBackgroundResource(R.drawable.the12dpvienxanh);
        }


        Anhxa();

        TextView tongtien = findViewById(R.id.tongammount);
        LinearLayout total =findViewById(R.id.total_layout);
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("tenvi", "Total");
                intent.putExtra("ammount", TongTien);
                intent.putExtra("currency_unit", "VND");
                intent.putExtra("tongsovi", tongsovi);
                startActivity(intent);
            }
        });

        TextView add = findViewById(R.id.addnew);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event;

                Intent intent = new Intent(Home_My_wallets.this  , Home_New_wallet.class);

                // Bắt đầu Activity mới
                startActivityForResult(intent, 1);
                Anhxa();

            }
        });
       listView = this.<ListView>findViewById(R.id.listView_wallets);


        Collections.sort(theViList, nameComparator);
        Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(this,R.layout.home_dong_vi,theViList);
        listView.setAdapter(theViAdap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item
                Home_TheVi clickedItem = theViList.get(position);

                // Create an Intent to start MainActivity
                Intent intent = new Intent(Home_My_wallets.this, MainActivity.class);


                // Put the "tenvi" and "ammount" properties of the clicked item as extras into the Intent
                intent.putExtra("tenvi", clickedItem.item.name);
                intent.putExtra("ammount",Double.parseDouble(clickedItem.item.amount));

                intent.putExtra("currency_unit", clickedItem.item.currency_unit);
                intent.putExtra("tongsovi", tongsovi);
                // Start MainActivity
                startActivity(intent);
            }
        });
ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Anhxa();
                Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(Home_My_wallets.this, R.layout.home_dong_vi, theViList);
                listView.setAdapter(theViAdap);


            }
        });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    Anhxa();
                    Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(Home_My_wallets.this, R.layout.home_dong_vi, theViList);
                    listView.setAdapter(theViAdap);
                }
            }
        });
        ImageView backArrow = findViewById(R.id.backarrow);

        // Đặt OnClickListener cho backarrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tenvi", viduocchon);
                if(viduocchon.equals("Total"))
                {
                    bundle.putDouble("ammount", TongTien);
                    bundle.putString("currency_unit", "VND");
                    bundle.putDouble("tongsovi", tongsovi);




                }
                else
                {
                    bundle.putDouble("ammount", tienduocchon);
                    bundle.putString("currency_unit", currencyduocchon);
                    bundle.putDouble("tongsovi", tongsovi);
                }

                // Kết thúc Activity hiện tại và quay lại Activity cũ
                Intent intent = new Intent(Home_My_wallets.this, MainActivity.class);
                intent.putExtras(bundle);
               startActivity(intent);
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
    Comparator<Home_TheVi> nameComparator = new Comparator<Home_TheVi>() {
        @Override
        public int compare(Home_TheVi o1, Home_TheVi o2) {
            return o1.item.name.compareTo(o2.item.name);
        }
    };

    Comparator<Home_TheVi> totalBalanceComparator = new Comparator<Home_TheVi>() {
        @Override
        public int compare(Home_TheVi o1, Home_TheVi o2) {
            return Double.compare(
                    doitien.converttoVND(o1.item.currency_unit, Double.parseDouble(o1.item.amount)),
                    doitien.converttoVND(o2.item.currency_unit, Double.parseDouble(o2.item.amount))
            );
        }
    };

    Comparator<Home_TheVi> recentlyUsedComparator = new Comparator<Home_TheVi>() {
        @Override
        public int compare(Home_TheVi o1, Home_TheVi o2) {
            // Assuming there is a 'lastUsed' field in the 'item' object that stores the last used date as a long
            return o1.item.currency_unit.compareTo(o2.item.currency_unit);
        }
    };
    private  void showDialog()
    {
        upin=up;
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_sapxepvi);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
         else if(txt2=="Currency unit")
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
               txt2 = "Currency unit";

                recentlayout.setBackgroundResource(R.drawable.nenluachon);
                totallayout.setBackgroundResource(0);
                namelayout.setBackgroundResource(0);

            }
        });
        totallayout.setOnClickListener(new View.OnClickListener()
 {
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
                    upDownImage.setBackgroundResource(R.drawable.up_arrow_1);
                }
                else
                {
                    upDownImage.setBackgroundResource(R.drawable.arrow_down);
                }
                if(txt2.equals("Name"))
                {  Log.d("asdf",String.valueOf(up));

                    if(up==0)

                    {
                        Collections.sort(theViList, nameComparator);
                    }
                    else
                    {

                        Collections.sort(theViList, Collections.reverseOrder(nameComparator));
                    }

                    Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(Home_My_wallets.this, R.layout.home_dong_vi, theViList);
                    listView.setAdapter(theViAdap);


                }
                else if(txt2.equals("Currency unit"))
                {
                    if(up==0)
                    {
                        Collections.sort(theViList, recentlyUsedComparator);
                    }
                    else
                    {
                        Collections.sort(theViList, Collections.reverseOrder(recentlyUsedComparator));
                    }

                    Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(Home_My_wallets.this, R.layout.home_dong_vi, theViList);
                    listView.setAdapter(theViAdap);

                }

                else if(txt2.equals("Total Balance"))
                {
                    if(up==0)
                    {
                        Collections.sort(theViList, totalBalanceComparator);
                    }
                    else
                    {
                        Collections.sort(theViList, Collections.reverseOrder(totalBalanceComparator));
                    }

                    Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(Home_My_wallets.this, R.layout.home_dong_vi, theViList);
                    listView.setAdapter(theViAdap);

                }
            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the result comes from our request
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Call Anhxa() here
            Anhxa();

            // Refresh the ListView
            Home_TheVi_Adapter theViAdap = new Home_TheVi_Adapter(this,R.layout.home_dong_vi,theViList);
            listView.setAdapter(theViAdap);
        }
    }
}