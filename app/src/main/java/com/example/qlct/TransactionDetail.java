package com.example.qlct;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionDetail extends AppCompatActivity {

    private TextView apply;
    private TextView date;
    private TextView day;
    private TextView month;
    private TextView year;
    private TextView week;
    private TextView timespan;
    private ImageView back;
    private ImageView next;
    private static String status;
    private LinearLayout time;
    private LinearLayout from_to;
    private TextView from,to;

    private ListView walletListView;

    private List<Wallet> walletList;

    private TextView dateTxtView;

    private void AnhXaWallet(){
        walletList = new ArrayList<Wallet>();
        walletList.add(new Wallet("Vi 1", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 2", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 3", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 4", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 5", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 6", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 7", "2000000 d", R.drawable.budget));
        walletList.add(new Wallet("Vi 8", "2000000 d", R.drawable.budget));
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

    private void setTransferBackground(MaterialButton transfer_btn){
        if (transfer){
            transfer_btn.setBackgroundColor(getResources().getColor(R.color.xanhnen));
            transfer_btn.setTextColor(getResources().getColor(R.color.white));
            transfer_btn.setIconTint(getColorStateList(R.color.white));
            transfer_btn.setRippleColor(getColorStateList(R.color.xanhnennhat));
        }else{
            transfer_btn.setBackgroundColor(getResources().getColor(R.color.white));
            transfer_btn.setTextColor(getResources().getColor(R.color.black));
            transfer_btn.setIconTint(getColorStateList(R.color.black));
            transfer_btn.setRippleColor(getColorStateList(R.color.xanhnen));
        }
    }
    private boolean income = true;
    private boolean expense = false;
    private boolean transfer = false;

    private void setChips(Chip chip, Context context){
        chip.setChipStrokeColor(getColorStateList(R.color.black));
        chip.setText("Food");

        if(chip.isChecked()){
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.xanhnen)));
            chip.setTextColor(getResources().getColor(R.color.white, null));
            chip.setCheckedIconTint(getColorStateList(R.color.white));
        }
        else{
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
            chip.setTextColor(getResources().getColor(R.color.black, null));
            chip.setCheckedIconTint(getColorStateList(R.color.black));
        }
    }
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

        MaterialButton income_btn = dialog.findViewById(R.id.income_button);
        income_btn.setBackgroundColor(getResources().getColor(R.color.xanhdam));
        income_btn.setTextColor(getResources().getColor(R.color.white));
        income_btn.setIconTint(getColorStateList(R.color.white));
        income_btn.setRippleColor(getColorStateList(R.color.xanhnhat));

        MaterialButton expense_btn = dialog.findViewById(R.id.expense_button);
        MaterialButton transfer_btn = dialog.findViewById(R.id.transfer_button);

        Chip chip1 = (Chip) LayoutInflater.from(dialog.getContext()).inflate(R.layout.chips_layout, null);
        chip1.setChipStrokeColor(getColorStateList(R.color.black));
        chip1.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
        chip1.setTextColor(getResources().getColor(R.color.black, null));
        chip1.setText("Food");
        chip1.setId(0);


        ChipGroup chipGroup = dialog.findViewById(R.id.trans_type_chipgroup);
        chipGroup.addView(chip1);

        chip1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chip1.isChecked()){
                    if(income){
                        chip1.setRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnhat, null)));
                        chip1.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhdam, null)));
                    }else if(expense){
                        chip1.setRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.lightred, null)));
                        chip1.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.red, null)));
                    }else if(transfer){
                        chip1.setRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnennhat, null)));
                        chip1.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnen, null)));
                    }
                    chip1.setCheckedIconVisible(true);
                    chip1.setTextColor(getResources().getColor(R.color.white, null));
                    chip1.setCheckedIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
                }else{
                    chip1.setCheckedIconVisible(false);
                    chip1.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.white, null)));
                    chip1.setTextColor(getResources().getColor(R.color.black, null));
                }
            }
        });

        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = true;
                expense = false;
                transfer = false;
                setIncomeBackground(income_btn);
                setExpenseBackground(expense_btn);
                setTransferBackground(transfer_btn);
            }
        });

        expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = false;
                expense = true;
                transfer = false;
                setIncomeBackground(income_btn);
                setExpenseBackground(expense_btn);
                setTransferBackground(transfer_btn);
            }
        });

        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = false;
                expense = false;
                transfer = true;
                setIncomeBackground(income_btn);
                setExpenseBackground(expense_btn);
                setTransferBackground(transfer_btn);
            }
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
                                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được nhỏ hơn hoặc bằng ngày bắt đầu", Toast.LENGTH_LONG).show();
                                        }
                                        else if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime())))
                                        {
                                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
                                        }
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

                        // Định dạng tháng mới và đặt nó vào TextView
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

                        // Định dạng năm mới và đặt nó vào TextView
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

                        // Định dạng ngày mới và đặt nó vào TextView
                        date.setText(format.format(calendarStart.getTime()) + " - " + format.format(calendarEnd.getTime()));

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

                        // Định dạng ngày mới và đặt nó vào TextView
                        if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime()))) {
                            Toast.makeText(dateDialog.getContext(), "Ngày không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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

                        // Giảm tháng đi 1
                        calendar.add(Calendar.MONTH, -1);
                        if(!isDateLessThanCurrent(format.format(calendar.getTime())))
                        {
                            Toast.makeText(dateDialog.getContext(), "Tháng không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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

                        // Giảm năm đi 1
                        calendar.add(Calendar.YEAR, -1);
                        if(!isYearLessThanCurrent(format.format(calendar.getTime())))
                        {
                            Toast.makeText(dateDialog.getContext(), "Năm không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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

                        // Giảm ngày đi 7 (1 tuần)
                        calendarStart.add(Calendar.DAY_OF_MONTH, -7);
                        calendarEnd.add(Calendar.DAY_OF_MONTH, -7);
                        if(isEndDateLessThanCurrent(". - "+format.format(calendarEnd.getTime()))) {
                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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
                }
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
                                            Toast.makeText(dateDialog.getContext(), "Ngày không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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
                                if(Integer.parseInt(year_lbl.getText().toString())>currentYear)
                                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                                else
                                    Toast.makeText(dialog.getContext(), "Năm không được nhỏ hơn "+currentYear, Toast.LENGTH_LONG).show();
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
                                        Toast.makeText(dialog.getContext(), "Tháng không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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
                                if(Integer.parseInt(year_lbl.getText().toString())>currentYear)
                                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                                else
                                    Toast.makeText(dialog.getContext(), "Năm không được nhỏ hơn "+currentYear, Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(dialog.getContext(), "Năm không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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
                                        if(isEndDateLessThanCurrent(getStartAndEndOfWeek(format.format(calendar.getTime())))==true)
                                        {
                                            Toast.makeText(dateDialog.getContext(), "Ngày kết thúc không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
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

        dateTxtView = findViewById(R.id.trans_detail_date_txtview);
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
            }
        });

        ImageButton morebutton = findViewById(R.id.trans_detail_more_option_btn);
        morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        ImageButton calenderIcon = findViewById(R.id.trans_detail_calendar_icon);
        calenderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
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
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date currentDate = calendar.getTime();
            // So sánh ngày thứ hai với ngày hiện tại
            return endDate.before(currentDate) ;
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
            if (inputYear < currentYear || (inputYear == currentYear && inputMonth < currentMonth)) {
                return false;
            } else {
                return true;
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
        if (inputYear < currentYear) {
            return false;
        } else {
            return true;
        }
    }
}