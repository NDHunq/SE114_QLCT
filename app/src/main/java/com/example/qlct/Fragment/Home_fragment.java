package com.example.qlct.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Utils.TransactionAPIUtil;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.Category_Add;
import com.example.qlct.Home.Home_My_wallets;
import com.example.qlct.Home.Home_New_wallet;
import com.example.qlct.Home.Home_TheGiaoDich;
import com.example.qlct.Home.Home_TheGiaoDich_Adapter;
import com.example.qlct.Home.Home_Wallet_Information;
import com.example.qlct.Home_TheTopSpent;
import com.example.qlct.Home_TheTopSpent_Adapter;
import com.example.qlct.Notification.Notificaiton;
import com.example.qlct.R;
import com.example.qlct.Transaction.*;

import com.example.qlct.doitiente;

import com.example.qlct.Transaction.TransactionDetail;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import java.util.ArrayList;

public class Home_fragment extends Fragment {

    ListView listView;
    ArrayList<Home_TheGiaoDich>  theGiaoDichList;
    Home_TheGiaoDich_Adapter theGiaoDichAdap;
    ArrayList<Home_TheTopSpent> theTopSpentList;
    Home_TheTopSpent_Adapter theTopSpentAdap;


    private  void Anhxa2()
    {
        theTopSpentList = new ArrayList<>();
        theTopSpentList.add(new Home_TheTopSpent(R.drawable.budget,"Ăn uống","5000000 đ","20%"));
        theTopSpentList.add(new Home_TheTopSpent(R.drawable.budget,"Ăn uống","5000000 đ","20%"));
        theTopSpentList.add(new Home_TheTopSpent(R.drawable.budget,"Ăn uống","5000000 đ","20%"));
    }
    private  void Anhxa()
    {
        try {
            theGiaoDichList = new ArrayList<>();
            int i=0;
            ArrayList<GetAllTransactionsEntity_quyen> parseAPIList = new TransactionAPIUtil().getAllTransactionsAPI_quyen();
            //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
            for (GetAllTransactionsEntity_quyen item : parseAPIList) {
                i++;
                if(i>3)
                {
                    break;
                }

                theGiaoDichList.add(new Home_TheGiaoDich(R.drawable.wallet,item.category.name, item.amount, item.transaction_date, item.notes, item.wallet.name));
            }
            Log.d("Get_wallet_data_object", theGiaoDichList.toString());

        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Log.d("abcz", e.getMessage());
            //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
            e.printStackTrace();
        }




    }
    double  TongTien=0;
    doitiente doitien = new doitiente();

    public  void loadData()
    {

    }
    String TenVi ="Total";
    double ammount = 0;
    String currency_unit = "đ";
    double tongsovi =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Anhxa();
        Anhxa2();

        Bundle arguments = getArguments();

