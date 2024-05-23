package com.example.qlct.Transaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllTransactionsEntity;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Utils.CategoryAPIUtil;
import com.example.qlct.API_Utils.TransactionAPIUtil;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.Category.Category_hdp;
import com.example.qlct.R;
import com.example.qlct.SelectWallet_Adapter;
import com.example.qlct.Wallet_hdp;
import com.example.qlct.doitiente;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TransactionDetail extends AppCompatActivity {

    private List<Category_hdp> categoryList = new ArrayList<>();
    private ExpandableListView expandableListView;
    private List<TransactionDetail_ExpandableListItems> listDataHeader;
    private List<TransactionDetail_TheGiaoDich> theGiaoDichList = new ArrayList<>();
    private HashMap<TransactionDetail_ExpandableListItems, List<TransactionDetail_TheGiaoDich>> listDataChild;
    private TransactionDetail_ExpandableListView expandableListAdapter;
    private TextView apply;
    private TextView date;
    private TextView day;
    private TextView month;
    private TextView year;
    private TextView week;
    private TextView timespan;
    private ImageView back;
    private ImageView next;
    private static String status = "day";
    private LinearLayout time;
    private LinearLayout from_to;
    private TextView from,to;

    private ListView walletListView;

    private ArrayList<Wallet_hdp> walletList;

    private TextView dateTxtView;

    private ArrayList<Chip> incomeChipList = new ArrayList<>();

    private ArrayList<Chip> expenseChipList = new ArrayList<>();

    private CircularProgressIndicator loading;

    private void AnhXaWallet(){
        try{
            walletList = new ArrayList<Wallet_hdp>();
            ArrayList<GetAllWalletsEntity> parseAPIList = new WalletAPIUtil().getAllWalletAPI();
            //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
            for (GetAllWalletsEntity item : parseAPIList) {
                walletList.add(new Wallet_hdp(item.id, item.name, item.amount, R.drawable.wallet, item.currency_unit));
            }
            walletList.add(new Wallet_hdp("total", "TOTAL", "", R.drawable.wallet, ""));
            Log.d("Get_wallet_data_object", walletList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void setIncomeBackground(MaterialButton income_btn){
        if (income){
            income_btn.setBackgroundColor(getResources().getColor(R.color.xanhdam));
            income_btn.setTextColor(getResources().getColor(R.color.white));
            income_btn.setIconTint(getColorStateList(R.color.white));
            income_btn.setRippleColor(getColorStateList(R.color.xanhnhat));
        }else{
            income_btn.setBackgroundColor(getResources().getColor(R.color.white));
            income_btn.setTextColor(getResources().getColor(R.color.black));
            income_btn.setIconTint(getColorStateList(R.color.black));
            income_btn.setRippleColor(getColorStateList(R.color.xanhdam));
        }
    }

    private void setExpenseBackground(MaterialButton expense_btn){
        if (expense){
            expense_btn.setBackgroundColor(getResources().getColor(R.color.red));
            expense_btn.setTextColor(getResources().getColor(R.color.white));
            expense_btn.setIconTint(getColorStateList(R.color.white));
            expense_btn.setRippleColor(getColorStateList(R.color.lightred));
        }else{
            expense_btn.setBackgroundColor(getResources().getColor(R.color.white));
            expense_btn.setTextColor(getResources().getColor(R.color.black));
            expense_btn.setIconTint(getColorStateList(R.color.black));
            expense_btn.setRippleColor(getColorStateList(R.color.red));
        }
    }

    private boolean income = true;
    private boolean expense = false;

    private void showDialog(){
        final Dialog dialog = new Dialog(TransactionDetail.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_transaction_type);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);

        dialog.show();
        AnhXaCategory();

        MaterialButton income_btn = dialog.findViewById(R.id.income_button);
        income_btn.setBackgroundColor(getResources().getColor(R.color.xanhdam));
        income_btn.setTextColor(getResources().getColor(R.color.white));
        income_btn.setIconTint(getColorStateList(R.color.white));
        income_btn.setRippleColor(getColorStateList(R.color.xanhnhat));

        ScrollView incomeScrollView = dialog.findViewById(R.id.income_chipgroup_scrollview);
        ScrollView expenseScrollView = dialog.findViewById(R.id.expense_chipgroup_scrollview);
        expenseScrollView.setVisibility(View.GONE);

        MaterialButton expense_btn = dialog.findViewById(R.id.expense_button);

        ChipGroup incomeChipGroup = dialog.findViewById(R.id.income_chipgroup);
        ChipGroup expenseChipGroup = dialog.findViewById(R.id.expense_chipgroup);
        AnhXaCategoryToChip(incomeChipGroup, expenseChipGroup);


        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = true;
                expense = false;
                setIncomeBackground(income_btn);
                setExpenseBackground(expense_btn);
                ScrollView incomeScrollView = dialog.findViewById(R.id.income_chipgroup_scrollview);
                ScrollView expenseScrollView = dialog.findViewById(R.id.expense_chipgroup_scrollview);
                incomeScrollView.setVisibility(View.VISIBLE);
                expenseScrollView.setVisibility(View.GONE);
            }
        });

        expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = false;
                expense = true;
                setIncomeBackground(income_btn);
                setExpenseBackground(expense_btn);
                ScrollView incomeScrollView = dialog.findViewById(R.id.income_chipgroup_scrollview);
                ScrollView expenseScrollView = dialog.findViewById(R.id.expense_chipgroup_scrollview);
                incomeScrollView.setVisibility(View.GONE);
                expenseScrollView.setVisibility(View.VISIBLE);
            }
        });

        TextView apply = dialog.findViewById(R.id.trans_type_apply_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    addCheckedChipsToLists(dialog);
                    Toast.makeText(dialog.getContext(), String.valueOf(incomeChipList.size()) + String.valueOf(expenseChipList.size()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
                dialog.dismiss();
                ChipGroup chipGroup = findViewById(R.id.chipgroup);
                chipGroup.removeAllViews();
                AnhXaChipToFilter(chipGroup);
            }
        });
    }

    private void showWalletDialog(){
        final Dialog dialog = new Dialog(TransactionDetail.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_select_wallet);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);

        dialog.show();

        walletListView = dialog.findViewById(R.id.select_wallet_listview);
        AnhXaWallet();
        SelectWallet_Adapter adapter = new SelectWallet_Adapter(TransactionDetail.this, R.layout.select_wallet_item_list, walletList);
        walletListView.setAdapter(adapter);

        walletListView.setOnItemClickListener((parent, view, position, id) -> {
            Wallet_hdp wallet = new Wallet_hdp("", "", "", 0, "");
            wallet = walletList.get(position);
            TextView walletName = findViewById(R.id.wallet_name_txtview);
            walletName.setText(wallet.getWalletName());
            dialog.dismiss();
        });
    }

    private void showDateDialog(){
        final Dialog dateDialog = new Dialog(TransactionDetail.this);

        dateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dateDialog.setContentView(R.layout.bottomsheet_filter_by_date);

        dateDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dateDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dateDialog.getWindow().setGravity(Gravity.BOTTOM);
        dateDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);

        dateDialog.show();

        apply = dateDialog.findViewById(R.id.apply);
        date = dateDialog.findViewById(R.id.date);
        day = dateDialog.findViewById(R.id.day);
        month = dateDialog.findViewById(R.id.month);
        year = dateDialog.findViewById(R.id.year);
        week = dateDialog.findViewById(R.id.week);
        timespan = dateDialog.findViewById(R.id.time_span);
        back = dateDialog.findViewById(R.id.back);
        next = dateDialog.findViewById(R.id.next);
        time = dateDialog.findViewById(R.id.time);
        from_to = dateDialog.findViewById(R.id.from_to);
        from = dateDialog.findViewById(R.id.from);
        to = dateDialog.findViewById(R.id.to);

        Calendar calendar = Calendar.getInstance();
        // Lấy ngày, tháng, năm hiện tại
        int dayy = calendar.get(Calendar.DAY_OF_MONTH);
        int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int yearr = calendar.get(Calendar.YEAR);
        date.setText(dayy + "/" + monthh + "/" + yearr);
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="day";
                Calendar calendar = Calendar.getInstance();
                // Lấy ngày, tháng, năm hiện tại
                int dayy = calendar.get(Calendar.DAY_OF_MONTH);
                int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int yearr = calendar.get(Calendar.YEAR);
                date.setText(dayy + "/" + monthh + "/" + yearr);
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="month";
                int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int yearr = calendar.get(Calendar.YEAR);
                date.setText( monthh + "/" + yearr);
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                day.setBackground(null);
                year.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="year";
                int yearr = calendar.get(Calendar.YEAR);
                date.setText(yearr+"");
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                month.setBackground(null);
                day.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="week";
                Calendar calendar = Calendar.getInstance();
                // Lấy ngày, tháng, năm hiện tại
                int dayy = calendar.get(Calendar.DAY_OF_MONTH);
                int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int yearr = calendar.get(Calendar.YEAR);
                date.setText(getStartAndEndOfWeek(dayy + "/" + monthh + "/" + yearr));
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                day.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });
        timespan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status="timespan";
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                day.setBackground(null);
                time.setVisibility(View.GONE);
                from_to.setVisibility(View.VISIBLE);
                from = dateDialog.findViewById(R.id.from);
                to = dateDialog.findViewById(R.id.to);
                from.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(dateDialog.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        from.setText(format.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                });
                to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(dateDialog.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        if(compareDates(from.getText().toString(),format.format(calendar.getTime()))==1 || compareDates(from.getText().toString(),format.format(calendar.getTime()))==0)
                                        {
                                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được lớn bé hoặc bằng ngày bắt đầu", Toast.LENGTH_LONG).show();
                                        }
//                                        else if(!isEndDateLessThanCurrent(". - "+format.format(calendar.getTime())))
//                                        {
//                                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
//                                        }
                                        else
                                            to.setText(format.format(calendar.getTime()));

                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                });
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "day":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích ngày từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng ngày lên 1
                        calendar.add(Calendar.DAY_OF_MONTH, 1);

                        // Định dạng ngày mới và đặt nó vào TextView
                        if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime()))) {
                            Toast.makeText(dateDialog.getContext(), "Ngày không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        else
                            date.setText(format.format(calendar.getTime()));
                    }
                    break;
                    case "month":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích tháng từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng tháng lên 1
                        calendar.add(Calendar.MONTH, 1);

                        if(!isDateLessThanCurrent(format.format(calendar.getTime())))
                        {
                            Toast.makeText(dateDialog.getContext(), "Tháng không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                            calendar.add(Calendar.MONTH, 1);
                        }
                        else
                            date.setText(format.format(calendar.getTime()));
                    }
                    break;
                    case "year":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.getDefault());
                        try {
                            // Phân tích năm từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng năm lên 1
                        calendar.add(Calendar.YEAR, 1);

                        if(!isYearLessThanCurrent(format.format(calendar.getTime())))
                        {
                            Toast.makeText(dateDialog.getContext(), "Năm không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                            calendar.add(Calendar.YEAR, 1);
                        }
                        else
                            date.setText(format.format(calendar.getTime()));

                    }
                    break;
                    case "week":
                    {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Calendar calendarStart = Calendar.getInstance();
                        Calendar calendarEnd = Calendar.getInstance();
                        try {
                            // Phân tích ngày từ TextView
                            String[] dates = date.getText().toString().split(" - ");
                            calendarStart.setTime(format.parse(dates[0]));
                            calendarEnd.setTime(format.parse(dates[1]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng ngày lên 7 (1 tuần)
                        calendarStart.add(Calendar.DAY_OF_MONTH, 7);
                        calendarEnd.add(Calendar.DAY_OF_MONTH, 7);

                        if(isEndDateLessThanCurrent(". - "+format.format(calendarStart.getTime()))) {
                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                            calendarStart.add(Calendar.DAY_OF_MONTH, 7);
                            calendarEnd.add(Calendar.DAY_OF_MONTH, 7);
                        }
                        else
                        {
                            // Định dạng ngày mới và đặt nó vào TextView
                            date.setText(format.format(calendarStart.getTime()) + " - " + format.format(calendarEnd.getTime()));
                        }

                    }
                    break;
                    case "timespan":
                    {


                    }
                    break;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "day":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích ngày từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm ngày đi 1
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        date.setText(format.format(calendar.getTime()));
                    }
                    break;
                    case "month":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích tháng từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm tháng đi 1
                        calendar.add(Calendar.MONTH, -1);
                        date.setText(format.format(calendar.getTime()));
                    }
                    break;
                    case "year":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.getDefault());
                        try {
                            // Phân tích năm từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm năm đi 1
                        calendar.add(Calendar.YEAR, -1);
                        date.setText(format.format(calendar.getTime()));


                    }
                    break;
                    case "week":
                    {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Calendar calendarStart = Calendar.getInstance();
                        Calendar calendarEnd = Calendar.getInstance();
                        try {
                            // Phân tích ngày từ TextView
                            String[] dates = date.getText().toString().split(" - ");
                            calendarStart.setTime(format.parse(dates[0]));
                            calendarEnd.setTime(format.parse(dates[1]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm ngày đi 7 (1 tuần)
                        calendarStart.add(Calendar.DAY_OF_MONTH, -7);
                        calendarEnd.add(Calendar.DAY_OF_MONTH, -7);
                        date.setText(format.format(calendarStart.getTime()) + " - " + format.format(calendarEnd.getTime()));


                    }
                    break;
                }
            }
        });

        TextView allTime = dateDialog.findViewById(R.id.all_time);
        allTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTxtView.setText("All time");
                dateDialog.dismiss();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status=="timespan")
                {
                    dateTxtView.setText(from.getText().toString()+" - "+to.getText().toString());
                }
                else {
                    dateTxtView.setText(date.getText().toString());
                }
                dateDialog.dismiss();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "day":
                    {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(dateDialog.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime()))==true)
                                        {
                                            Toast.makeText(dateDialog.getContext(), "Ngày không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                            date.setText(format.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                    break;
                    case "month":
                    {
                        Dialog dialog = new Dialog(dateDialog.getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_month_picker);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        GridView gridView = dialog.findViewById(R.id.gridView);
                        TextView month_lbl = dialog.findViewById(R.id.month);
                        TextView year_lbl = dialog.findViewById(R.id.year);
                        TextView cancel = dialog.findViewById(R.id.cancel);
                        TextView ok = dialog.findViewById(R.id.ok);
                        ImageView up = dialog.findViewById(R.id.up);
                        ImageView down = dialog.findViewById(R.id.down);
                        String[] month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                        ArrayAdapter monthAdapter = new ArrayAdapter(dialog.getContext(), android.R.layout.simple_list_item_1, month) ;
                        gridView.setAdapter(monthAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                month_lbl.setText(month[position]);
                            }
                        });
                        up.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())+1+"");
                            }
                        });
                        down.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                int currentYear = calendar.get(Calendar.YEAR);
                                if(Integer.parseInt(year_lbl.getText().toString())<=currentYear)
                                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                                else
                                    Toast.makeText(dialog.getContext(), "Năm không được lớn hơn "+currentYear, Toast.LENGTH_LONG).show();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String inputDate = month_lbl.getText().toString()+" "+year_lbl.getText().toString();
                                SimpleDateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                                SimpleDateFormat outputFormat = new SimpleDateFormat("M/yyyy", Locale.ENGLISH);

                                try {
                                    Date daTe = inputFormat.parse(inputDate);
                                    String outputDate = outputFormat.format(daTe);
                                    if(!isDateLessThanCurrent(outputDate))
                                    {
                                        Toast.makeText(dialog.getContext(), "Tháng không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        date.setText(outputDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                dialog.dismiss();
                            }
                        });
                    }
                    break;
                    case "year":
                    {
                        Dialog dialog = new Dialog(dateDialog.getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_year_picker);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        GridView gridView = dialog.findViewById(R.id.gridView);
                        TextView year_lbl = dialog.findViewById(R.id.year);
                        TextView cancel = dialog.findViewById(R.id.cancel);
                        TextView ok = dialog.findViewById(R.id.ok);
                        ImageView up = dialog.findViewById(R.id.up);
                        ImageView down = dialog.findViewById(R.id.down);
                        String[] years={"2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035"};
                        ArrayAdapter yearAdapter = new ArrayAdapter(dialog.getContext(), android.R.layout.simple_list_item_1, years) ;
                        gridView.setAdapter(yearAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                year_lbl.setText(years[position]);
                            }
                        });
                        up.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())+1+"");
                            }
                        });
                        down.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                int currentYear = calendar.get(Calendar.YEAR);
                                if(Integer.parseInt(year_lbl.getText().toString())<=currentYear)
                                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                                else
                                    Toast.makeText(dialog.getContext(), "Năm không được lớn hơn "+currentYear, Toast.LENGTH_LONG).show();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!isYearLessThanCurrent(year_lbl.getText().toString()))
                                {
                                    Toast.makeText(dialog.getContext(), "Năm không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                                }
                                else
                                    date.setText(year_lbl.getText().toString());
                                dialog.dismiss();
                            }
                        });
                    }
                    break;
                    case "week":
                    {
                        final Calendar b = Calendar.getInstance();
                        int year = b.get(Calendar.YEAR);
                        int month = b.get(Calendar.MONTH);
                        int day = b.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(dateDialog.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        if(isEndDateLessThanCurrent(getStartAndEndOfWeek(format.format(calendar.getTime())))==false)
                                        {
                                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                            date.setText(getStartAndEndOfWeek(format.format(calendar.getTime())));
                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                    break;
                    case "timespan":
                    {}
                    break;
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        expandableListView = findViewById(R.id.trans_detail_listview);
        expandableListView.setGroupIndicator(null);

        loading = findViewById(R.id.progressBar);

        AnhXaWallet();
//        ExecutorService excuterService = Executors.newSingleThreadExecutor();
////        // Hiển thị ProgressBar
////        loading.setVisibility(View.VISIBLE);
////
////        excuterService.execute(new Runnable() {
////            @Override
////            public void run() {
////
////                setupExpandableListView();
////
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        expandableListView = findViewById(R.id.trans_detail_listview);
////                        expandableListView.setGroupIndicator(null);
////                        expandableListAdapter = new TransactionDetail_ExpandableListView(TransactionDetail.this, listDataHeader, listDataChild);
////                        expandableListView.setAdapter(expandableListAdapter);
////                        for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
////                            expandableListView.expandGroup(i);
////                        }
////
////                        loading.setVisibility(View.GONE);
////                    }
////                });
////            }
////        });

        TextView noTransaction = findViewById(R.id.no_transaction);
        setupExpandableListView(theGiaoDichList);
        expandableListView = findViewById(R.id.trans_detail_listview);
        expandableListView.setGroupIndicator(null);
        expandableListAdapter = new TransactionDetail_ExpandableListView(TransactionDetail.this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
        for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        noTransaction.setVisibility(View.GONE);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // Lấy thông tin của mục con được nhấp
                TransactionDetail_TheGiaoDich clickedItem = (TransactionDetail_TheGiaoDich) expandableListAdapter.getChild(groupPosition, childPosition);

                // Tạo một Intent để khởi động Activity mới
                Intent intent = new Intent(TransactionDetail.this, ViewTransaction.class);

                // Đưa thông tin của mục con vào Intent
                intent.putExtra("clickedItem", clickedItem);

                // Khởi động Activity mới
                startActivity(intent);

                // Trả về true nếu sự kiện onClick đã được xử lý, false nếu không
                return true;
            }
        });

        dateTxtView = findViewById(R.id.trans_detail_date_txtview);
        dateTxtView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Code to execute before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Code to execute when text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Code to execute after text has changed
                reloadExpandableListView();
            }
        });

        TextView walletName = findViewById(R.id.wallet_name_txtview);
        walletName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Code to execute before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Code to execute when text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Code to execute after text has changed
                reloadExpandableListView();
            }
        });

        ChipGroup chipGroup = findViewById(R.id.chipgroup);
        chipGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                // Code to execute when a new chip is added to the ChipGroup
                reloadExpandableListView();
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                // Code to execute when a chip is removed from the ChipGroup
                reloadExpandableListView();
            }
        });

        ImageButton backbutton = findViewById(R.id.trans_detail_back_btn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton walletbutton = findViewById(R.id.trans_detail_wallet_icon);
        walletbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletDialog();
            }
        });

        ImageButton morebutton = findViewById(R.id.trans_detail_more_option_btn);
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = true;
                expense = false;
                //Toast.makeText(TransactionDetail.this, String.valueOf(incomeChipList.size()) + " " + String.valueOf(expenseChipList.size()), Toast.LENGTH_SHORT).show();
                showDialog();
                ChipGroup chipGroup = findViewById(R.id.chipgroup);
                AnhXaChipToFilter(chipGroup);
            }
        });

        ImageButton calenderIcon = findViewById(R.id.trans_detail_calendar_icon);
        calenderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = findViewById(R.id.trans_detail_date_txtview);
                showDateDialog();
            }
        });

        FloatingActionButton addTransaction = findViewById(R.id.trans_detail_add_trans);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionDetail.this, AddTransaction.class);
                startActivity(intent);
            }
        });
    }

    public String getStartAndEndOfWeek(String inputDate) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            // Phân tích ngày từ input
            calendar.setTime(format.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Đặt calendar về ngày đầu tuần (Thứ 2)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        // In ra ngày thứ 2
        System.out.println("Monday: " + format.format(calendar.getTime()));
        result=result+ format.format(calendar.getTime())+" - ";
        // Đặt calendar về ngày cuối tuần (Chủ nhật)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        // In ra ngày chủ nhật
        result=result+ format.format(calendar.getTime());
        return result;
    }
    public boolean isEndDateLessThanCurrent(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            // Tách chuỗi ngày thành hai chuỗi riêng biệt
            String[] dates = dateString.split(" - ");
            // Chuyển đổi chuỗi ngày thứ hai thành đối tượng Date
            Date endDate = format.parse(dates[1]);
            Calendar calendar = Calendar.getInstance();
            //calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date currentDate = calendar.getTime();
            // So sánh ngày thứ hai với ngày hiện tại
            return !(endDate.before(currentDate)) ;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int compareDates(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);

            if (d1.before(d2)) {
                return -1; // date1 nhỏ hơn date2
            } else if (d1.after(d2)) {
                return 1; // date1 lớn hơn date2
            } else {
                return 0; // date1 bằng date2
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public boolean isDateLessThanCurrent(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("M/yyyy");
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date inputDate = format.parse(dateString);

            // Lấy tháng và năm hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1

            // Lấy tháng và năm từ chuỗi ngày
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);
            int inputYear = inputCalendar.get(Calendar.YEAR);
            int inputMonth = inputCalendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1

            // So sánh tháng và năm
            if (inputYear < currentYear || (inputYear == currentYear && inputMonth <= currentMonth)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isYearLessThanCurrent(String yearString) {
        // Chuyển đổi chuỗi năm thành số nguyên
        int inputYear = Integer.parseInt(yearString);

        // Lấy năm hiện tại
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);

        // So sánh năm
        if (inputYear <= currentYear) {
            return true;
        } else {
            return false;
        }
    }

    public void setupExpandableListView(List<TransactionDetail_TheGiaoDich> TransactionList){
        if(TransactionList.isEmpty()){
            try{
                TransactionAPIUtil transactionAPIUtil = new TransactionAPIUtil();
                ArrayList<GetAllTransactionsEntity> parseAPIList = transactionAPIUtil.getAllTransactionsAPI();
                for(GetAllTransactionsEntity item : parseAPIList){
                    if(item.category != null){
                        TransactionList.add(new TransactionDetail_TheGiaoDich(item.picture, item.category.picture, item.category, item.amount, item.transaction_date, item.transaction_type, item.notes, item.currency_unit, findWalletById(item.wallet_id), findWalletById(item.target_wallet_id)));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        // Adding header data
        listDataHeader = new ArrayList<>();
        //Toast.makeText(TransactionDetail.this, formatDate(theGiaoDichList.get(0).getNgayThang()), Toast.LENGTH_SHORT).show();

        TransactionList.sort(Comparator.comparing(TransactionDetail_TheGiaoDich::getNgayThang_LocalDate));

        doitiente doi_tien_te = new doitiente();

        if(!TransactionList.isEmpty()){
            if(TransactionList.get(0).getLoaiGiaoDich().equals("INCOME")){
                listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(0).getNgayThang())), doi_tien_te.converttoVND(TransactionList.get(0).getDonViTien(), Double.parseDouble(TransactionList.get(0).getSoTien())) , 0, TransactionList.get(0).getDonViTien()));
            }
            else if(TransactionList.get(0).getLoaiGiaoDich().equals("EXPENSE")){
                listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(0).getNgayThang())), 0, doi_tien_te.converttoVND(TransactionList.get(0).getDonViTien(), Double.parseDouble(TransactionList.get(0).getSoTien())), TransactionList.get(0).getDonViTien()));
            }
            else{
                listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(0).getNgayThang())), 0, 0, TransactionList.get(0).getDonViTien()));
            }

            for(int i = 1; i < TransactionList.size(); i++){
                if(!TransactionList.get(i).getNgayThang().equals(TransactionList.get(i - 1).getNgayThang())){
                    if(TransactionList.get(i).getViTien() != null){
                        if(TransactionList.get(i).getLoaiGiaoDich().equals("INCOME")){
                            listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(i).getNgayThang())), doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())), 0, TransactionList.get(i).getDonViTien()));
                        }
                        else if(TransactionList.get(i).getLoaiGiaoDich().equals("EXPENSE")){
                            listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(i).getNgayThang())), 0, doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())), TransactionList.get(i).getDonViTien()));
                        }
                        else{
                            listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(i).getNgayThang())), 0, 0, TransactionList.get(i).getDonViTien()));
                        }
                    }
                }
                else{
                    if(TransactionList.get(i).getLoaiGiaoDich().equals("INCOME")){
                        listDataHeader.get(listDataHeader.size() - 1).setTotalIncome(doi_tien_te.converttoVND("VND", listDataHeader.get(listDataHeader.size() - 1).getTotalIncome()) + doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())));
                    }
                    else if(TransactionList.get(i).getLoaiGiaoDich().equals("EXPENSE")){
                        listDataHeader.get(listDataHeader.size() - 1).setTotalExpense(doi_tien_te.converttoVND("VND", listDataHeader.get(listDataHeader.size() - 1).getTotalExpense()) + doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())));
                    }
                }
            }
        }

        listDataChild = new HashMap<TransactionDetail_ExpandableListItems, List<TransactionDetail_TheGiaoDich>>();
        for (TransactionDetail_ExpandableListItems headerItem : listDataHeader) {
            List<TransactionDetail_TheGiaoDich> childList = new ArrayList<>();

            // Duyệt qua từng phần tử trong theGiaoDichList
            for (TransactionDetail_TheGiaoDich giaoDichItem : TransactionList) {
                // So sánh ngày của giaoDichItem với ngày của headerItem
                if (formatDate(giaoDichItem.getNgayThang()).equals(headerItem.getTime().toString())) {
                    // Nếu ngày giống nhau, thêm giaoDichItem vào childList
                    childList.add(giaoDichItem);
                }
            }

            // Thêm childList vào listDataChild với key là headerItem
            listDataChild.put(headerItem, childList);
        }

    }

    public void filterExpandableListView(List<TransactionDetail_TheGiaoDich> TransactionList){
        TextView noTransaction = findViewById(R.id.no_transaction);
        ExpandableListView expandableListView = findViewById(R.id.trans_detail_listview);

        if(TransactionList.isEmpty()){
            noTransaction.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
        }
        else{
            noTransaction.setVisibility(View.GONE);
            expandableListView.setVisibility(View.VISIBLE);

            // Adding header data
            listDataHeader = new ArrayList<>();
            //Toast.makeText(TransactionDetail.this, formatDate(theGiaoDichList.get(0).getNgayThang()), Toast.LENGTH_SHORT).show();

            TransactionList.sort(Comparator.comparing(TransactionDetail_TheGiaoDich::getNgayThang_LocalDate));

            doitiente doi_tien_te = new doitiente();

            if(!TransactionList.isEmpty()){
                if(TransactionList.get(0).getLoaiGiaoDich().equals("INCOME")){
                    listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(0).getNgayThang())), doi_tien_te.converttoVND(TransactionList.get(0).getDonViTien(), Double.parseDouble(TransactionList.get(0).getSoTien())) , 0, TransactionList.get(0).getDonViTien()));
                }
                else if(TransactionList.get(0).getLoaiGiaoDich().equals("EXPENSE")){
                    listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(0).getNgayThang())), 0, doi_tien_te.converttoVND(TransactionList.get(0).getDonViTien(), Double.parseDouble(TransactionList.get(0).getSoTien())), TransactionList.get(0).getDonViTien()));
                }
                else{
                    listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(0).getNgayThang())), 0, 0, TransactionList.get(0).getDonViTien()));
                }

                for(int i = 1; i < TransactionList.size(); i++){
                    if(!TransactionList.get(i).getNgayThang().equals(TransactionList.get(i - 1).getNgayThang())){
                        if(TransactionList.get(i).getViTien() != null){
                            if(TransactionList.get(i).getLoaiGiaoDich().equals("INCOME")){
                                listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(i).getNgayThang())), doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())), 0, TransactionList.get(i).getDonViTien()));
                            }
                            else if(TransactionList.get(i).getLoaiGiaoDich().equals("EXPENSE")){
                                listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(i).getNgayThang())), 0, doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())), TransactionList.get(i).getDonViTien()));
                            }
                            else{
                                listDataHeader.add(new TransactionDetail_ExpandableListItems(LocalDate.parse(formatDate(TransactionList.get(i).getNgayThang())), 0, 0, TransactionList.get(i).getDonViTien()));
                            }
                        }
                    }
                    else{
                        if(TransactionList.get(i).getLoaiGiaoDich().equals("INCOME")){
                            listDataHeader.get(listDataHeader.size() - 1).setTotalIncome(doi_tien_te.converttoVND("VND", listDataHeader.get(listDataHeader.size() - 1).getTotalIncome()) + doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())));
                        }
                        else if(TransactionList.get(i).getLoaiGiaoDich().equals("EXPENSE")){
                            listDataHeader.get(listDataHeader.size() - 1).setTotalExpense(doi_tien_te.converttoVND("VND", listDataHeader.get(listDataHeader.size() - 1).getTotalExpense()) + doi_tien_te.converttoVND(TransactionList.get(i).getDonViTien(), Double.parseDouble(TransactionList.get(i).getSoTien())));
                        }
                    }
                }
            }

            listDataChild = new HashMap<TransactionDetail_ExpandableListItems, List<TransactionDetail_TheGiaoDich>>();
            for (TransactionDetail_ExpandableListItems headerItem : listDataHeader) {
                List<TransactionDetail_TheGiaoDich> childList = new ArrayList<>();

                // Duyệt qua từng phần tử trong theGiaoDichList
                for (TransactionDetail_TheGiaoDich giaoDichItem : TransactionList) {
                    // So sánh ngày của giaoDichItem với ngày của headerItem
                    if (formatDate(giaoDichItem.getNgayThang()).equals(headerItem.getTime().toString())) {
                        // Nếu ngày giống nhau, thêm giaoDichItem vào childList
                        childList.add(giaoDichItem);
                    }
                }

                // Thêm childList vào listDataChild với key là headerItem
                listDataChild.put(headerItem, childList);
            }
        }
    }

    private void AnhXaCategory(){
        try{
            categoryList = new ArrayList<Category_hdp>();
            ArrayList<GetAllCategoryEntity> parseAPIList = new CategoryAPIUtil().getAllCategory();
            for(GetAllCategoryEntity category : parseAPIList){
                categoryList.add(new Category_hdp(category.getId(), category.getName(), category.getPicture(), category.getType()));
            }
            Log.d("Get_wallet_data_object", categoryList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AnhXaCategoryToChip(ChipGroup incomeChipGroup, ChipGroup expenseChipGroup){
        for (Category_hdp category : categoryList){
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.filter_chips_layout, null, false);
            chip.setText(category.getCategory_name());
            chip.setChipStrokeColor(getColorStateList(R.color.black));
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
            chip.setTextColor(getResources().getColor(R.color.black, null));
            chip.setCheckable(true);
            chip.setTag(category);

            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(chip.isChecked()){
                        String categoryType = ((Category_hdp)chip.getTag()).getCategory_type();
                        if(categoryType.equals("INCOME")){
                            chip.setRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnhat, null)));
                            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhdam, null)));
                        }else if(categoryType.equals("EXPENSE")){
                            chip.setRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.lightred, null)));
                            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.red, null)));
                        }
                        chip.setCheckedIconVisible(true);
                        chip.setTextColor(getResources().getColor(R.color.white, null));
                        chip.setCheckedIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
                    }else{
                        chip.setCheckedIconVisible(false);
                        chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
                        chip.setTextColor(getResources().getColor(R.color.black, null));
                    }
                }
            });

            if(category.getCategory_type().equals("INCOME")) {
                incomeChipGroup.addView(chip);
            }
            else {
                expenseChipGroup.addView(chip);
            }
        }

        if(!incomeChipList.isEmpty()) {
            for (int i = 0; i < incomeChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) incomeChipGroup.getChildAt(i);
                for (Chip selectedChip : incomeChipList) {
                    if (selectedChip.getTag().equals(chip.getTag())) {
                        chip.setChecked(true);
                        chip.setSelected(true);
                        break;
                    }
                }
            }
        }

        if(!expenseChipList.isEmpty()) {
            for (int i = 0; i < expenseChipGroup.getChildCount(); i++) {
                Chip chip = (Chip) expenseChipGroup.getChildAt(i);
                for (Chip selectedChip : expenseChipList) {
                    if (selectedChip.getTag().equals(chip.getTag())) {
                        chip.setChecked(true);
                        chip.setSelected(true);
                        break;
                    }
                }
            }
        }

        incomeChipList.clear();
        expenseChipList.clear();

        ChipGroup chipGroup = findViewById(R.id.chipgroup);
    }

    private void addCheckedChipsToLists(Dialog dialog) {
        // Clear the lists first
        incomeChipList.clear();
        expenseChipList.clear();

        ChipGroup incomeChipGroup = dialog.findViewById(R.id.income_chipgroup);
        ChipGroup expenseChipGroup = dialog.findViewById(R.id.expense_chipgroup);

        // Iterate over the chips in the incomeChipGroup
        for (int i = 0; i < incomeChipGroup.getChildCount(); i++) {
            View view = incomeChipGroup.getChildAt(i);
            if (view instanceof Chip) {
                Chip chip = (Chip) view;
                if (chip.isChecked()) {
                    // If the chip is checked, add it to the incomeChipList
                    incomeChipList.add(chip);
                }
            }
        }

        // Iterate over the chips in the expenseChipGroup
        for (int i = 0; i < expenseChipGroup.getChildCount(); i++) {
            View view = expenseChipGroup.getChildAt(i);
            if (view instanceof Chip) {
                Chip chip = (Chip) view;
                if (chip.isChecked()) {
                    // If the chip is checked, add it to the expenseChipList
                    expenseChipList.add(chip);
                }
            }
        }
    }

    private void AnhXaChipToFilter(ChipGroup chipGroup){
        if(!incomeChipList.isEmpty())
            for (Chip chip : incomeChipList){
                Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.input_chips_layout, null, false);
                newChip.setText(chip.getText());
                newChip.setChipStrokeColor(getColorStateList(R.color.black));
                newChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhdam, null)));
                newChip.setTextColor(getResources().getColor(R.color.white, null));
                newChip.setCloseIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
                newChip.setTag(chip.getTag());
                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chipGroup.removeView(newChip);
                        incomeChipList.remove(chip);
                        reloadExpandableListView();
                    }
                });
                chipGroup.addView(newChip);
            }
        if(!expenseChipList.isEmpty()){
            for (Chip chip : expenseChipList){
                Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.input_chips_layout, null, false);
                newChip.setText(chip.getText());
                newChip.setChipStrokeColor(getColorStateList(R.color.black));
                newChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.red, null)));
                newChip.setTextColor(getResources().getColor(R.color.white, null));
                newChip.setCloseIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
                newChip.setTag(chip.getTag());
                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chipGroup.removeView(newChip);
                        expenseChipList.remove(chip);
                        reloadExpandableListView();
                    }
                });
                chipGroup.addView(newChip);
            }
        }
    }

    public String formatDate(String inputDate) {
        try {
            // Tạo một đối tượng SimpleDateFormat với định dạng ngày giờ đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Phân tích chuỗi ngày giờ đầu vào thành một đối tượng Date
            Date date = inputFormat.parse(inputDate);

            // Tạo một đối tượng SimpleDateFormat với định dạng ngày đầu ra
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Định dạng lại đối tượng Date thành chuỗi ngày và trả về
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String formatDate2(String inputDate) {
        DateTimeFormatter inputFormat;
        DateTimeFormatter outputFormat;

        // Kiểm tra định dạng ngày đầu vào
        if (inputDate.contains("/")) {
            String[] parts = inputDate.split("/");
            if (parts.length == 3) {
                // Định dạng ngày là "d/M/yyyy"
                inputFormat = DateTimeFormat.forPattern("d/M/yyyy");
                outputFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
            } else {
                // Định dạng ngày là "M/yyyy"
                inputFormat = DateTimeFormat.forPattern("M/yyyy");
                outputFormat = DateTimeFormat.forPattern("yyyy-MM");
            }
        } else {
            return null;
        }

        try {
            // Phân tích chuỗi ngày đầu vào thành một đối tượng DateTime
            org.joda.time.DateTime date = inputFormat.parseDateTime(inputDate);

            // Định dạng lại đối tượng DateTime thành chuỗi ngày và trả về
            return outputFormat.print(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CreateWalletEntity findWalletById(String id){
        try{
            for(Wallet_hdp wallet : walletList){
                if(wallet.getId().equals(id)){
                    CreateWalletEntity temp = new CreateWalletEntity(wallet.getWalletName(), Integer.parseInt(wallet.getAmountMoney()), wallet.getCurrency());
                    return temp;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<TransactionDetail_TheGiaoDich> filter(String date, String walletName, ArrayList<Chip> chipList) {
        List<TransactionDetail_TheGiaoDich> filteredList = new ArrayList<>(theGiaoDichList);

        // Filter by date
        if (!date.equals("All time")) {
            List<TransactionDetail_TheGiaoDich> list = new ArrayList<>();
            if (date.contains("-")) {
                // Date range
                String[] dates = date.split(" - ");
                for (TransactionDetail_TheGiaoDich item : filteredList) {
                    if (item.getNgayThang().compareTo(formatDate2(dates[0])) >= 0 && item.getNgayThang().compareTo(formatDate2(dates[1])) <= 0) {
                        list.add(item);
                    }
                }
                filteredList = list;
            } else if (date.contains("/")) {
                // Single date or month/year
                for (TransactionDetail_TheGiaoDich item : filteredList) {
                    if (item.getNgayThang().startsWith(formatDate2(date))) {
                        list.add(item);
                    }
                }
                filteredList = list;
            } else {
                // Year
                for (TransactionDetail_TheGiaoDich item : filteredList) {
                    if (item.getNgayThang().contains(date)) {
                        list.add(item);
                    }
                }
                filteredList = list;
            }
        }

        if(!walletName.equals("TOTAL")){
            // Filter by wallet name
            List<TransactionDetail_TheGiaoDich> list = new ArrayList<>();
            for (TransactionDetail_TheGiaoDich item : filteredList) {
                if(item.getViTien() != null){
                    if(item.getViDuocNhanTien() != null){
                        if(item.getViTien().name.equals(walletName) || item.getViDuocNhanTien().name.equals(walletName)){
                            list.add(item);
                        }
                    }
                    else if(item.getViTien().name.equals(walletName)){
                        list.add(item);
                    }
                }
            }
            filteredList = list;
        }

        if(!chipList.isEmpty()){
            // Filter by chip list
            List<CharSequence> chipNames = chipList.stream().map(chip -> ((Category_hdp) chip.getTag()).getCategory_name()).collect(Collectors.toList());
            List<CharSequence> chipTypes = chipList.stream().map(chip -> ((Category_hdp) chip.getTag()).getCategory_type()).collect(Collectors.toList());
            List<TransactionDetail_TheGiaoDich> list = new ArrayList<>();
            for (TransactionDetail_TheGiaoDich item : filteredList) {
                if (item.getDanhMuc() != null && chipNames.contains(item.getDanhMuc().name) && chipTypes.contains(item.getDanhMuc().type)) {
                    list.add(item);
                }
            }
            filteredList = list;
        }

        return filteredList;
    }

    private ArrayList<Chip> getChipList(){
        ArrayList<Chip> chipList = new ArrayList<>();
        chipList.addAll(incomeChipList);
        chipList.addAll(expenseChipList);
        return chipList;
    }

    public void reloadExpandableListView() {
        // Lấy thông tin cần thiết để lọc dữ liệu
        TextView walletName = findViewById(R.id.wallet_name_txtview);
        TextView date = findViewById(R.id.trans_detail_date_txtview);
        ArrayList<Chip> tempChipList = getChipList();
        String dateString = date.getText().toString();
        String walletNameString = walletName.getText().toString();

        // Lọc dữ liệu và thiết lập lại ExpandableListView
        List<TransactionDetail_TheGiaoDich> filteredList = filter(dateString, walletNameString, tempChipList);
        filterExpandableListView(filteredList);

        // Thiết lập lại adapter cho ExpandableListView
        expandableListView = findViewById(R.id.trans_detail_listview);
        expandableListAdapter = new TransactionDetail_ExpandableListView(TransactionDetail.this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

        // Mở rộng tất cả các nhóm trong ExpandableListView
        for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }
}