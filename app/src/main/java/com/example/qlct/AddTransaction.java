package com.example.qlct;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddTransaction extends AppCompatActivity {

    private ListView categoryListView;
    private List<Category> categoryList = new ArrayList<>();

    private List<Category> renderCategoryList;

    private String currency;

    private ListView walletListView;

    private List<Wallet> walletList;

    private TextView select_category_txtview;

    private TextView select_wallet_txtview;

    //Unfocus EditText khi click ra ngoai
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
    private void AnhXaCategory(){
        categoryList.add(new Category("Food", R.drawable.dish, 1));
        categoryList.add(new Category("Food", R.drawable.dish, 2));
        categoryList.add(new Category("Food", R.drawable.dish, 2));
        categoryList.add(new Category("Food", R.drawable.dish, 1));
        categoryList.add(new Category("Food", R.drawable.dish, 2));
        categoryList.add(new Category("Food", R.drawable.dish, 1));
        categoryList.add(new Category("Food", R.drawable.dish, 1));
    }

    private void RenderCategoryList(){
        if(!categoryList.isEmpty()){
            if(income){
                renderCategoryList = new ArrayList<Category>();
                for (Category category : categoryList){
                    if(category.getCategory_type() == 1){
                        renderCategoryList.add(category);
                    }
                }
            }
            else if(expense){
                renderCategoryList = new ArrayList<Category>();
                for (Category category : categoryList){
                    if(category.getCategory_type() == 2){
                        renderCategoryList.add(category);
                    }
                }
            } else if (transfer) {

            }
        }
    }

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

    private void showCategoryDialog(){
        final Dialog dialog = new Dialog(AddTransaction.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_select_category);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);
        dialog.show();

        categoryListView = dialog.findViewById(R.id.select_category_listview);
        AnhXaCategory();
        RenderCategoryList();
        Category_adapter categoryAdapter = new Category_adapter(dialog.getContext(), R.layout.category_list_item, renderCategoryList);
        categoryListView.setAdapter(categoryAdapter);

        TextView addnew = dialog.findViewById(R.id.select_category_addnew_btn);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddCategoryIntent = new Intent(dialog.getContext(), Category_Add.class);
                startActivity(AddCategoryIntent);
            }
        });
    }

    private void showWalletDialog(){
        final Dialog dialog = new Dialog(AddTransaction.this);

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
        SelectWallet_Adapter adapter = new SelectWallet_Adapter(AddTransaction.this, R.layout.select_wallet_item_list, walletList);
        walletListView.setAdapter(adapter);
    }

    private void showCurrencyDialog(){
        final Dialog dialog = new Dialog(AddTransaction.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_currency);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);
        dialog.show();

        LinearLayout bo1 = dialog.findViewById(R.id.bo1);
        LinearLayout bo2 = dialog.findViewById(R.id.bo2);
        LinearLayout bo3 = dialog.findViewById(R.id.bo3);
        LinearLayout bo4 = dialog.findViewById(R.id.bo4);
        Button txt1 = findViewById(R.id.add_trans_currency_btn);
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
                Button btn = findViewById(R.id.add_trans_currency_btn);
                btn.setText(currency);
                TextInputLayout amount = findViewById(R.id.Amount_txtbox_layout);
                switch (currency){
                    case "USD":
                        amount.setPrefixText("$");
                        break;
                    case "VND":
                        amount.setPrefixText("₫");
                        break;
                    case "EUR":
                        amount.setPrefixText("€");
                        break;
                    case "CNY":
                        amount.setPrefixText("¥");
                        break;
                }
                dialog.dismiss();
            }
        });

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_transaction), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton close_btn = findViewById(R.id.close_button);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        MaterialButton income_btn = findViewById(R.id.income_button);
        MaterialButton expense_btn = findViewById(R.id.expense_button);
        MaterialButton transfer_btn = findViewById(R.id.transfer_button);

        income_btn.setBackgroundColor(getResources().getColor(R.color.xanhdam, null));
        income_btn.setTextColor(getResources().getColor(R.color.white, null));
        income_btn.setIconTint(getColorStateList(R.color.white));
        income_btn.setRippleColor(getColorStateList(R.color.xanhnhat));

        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                income = true;
                expense = false;
                transfer = false;
                setIncomeBackground(income_btn);
                setExpenseBackground(expense_btn);
                setTransferBackground(transfer_btn);
                RenderCategoryList();

                select_category_txtview = findViewById(R.id.select_category_txtview);
                select_category_txtview.setText("Select Category");
                select_wallet_txtview = findViewById(R.id.select_wallet_txtview);
                select_wallet_txtview.setText("Select Wallet");


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
                RenderCategoryList();

                select_category_txtview = findViewById(R.id.select_category_txtview);
                select_category_txtview.setText("Select Category");
                select_wallet_txtview = findViewById(R.id.select_wallet_txtview);
                select_wallet_txtview.setText("Select Wallet");


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
                RenderCategoryList();

                select_category_txtview = findViewById(R.id.select_category_txtview);
                select_category_txtview.setText("From Wallet");
                select_wallet_txtview = findViewById(R.id.select_wallet_txtview);
                select_wallet_txtview.setText("To Wallet");
            }
        });

        TextView selectCategory = findViewById(R.id.add_trans_select_category);
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transfer){
                    showWalletDialog();
                }
                else{
                    showCategoryDialog();
                }
            }
        });

        TextView selectWallet = findViewById(R.id.add_trans_select_wallet);
        selectWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletDialog();
            }
        });

        Button currencyBtn = findViewById(R.id.add_trans_currency_btn);
        currencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyDialog();
            }
        });

        ImageView calendarIcon = findViewById(R.id.add_trans_calendar_icon);
        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy ngày hiện tại làm ngày mặc định cho DatePickerDialog
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Tạo và hiển thị DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTransaction.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Người dùng đã chọn ngày, cập nhật select_date_txtbox
                                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                TextInputEditText dateTextBox = findViewById(R.id.select_date_txtbox);
                                dateTextBox.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        ImageView remindIcon = findViewById(R.id.remind_btn);
        remindIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thời gian hiện tại làm thời gian mặc định cho TimePickerDialog
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Tạo và hiển thị TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTransaction.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Người dùng đã chọn thời gian, cập nhật select_date_txtbox
                                String selectedTime = hourOfDay + ":" + minute;
                                TextView timeTextBox = findViewById(R.id.remind_txt);
                                timeTextBox.setText(selectedTime);
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }
}