        if (arguments != null) {
             TenVi = arguments.getString("tenvi");
             ammount = arguments.getDouble("ammount");
             currency_unit = arguments.getString("currency_unit");
             tongsovi = arguments.getDouble("tongsovi");

            // Now you can use "tenvi" and "ammount" in your fragment
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        listView = view.findViewById(R.id.listView);
        ImageView imageView = view.findViewById(R.id.optionsVi);
        imageView.setVisibility(View.VISIBLE);
        // Tìm ImageView unseen
        ImageView unseen = view.findViewById(R.id.unseen);
        TextView text = view.findViewById(R.id.total_blance);
        ImageView noti = view.findViewById(R.id.noti);
        TextView textView = view.findViewById(R.id.tenVi);
        textView.setText(TenVi);
        if(TenVi.equals("Total"))
        {
            textView.setTextColor(Color.parseColor("#1EABED"));
        }
        else
        {
            textView.setTextColor(Color.GRAY);
        }
        //add category test
        TextView category = view.findViewById(R.id.seerp);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Category_Add.class);
                startActivity(intent);
            }
        });
        /// data
        BarChart barChart = view.findViewById(R.id.barChart);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);


        ArrayList<BarEntry> dataValues1 = new ArrayList<>();
        dataValues1.add(new BarEntry(0, 40f)); // Số liệu cho cột 1

        ArrayList<BarEntry> dataValues2 = new ArrayList<>();
        dataValues2.add(new BarEntry(1, 30f)); // Số liệu cho cột 2

        BarDataSet barDataSet1 = new BarDataSet(dataValues1, "Last month");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(dataValues2, "This month");
        barDataSet2.setColor(Color.BLUE);



        BarData data = new BarData(barDataSet1, barDataSet2);
        float groupSpace = 0f; // Khoảng cách giữa các nhóm
        float barSpace = 0.3f; // Không có khoảng cách giữa các cột trong cùng một nhóm
        float barWidth = 0.7f; // Chiếm phần lớn không gian nhóm nhưng không toàn bộ

        data.setBarWidth(barWidth);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(2);
        barChart.getAxisLeft().setAxisMinimum(0); // Đặt cột y bắt đầu từ số 0
        barChart.getAxisRight().setAxisMinimum(0);


        ///


        TextView totalbalance = view.findViewById(R.id.total_blance);
        totalbalance.setText(doitien.formatValue(ammount)+ currency_unit);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notificaiton.class);
                startActivity(intent);
            }
        });
        ImageView optionVi = view.findViewById(R.id.optionsVi);
        optionVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Wallet_Information.class);
                startActivity(intent);
            }
        });

        // Đặt OnClickListener cho unseen
        unseen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(text.getText().equals("**********"))
                {
                    unseen.setBackgroundResource(R.drawable.eye);
                    text.setText(doitien.formatValue(ammount)+ " đ");
                }

               else
                {
                    text.setText("**********");
                    unseen.setBackgroundResource(R.drawable.eyedown);
                }


            }
        });
        TextView month= view.findViewById(R.id.month);
        TextView year= view.findViewById(R.id.year);
        ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
        month.setBackgroundTintList(colorStateList);
        ColorStateList colorStateList2= ColorStateList.valueOf(Color.parseColor("#ACACAC"));
        year.setBackgroundTintList(colorStateList2);

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                month.setBackgroundTintList(colorStateList);
                ColorStateList colorStateList2= ColorStateList.valueOf(Color.parseColor("#ACACAC"));
                year.setBackgroundTintList(colorStateList2);
                BarChart barChart = view.findViewById(R.id.barChart);
                barChart.setTouchEnabled(false);
                barChart.setDragEnabled(false);
                barChart.setScaleEnabled(false);
                barChart.setPinchZoom(false);

                // Tạo dữ liệu mới cho BarChart
                ArrayList<BarEntry> dataValues1 = new ArrayList<>();
                dataValues1.add(new BarEntry(0, 40f)); // Số liệu mới cho cột 1

                ArrayList<BarEntry> dataValues2 = new ArrayList<>();
                dataValues2.add(new BarEntry(1, 60f)); // Số liệu mới cho cột 2

                BarDataSet barDataSet1 = new BarDataSet(dataValues1, "Last month");
                barDataSet1.setColor(Color.RED);
                BarDataSet barDataSet2 = new BarDataSet(dataValues2, "This month");
                barDataSet2.setColor(Color.BLUE);

                BarData data = new BarData(barDataSet1, barDataSet2);
                float groupSpace = 0f; // Khoảng cách giữa các nhóm
                float barSpace = 0.3f; // Không có khoảng cách giữa các cột trong cùng một nhóm
                float barWidth = 0.7f; // Chiếm phần lớn không gian nhóm nhưng không toàn bộ

                data.setBarWidth(barWidth);
                barChart.setData(data);
                barChart.groupBars(0, groupSpace, barSpace);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(2);
                barChart.getAxisLeft().setAxisMinimum(0); // Đặt cột y bắt đầu từ số 0
                barChart.getAxisRight().setAxisMinimum(0);

                // Vẽ lại BarChart với dữ liệu mới
                barChart.invalidate();

            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                year.setBackgroundTintList(colorStateList);
                ColorStateList colorStateList2= ColorStateList.valueOf(Color.parseColor("#ACACAC"));
                month.setBackgroundTintList(colorStateList2);

                // Tìm BarChart và vô hiệu hóa tất cả các tương tác
                BarChart barChart = view.findViewById(R.id.barChart);
                barChart.setTouchEnabled(false);
                barChart.setDragEnabled(false);
                barChart.setScaleEnabled(false);
                barChart.setPinchZoom(false);

                // Tạo dữ liệu mới cho BarChart
                ArrayList<BarEntry> dataValues1 = new ArrayList<>();
                dataValues1.add(new BarEntry(0, 50f)); // Số liệu mới cho cột 1

                ArrayList<BarEntry> dataValues2 = new ArrayList<>();
                dataValues2.add(new BarEntry(1, 60f)); // Số liệu mới cho cột 2

                BarDataSet barDataSet1 = new BarDataSet(dataValues1, "Last month");
                barDataSet1.setColor(Color.RED);
                BarDataSet barDataSet2 = new BarDataSet(dataValues2, "This month");
                barDataSet2.setColor(Color.BLUE);

                BarData data = new BarData(barDataSet1, barDataSet2);
                float groupSpace = 0f; // Khoảng cách giữa các nhóm
                float barSpace = 0.3f; // Không có khoảng cách giữa các cột trong cùng một nhóm
                float barWidth = 0.7f; // Chiếm phần lớn không gian nhóm nhưng không toàn bộ

                data.setBarWidth(barWidth);
                barChart.setData(data);
                barChart.groupBars(0, groupSpace, barSpace);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(2);
                barChart.getAxisLeft().setAxisMinimum(0); // Đặt cột y bắt đầu từ số 0
                barChart.getAxisRight().setAxisMinimum(0);

                // Vẽ lại BarChart với dữ liệu mới
                barChart.invalidate();
            }
        });
        // Tìm TextView Seeall
        TextView seeAll = view.findViewById(R.id.Seeall);

        // Đặt OnClickListener cho Seeall
        seeAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(seeAll.getText().toString().equals("See all"))
                {
                    Intent event;


                    Intent intent = new Intent(getActivity(), Home_My_wallets.class);

                    TextView vi = view.findViewById(R.id.tenVi);
                    Log.d("adfdf",vi.getText().toString());
                    intent.putExtra("viduocchon",vi.getText());
                    // Bắt đầu Activity mới
                    startActivity(intent);
                }
                else
                {
                    Intent event;

                    Intent intent = new Intent(getActivity(), Home_New_wallet.class);
                    startActivityForResult(intent, 1);
                }

            }
        });


        TextView showmore = view.findViewById(R.id.showmore);
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tao intent show more
                Intent ShowMoreIntent = new Intent(getActivity(), TransactionDetail.class);

                // Mo activity transaction details
                startActivity(ShowMoreIntent);
            }
        });
       theGiaoDichAdap= new Home_TheGiaoDich_Adapter(getContext(),R.layout.home_dong_giao_dich,theGiaoDichList);
        listView.setAdapter(theGiaoDichAdap);
        setListViewHeightBasedOnChildren(listView);
        ListView listView2 = view.findViewById(R.id.listView2);
        theTopSpentAdap = new Home_TheTopSpent_Adapter(getContext(),R.layout.home_dong_topspent,theTopSpentList);
        listView2.setAdapter(theTopSpentAdap);
        setListViewHeightBasedOnChildren(listView2);
        if(tongsovi==0)
        {
            TextView tenvi = view.findViewById(R.id.tenVi);
            tenvi.setText("No wallet");
            tenvi.setTextColor(Color.GRAY);
            TextView seeall = view.findViewById(R.id.Seeall);
            seeall.setText("ADD NEW");
            ImageView op = view.findViewById(R.id.optionsVi);
            op.setVisibility(View.INVISIBLE);
        }



        return view;



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tenvi = getView().findViewById(R.id.tenVi);
        TextView totalbalance = getView().findViewById(R.id.total_blance);

        // Check if the request code is the same as the one used in startActivityForResult()
        if (requestCode == 1) {

            try {

                ArrayList<GetAllWalletsEntity> parseAPIList = new WalletAPIUtil().getAllWalletAPI();
                //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
                for (GetAllWalletsEntity item : parseAPIList) {
                        tenvi.setText(item.name);
                        if(item.currency_unit.equals("VND"))
                        {
                            totalbalance.setText(doitien.formatValue(Double.parseDouble(item.amount))+ " đ");
                        }
                        else if(item.currency_unit.equals("USD"))
                        {
                            totalbalance.setText(doitien.formatValue(Double.parseDouble(item.amount))+ " $");
                        }
                        else if(item.currency_unit.equals("EUR"))
                        {
                            totalbalance.setText(doitien.formatValue(Double.parseDouble(item.amount))+ " €");
                        }
                        else if(item.currency_unit.equals("CNY"))
                        {
                            totalbalance.setText(doitien.formatValue(Double.parseDouble(item.amount))+ " ¥");
                        }

                    break;
                }

            }
            catch (Exception e) {
                //Thông báo lỗi, không thể kết nối đến server, co the hien mot notification ra app
                e.printStackTrace();
            }
            if(tenvi.getText().toString().equals("No wallet")) {

            }
            else {
                TextView seeall = getView().findViewById(R.id.Seeall);
                seeall.setText("See all");
                ImageView op = getView().findViewById(R.id.optionsVi);
                op.setVisibility(View.VISIBLE);
            }


        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}