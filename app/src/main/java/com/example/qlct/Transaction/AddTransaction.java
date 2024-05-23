package com.example.qlct.Transaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qlct.API_Entity.CreateTransactionEntity;
import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Entity.UpdateWalletEntity;
import com.example.qlct.API_Utils.CategoryAPIUtil;
import com.example.qlct.API_Utils.TransactionAPIUtil;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.Category.Category_hdp;
import com.example.qlct.Category.Category_Add;
import com.example.qlct.Category.Category_adapter;
import com.example.qlct.Home.Home_New_wallet;
import com.example.qlct.R;
import com.example.qlct.SelectWallet_Adapter;
import com.example.qlct.Wallet_hdp;
import com.example.qlct.doitiente;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddTransaction extends AppCompatActivity {

    private String url=null;

    private Button uploadBtn;

    private ImageView transactionImage;

    ActivityResultLauncher<Intent> resultLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    private boolean isUserInteracting = true;
    private ListView categoryListView;
    private List<Category_hdp> categoryList = new ArrayList<>();

    private List<Category_hdp> renderCategoryList;

    private String currency;

    private ListView walletListView;

    private ArrayList<Wallet_hdp> walletList;

    private TextView select_category_txtview;

    private TextView select_wallet_txtview;

    private String fromWalletIdStorage = "";
    private String targetWalletIdStorage = "";

    private String incomeWalletIdStorage = "";

    private String incomeCategoryStorage = "";

    private String expenseWalletIdStorage = "";

    private String expenseCategoryStorage = "";

    private String expenseWalletAmount = "";

    private String expenseWalletCurrency = "";

    private String fromWalletAmount = "";

    private String fromWalletCurrency = "";

    private ArrayList<Wallet_hdp> fromWalletList;

    private ArrayList<Wallet_hdp> targetWalletList;

    private boolean from_to_flag = true; // true: from, false: to
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
        categoryList = new ArrayList<Category_hdp>();
        ArrayList<GetAllCategoryEntity> parseAPIList = new CategoryAPIUtil().getAllCategory();
        for(GetAllCategoryEntity category : parseAPIList){
            categoryList.add(new Category_hdp(category.getId(), category.getName(), category.getPicture(), category.getType()));
        }
        Log.d("Get_wallet_data_object", categoryList.toString());
    }

    private void RenderCategoryList(){
        if(!categoryList.isEmpty()){
            if(income){
                renderCategoryList = new ArrayList<Category_hdp>();
                for (Category_hdp category : categoryList){
                    if(category.getCategory_type().equals("INCOME")){
                        renderCategoryList.add(category);
                    }
                }
            }
            else if(expense){
                renderCategoryList = new ArrayList<Category_hdp>();
                for (Category_hdp category : categoryList){
                    if(category.getCategory_type().equals("EXPENSE")){
                        renderCategoryList.add(category);
                    }
                }
            }
        }
    }

    private void AnhXaWallet(){
        try{
            walletList = new ArrayList<Wallet_hdp>();
            ArrayList<GetAllWalletsEntity> parseAPIList = new WalletAPIUtil().getAllWalletAPI();
            //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
            for (GetAllWalletsEntity item : parseAPIList) {
                walletList.add(new Wallet_hdp(item.id, item.name, item.amount, R.drawable.wallet, item.currency_unit));
            }
            Log.d("Get_wallet_data_object", walletList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<Wallet_hdp> setupFromToWallet(String walletId){
        ArrayList<Wallet_hdp> fromToWallet = new ArrayList<Wallet_hdp>();
        for(Wallet_hdp wallet : walletList){
            if(!wallet.getId().equals(walletId)){
                fromToWallet.add(wallet);
            }
        }
        return fromToWallet;
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

        categoryListView.setOnItemClickListener((parent, view, position, id) -> {
            Category_hdp category = renderCategoryList.get(position);
            if(income){
                TextView categoryTxtView = findViewById(R.id.income_category_txtview);
                categoryTxtView.setText(category.getCategory_name());
                ImageView categoryIcon = findViewById(R.id.income_category_icon);
                Glide.with(dialog.getContext()).load(category.getImageURL()).into(categoryIcon);
                incomeCategoryStorage = category.getCategory_id();
            }
            else if(expense){
                TextView categoryTxtView = findViewById(R.id.expense_category_txtview);
                categoryTxtView.setText(category.getCategory_name());
                ImageView categoryIcon = findViewById(R.id.expense_category_icon);
                Glide.with(dialog.getContext()).load(category.getImageURL()).into(categoryIcon);
                expenseCategoryStorage = category.getCategory_id();
            }
            dialog.dismiss();
            //Toast.makeText(AddTransaction.this, category.getImageURL(), Toast.LENGTH_SHORT).show();
        });

        TextView addnew = dialog.findViewById(R.id.select_category_addnew_btn);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddCategoryIntent = new Intent(dialog.getContext(), Category_Add.class);
                startActivity(AddCategoryIntent);
                dialog.dismiss();
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
        if(transfer){
            TextView fromWalletAmount = findViewById(R.id.from_wallet_amount_txtview);
            TextView targetWalletName = findViewById(R.id.target_wallet_amount_txtview);
            if(from_to_flag){
                if(targetWalletName.getText().toString().equals("")){
                    AnhXaWallet();
                    SelectWallet_Adapter adapter = new SelectWallet_Adapter(AddTransaction.this, R.layout.select_wallet_item_list, walletList);
                    walletListView.setAdapter(adapter);
                }
                else{
                    fromWalletList = setupFromToWallet(targetWalletIdStorage);
                    SelectWallet_Adapter adapter = new SelectWallet_Adapter(AddTransaction.this, R.layout.select_wallet_item_list, fromWalletList);
                    walletListView.setAdapter(adapter);
                }
            }
            else{
                if(fromWalletAmount.getText().toString().equals("")){
                    AnhXaWallet();
                    SelectWallet_Adapter adapter = new SelectWallet_Adapter(AddTransaction.this, R.layout.select_wallet_item_list, walletList);
                    walletListView.setAdapter(adapter);
                }
                else{
                    targetWalletList = setupFromToWallet(fromWalletIdStorage);
                    SelectWallet_Adapter adapter = new SelectWallet_Adapter(AddTransaction.this, R.layout.select_wallet_item_list, targetWalletList);
                    walletListView.setAdapter(adapter);
                }
            }
        }
        else{
            AnhXaWallet();
            SelectWallet_Adapter adapter = new SelectWallet_Adapter(AddTransaction.this, R.layout.select_wallet_item_list, walletList);
            walletListView.setAdapter(adapter);
        }


        walletListView.setOnItemClickListener((parent, view, position, id) -> {
            Wallet_hdp wallet = new Wallet_hdp("", "", "", 0, "");
            if(transfer){
                if(from_to_flag){
                    if(fromWalletList != null){
                        wallet = fromWalletList.get(position);
                    }
                    else{
                        wallet = walletList.get(position);
                    }
                }
                else{
                    if(targetWalletList != null){
                        wallet = targetWalletList.get(position);
                    }
                }
            }
            else{
                wallet = walletList.get(position);
            }
            if(income){
                ImageView walletIcon = findViewById(R.id.income_wallet_icon);
                walletIcon.setImageResource(wallet.getImage());
                TextView walletTxtView = findViewById(R.id.income_wallet_name_txtview);
                walletTxtView.setText(wallet.getWalletName());
                TextView walletMoney = findViewById(R.id.income_amount_txtview);
                String currencySymbol = "";
                switch (wallet.getCurrency()){
                    case "USD":
                        currencySymbol = "$";
                        break;
                    case "VND":
                        currencySymbol = "₫";
                        break;
                    case "EUR":
                        currencySymbol = "€";
                        break;
                    case "CNY":
                        currencySymbol = "¥";
                        break;
                }
                String amount = formatCurrency(Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()) + " " + currencySymbol;
                walletMoney.setTextColor(getResources().getColor(R.color.xanhdam, null));
                walletMoney.setText(amount);
                incomeWalletIdStorage = wallet.getId();
            }
            else if(expense){
                ImageView walletIcon = findViewById(R.id.expense_wallet_icon);
                walletIcon.setImageResource(wallet.getImage());
                TextView walletTxtView = findViewById(R.id.expense_wallet_name_txtview);
                walletTxtView.setText(wallet.getWalletName());
                TextView walletMoney = findViewById(R.id.expense_amount_txtview);
                String currencySymbol = "";
                switch (wallet.getCurrency()){
                    case "USD":
                        currencySymbol = "$";
                        break;
                    case "VND":
                        currencySymbol = "₫";
                        break;
                    case "EUR":
                        currencySymbol = "€";
                        break;
                    case "CNY":
                        currencySymbol = "¥";
                        break;
                }
                String amount = formatCurrency(Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()) + " " + currencySymbol;
                walletMoney.setTextColor(getResources().getColor(R.color.xanhdam, null));
                walletMoney.setText(amount);
                expenseWalletIdStorage = wallet.getId();
                expenseWalletAmount = wallet.getAmountMoney();
                expenseWalletCurrency = wallet.getCurrency();
            }
            else if (transfer) {
                if(from_to_flag){
                    ImageView walletIcon = findViewById(R.id.from_wallet_icon);
                    walletIcon.setImageResource(wallet.getImage());
                    TextView walletTxtView = findViewById(R.id.from_wallet_txtview);
                    walletTxtView.setText(wallet.getWalletName());
                    TextView walletMoney = findViewById(R.id.from_wallet_amount_txtview);
                    String currencySymbol = "";
                    switch (wallet.getCurrency()){
                        case "USD":
                            currencySymbol = "$";
                            break;
                        case "VND":
                            currencySymbol = "₫";
                            break;
                        case "EUR":
                            currencySymbol = "€";
                            break;
                        case "CNY":
                            currencySymbol = "¥";
                            break;
                    }
                    String amount = formatCurrency(Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()) + " " + currencySymbol;
                    walletMoney.setText(amount);
                    walletMoney.setTextColor(getResources().getColor(R.color.xanhdam, null));
                    fromWalletIdStorage = wallet.getId();
                    fromWalletAmount = wallet.getAmountMoney();
                    fromWalletCurrency = wallet.getCurrency();
                }
                else{
                    ImageView walletIcon = findViewById(R.id.target_wallet_icon);
                    walletIcon.setImageResource(wallet.getImage());
                    TextView walletTxtView = findViewById(R.id.target_wallet_name_txtview);
                    walletTxtView.setText(wallet.getWalletName());
                    TextView walletMoney = findViewById(R.id.target_wallet_amount_txtview);
                    String currencySymbol = "";
                    switch (wallet.getCurrency()){
                        case "USD":
                            currencySymbol = "$";
                            break;
                        case "VND":
                            currencySymbol = "₫";
                            break;
                        case "EUR":
                            currencySymbol = "€";
                            break;
                        case "CNY":
                            currencySymbol = "¥";
                            break;
                    }
                    String amount = formatCurrency(Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()) + " " + currencySymbol;
                    walletMoney.setText(amount);
                    walletMoney.setTextColor(getResources().getColor(R.color.xanhdam, null));
                    targetWalletIdStorage = wallet.getId();
                }
            }
            dialog.dismiss();
        });

        TextView addNewWallet = dialog.findViewById(R.id.select_wallet_addnew_btn);
        addNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddWalletIntent = new Intent(dialog.getContext(), Home_New_wallet.class);
                startActivity(AddWalletIntent);
                dialog.dismiss();
            }
        });
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
                validateAmount();
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

        LinearLayout income_category_layout = findViewById(R.id.income_category_layout);
        income_category_layout.setVisibility(View.VISIBLE);
        LinearLayout expense_category_layout = findViewById(R.id.expense_category_layout);
        expense_category_layout.setVisibility(View.GONE);
        LinearLayout transfer_category_layout = findViewById(R.id.from_wallet_layout);
        transfer_category_layout.setVisibility(View.GONE);

        LinearLayout income_wallet_layout = findViewById(R.id.income_wallet_layout);
        income_wallet_layout.setVisibility(View.VISIBLE);
        LinearLayout expense_wallet_layout = findViewById(R.id.expense_wallet_layout);
        expense_wallet_layout.setVisibility(View.GONE);
        LinearLayout transfer_wallet_layout = findViewById(R.id.target_wallet_layout);
        transfer_wallet_layout.setVisibility(View.GONE);

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

                LinearLayout income_category_layout = findViewById(R.id.income_category_layout);
                income_category_layout.setVisibility(View.VISIBLE);
                LinearLayout expense_category_layout = findViewById(R.id.expense_category_layout);
                expense_category_layout.setVisibility(View.GONE);
                LinearLayout transfer_category_layout = findViewById(R.id.from_wallet_layout);
                transfer_category_layout.setVisibility(View.GONE);

                LinearLayout income_wallet_layout = findViewById(R.id.income_wallet_layout);
                income_wallet_layout.setVisibility(View.VISIBLE);
                LinearLayout expense_wallet_layout = findViewById(R.id.expense_wallet_layout);
                expense_wallet_layout.setVisibility(View.GONE);
                LinearLayout transfer_wallet_layout = findViewById(R.id.target_wallet_layout);
                transfer_wallet_layout.setVisibility(View.GONE);
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

                LinearLayout income_category_layout = findViewById(R.id.income_category_layout);
                income_category_layout.setVisibility(View.GONE);
                LinearLayout expense_category_layout = findViewById(R.id.expense_category_layout);
                expense_category_layout.setVisibility(View.VISIBLE);
                LinearLayout transfer_category_layout = findViewById(R.id.from_wallet_layout);
                transfer_category_layout.setVisibility(View.GONE);

                LinearLayout income_wallet_layout = findViewById(R.id.income_wallet_layout);
                income_wallet_layout.setVisibility(View.GONE);
                LinearLayout expense_wallet_layout = findViewById(R.id.expense_wallet_layout);
                expense_wallet_layout.setVisibility(View.VISIBLE);
                LinearLayout transfer_wallet_layout = findViewById(R.id.target_wallet_layout);
                transfer_wallet_layout.setVisibility(View.GONE);
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

                LinearLayout income_category_layout = findViewById(R.id.income_category_layout);
                income_category_layout.setVisibility(View.GONE);
                LinearLayout expense_category_layout = findViewById(R.id.expense_category_layout);
                expense_category_layout.setVisibility(View.GONE);
                LinearLayout transfer_category_layout = findViewById(R.id.from_wallet_layout);
                transfer_category_layout.setVisibility(View.VISIBLE);

                LinearLayout income_wallet_layout = findViewById(R.id.income_wallet_layout);
                income_wallet_layout.setVisibility(View.GONE);
                LinearLayout expense_wallet_layout = findViewById(R.id.expense_wallet_layout);
                expense_wallet_layout.setVisibility(View.GONE);
                LinearLayout transfer_wallet_layout = findViewById(R.id.target_wallet_layout);
                transfer_wallet_layout.setVisibility(View.VISIBLE);
            }
        });

        TextView selectIncomeCategory = findViewById(R.id.add_trans_select_income_category);
        selectIncomeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog();
            }
        });

        TextView selectExpenseCategory = findViewById(R.id.add_trans_select_expense_category);
        selectExpenseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog();
            }
        });

        TextView selectFromWallet = findViewById(R.id.add_trans_select_from_wallet);
        selectFromWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from_to_flag = true;
                showWalletDialog();
            }
        });

        TextView selectIncomeWallet = findViewById(R.id.add_trans_select_income_wallet);
        selectIncomeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletDialog();
            }
        });

        TextView selectExpenseWallet = findViewById(R.id.add_trans_select_expense_wallet);
        selectExpenseWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletDialog();
            }
        });

        TextView selectTargetWallet = findViewById(R.id.add_trans_select_target_wallet);
        selectTargetWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from_to_flag = false;
                TextView fromWalletAmount = findViewById(R.id.from_wallet_amount_txtview);
                if(fromWalletAmount.getText().toString().equals("")){
                    Toast.makeText(AddTransaction.this, "Please select from wallet first", Toast.LENGTH_SHORT).show();
                }
                else{
                    showWalletDialog();
                }
            }
        });

        Button currencyBtn = findViewById(R.id.add_trans_currency_btn);
        currencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrencyDialog();
            }
        });

        TextInputEditText dateTextBox = findViewById(R.id.select_date_txtbox);

        dateTextBox.setOnClickListener(new View.OnClickListener() {
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
                                dateTextBox.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        try{
            dateTextBox.addTextChangedListener(new TextWatcher() {
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
                    validateDate();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        TextView saveButton = findViewById(R.id.save_button);

        try {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
                    TextInputEditText dateEditText = findViewById(R.id.select_date_txtbox);
                    TextInputEditText noteEditText = findViewById(R.id.note_txtbox);
                    String amount = amountEditText.getText().toString().replaceAll("[.,]", "");
                    String currencies = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
                    ImageView transactionImage = findViewById(R.id.transaction_image);

                    if(!validate()){
                        Toast.makeText(AddTransaction.this, "An error(s) has occurred!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Tạo một đối tượng SimpleDateFormat để parse chuỗi ngày ban đầu
                        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = originalFormat.parse(dateEditText.getText().toString());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        // Tạo một đối tượng SimpleDateFormat mới để định dạng ngày đã parse
                        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = targetFormat.format(date);

                        String note = noteEditText.getText().toString();
                        TransactionAPIUtil transactionAPIUtil = new TransactionAPIUtil();
                        if(income){
                            CreateTransactionEntity createTransactionEntity = new CreateTransactionEntity(Double.parseDouble(amount), formattedDate, incomeCategoryStorage, incomeWalletIdStorage, note, url, "INCOME", currencies, null);
                            transactionAPIUtil.createTransactionAPI(createTransactionEntity);
                            excuteIncome(incomeWalletIdStorage);
                        }
                        else if(expense){
                            CreateTransactionEntity createTransactionEntity = new CreateTransactionEntity(Double.parseDouble(amount), formattedDate, expenseCategoryStorage, expenseWalletIdStorage, note, url, "EXPENSE", currencies, null);
                            transactionAPIUtil.createTransactionAPI(createTransactionEntity);
                            excuteExpense(expenseWalletIdStorage);
                        }
                        else {
                            CreateTransactionEntity createTransactionEntity = new CreateTransactionEntity(Double.parseDouble(amount), formattedDate, null, fromWalletIdStorage, note, url, "TRANSFER", currencies, targetWalletIdStorage);
                            transactionAPIUtil.createTransactionAPI(createTransactionEntity);
                            excuteTransfer(fromWalletIdStorage, targetWalletIdStorage);
                        }
                        Toast.makeText(AddTransaction.this, url, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        // format currency trên amount edittext
        try{
            TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
            amountEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isUserInteracting && !s.toString().isEmpty()) {
                        isUserInteracting = false; // Set to false before changing text programmatically
                        try {
                            // Remove thousands separators before parsing
                            String cleanString = s.toString().replaceAll("[.,]", "");
                            double amount = Double.parseDouble(cleanString);
                            String currency = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
                            String formattedAmount = formatCurrency(amount, currency);
                            amountEditText.setText(formattedAmount);
                            amountEditText.setSelection(formattedAmount.length()); // Move cursor to the end
                        } catch (Exception e) {
                            // Handle or log the error
                            e.printStackTrace();
                        }
                        validateAmount();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    isUserInteracting = true; // Set back to true after text change is done
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //Disable strict mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //review = findViewById(R.id.transaction_image);
        registerResult();
        transactionImage = findViewById(R.id.transaction_image);
        transactionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                resultLauncher.launch(intent);
            }
        });
    }

    private void excuteIncome(String incomeID){
        doitiente doi_tien_te = new doitiente();
        for(Wallet_hdp wallet : walletList){
            if(wallet.getId().equals(incomeID)){
                String currencies = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
                TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
                double vndAmount = doi_tien_te.converttoVND(wallet.getCurrency(), Double.parseDouble(wallet.getAmountMoney())) + doi_tien_te.converttoVND(currencies, Double.parseDouble(amountEditText.getText().toString().replaceAll("[.,]", "")));
                switch (wallet.getCurrency()){
                    case "USD":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUSDtoVND()));
                        break;
                    case "VND":
                        wallet.setAmountMoney(String.valueOf(vndAmount));
                        break;
                    case "EUR":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUERtoVND()));
                        break;
                    case "CNY":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getCNYtoVND()));
                        break;
                }
                WalletAPIUtil walletAPIUtil = new WalletAPIUtil();
                walletAPIUtil.updateWalletAPI(incomeID, new UpdateWalletEntity(wallet.getWalletName(), Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()));
            }
        }
    }

    private void excuteExpense(String expenseID){
        doitiente doi_tien_te = new doitiente();
        for(Wallet_hdp wallet : walletList){
            if(wallet.getId().equals(expenseID)){
                String currencies = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
                TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
                double vndAmount = doi_tien_te.converttoVND(wallet.getCurrency(), Double.parseDouble(wallet.getAmountMoney())) - doi_tien_te.converttoVND(currencies, Double.parseDouble(amountEditText.getText().toString().replaceAll("[.,]", "")));
                switch (wallet.getCurrency()){
                    case "USD":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUSDtoVND()));
                        break;
                    case "VND":
                        wallet.setAmountMoney(String.valueOf(vndAmount));
                        break;
                    case "EUR":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUERtoVND()));
                        break;
                    case "CNY":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getCNYtoVND()));
                        break;
                }
                WalletAPIUtil walletAPIUtil = new WalletAPIUtil();
                walletAPIUtil.updateWalletAPI(expenseID, new UpdateWalletEntity(wallet.getWalletName(), Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()));
            }
        }
    }

    private void excuteTransfer(String fromID, String targetID){
        doitiente doi_tien_te = new doitiente();
        for(Wallet_hdp wallet : walletList){
            if(wallet.getId().equals(fromID)){
                String currencies = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
                TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
                double vndAmount = doi_tien_te.converttoVND(wallet.getCurrency(), Double.parseDouble(wallet.getAmountMoney())) - doi_tien_te.converttoVND(currencies, Double.parseDouble(amountEditText.getText().toString().replaceAll("[.,]", "")));
                switch (wallet.getCurrency()){
                    case "USD":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUSDtoVND()));
                        break;
                    case "VND":
                        wallet.setAmountMoney(String.valueOf(vndAmount));
                        break;
                    case "EUR":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUERtoVND()));
                        break;
                    case "CNY":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getCNYtoVND()));
                        break;
                }
                WalletAPIUtil walletAPIUtil = new WalletAPIUtil();
                walletAPIUtil.updateWalletAPI(fromID, new UpdateWalletEntity(wallet.getWalletName(), Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()));
            }
            if(wallet.getId().equals(targetID)){
                String currencies = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
                TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
                double vndAmount = doi_tien_te.converttoVND(wallet.getCurrency(), Double.parseDouble(wallet.getAmountMoney())) + doi_tien_te.converttoVND(currencies, Double.parseDouble(amountEditText.getText().toString().replaceAll("[.,]", "")));
                switch (wallet.getCurrency()){
                    case "USD":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUSDtoVND()));
                        break;
                    case "VND":
                        wallet.setAmountMoney(String.valueOf(vndAmount));
                        break;
                    case "EUR":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getUERtoVND()));
                        break;
                    case "CNY":
                        wallet.setAmountMoney(String.valueOf(vndAmount / doi_tien_te.getCNYtoVND()));
                        break;
                }
                WalletAPIUtil walletAPIUtil = new WalletAPIUtil();
                walletAPIUtil.updateWalletAPI(targetID, new UpdateWalletEntity(wallet.getWalletName(), Double.parseDouble(wallet.getAmountMoney()), wallet.getCurrency()));
            }
        }
    }

    public static String formatCurrency(double amount, String currency) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        // Set the thousands separator depending on the currency
        if ("VND".equals(currency)) {
            symbols.setGroupingSeparator('.');
        } else {
            symbols.setGroupingSeparator(',');
        }

        // Always use a dot for the decimal separator
        symbols.setDecimalSeparator('.');

        // Create a DecimalFormat with the desired symbols and format the amount
        DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);
        return formatter.format(amount);
    }

    private boolean validateAmount() {
        String currencies = ((MaterialButton) findViewById(R.id.add_trans_currency_btn)).getText().toString();
        TextInputLayout amountEditTextLayout = findViewById(R.id.Amount_txtbox_layout);
        TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);
        String amountInput = amountEditText.getText().toString().replaceAll("[.,]", "").trim();


        if(!amountInput.isEmpty()){
            doitiente doi_tien_te = new doitiente();
            if (Double.parseDouble(amountInput) < 1) { // Check if the input is a valid number
                amountEditTextLayout.setError("Please enter a positive amount!");
                amountEditText.setTextColor(getResources().getColor(R.color.errorColor, null));
                amountEditTextLayout.setPrefixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
                return false;
            }
            if(expense) {
                if(!expenseWalletAmount.equals("") && (doi_tien_te.converttoVND(currencies, Double.parseDouble(amountInput)) > doi_tien_te.converttoVND(expenseWalletCurrency, Double.parseDouble(expenseWalletAmount)))){
                    amountEditTextLayout.setError("Expense amount can't be greater than wallet amount!");
                    amountEditText.setTextColor(getResources().getColor(R.color.errorColor, null));
                    amountEditTextLayout.setPrefixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
                    return false;
                }
            }
            else if(transfer) {
                if(!fromWalletAmount.equals("") && (doi_tien_te.converttoVND(currencies, Double.parseDouble(amountInput)) > doi_tien_te.converttoVND(fromWalletCurrency, Double.parseDouble(fromWalletAmount)))){
                    amountEditTextLayout.setError("Transfer amount can't be greater than from wallet amount!");
                    amountEditText.setTextColor(getResources().getColor(R.color.errorColor, null));
                    amountEditTextLayout.setPrefixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
                    return false;
                }
            }
            amountEditTextLayout.setError(null);
            amountEditText.setTextColor(getResources().getColor(R.color.xanhnen, null));
            amountEditTextLayout.setPrefixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnen, null)));
            return true;
        }
        else{
            amountEditTextLayout.setError("Amount can't be empty!");
            return false;
        }
    }

    private boolean validateDate() {
        TextInputLayout dateEditTextLayout = findViewById(R.id.select_date_txtbox_layout);
        TextInputEditText dateEditText = findViewById(R.id.select_date_txtbox);
        String dateInput = dateEditText.getText().toString().trim();

        if (!dateInput.isEmpty()) {
            if(LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("d/M/yyyy")).isAfter(LocalDate.now())){
                dateEditTextLayout.setError("Date can't be in the future!");
                dateEditText.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
                dateEditTextLayout.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
                return false;
            }
            dateEditTextLayout.setError(null);
            dateEditTextLayout.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.black, null)));
            dateEditText.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black, null)));
            return true;
        }
        else{
            dateEditTextLayout.setError("Please press calendar icon to select a date!");
            dateEditTextLayout.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
            return false;
        }
    }

    private boolean validate(){
        boolean isListValid = true;
        if(income){
            TextView incomeWalletAmount = findViewById(R.id.income_amount_txtview);
            if(incomeWalletAmount.getText().toString().equals("")) {
                isListValid = false;
            }
        }
        else if(expense){
            TextView expenseWalletAmount = findViewById(R.id.expense_amount_txtview);
            if(expenseWalletAmount.getText().toString().equals("")) {
                isListValid = false;
            }
        }
        else{
            TextView fromWalletAmount = findViewById(R.id.from_wallet_amount_txtview);
            TextView targetWalletAmount = findViewById(R.id.target_wallet_amount_txtview);
            if(fromWalletAmount.getText().toString().equals("") || targetWalletAmount.getText().toString().equals("")) {
                isListValid = false;
            }
        }
        boolean isAmountValid = validateAmount();
        boolean isDateValid = validateDate();
        return isAmountValid && isDateValid && isListValid;
    }

    //API chính để upload ảnh
    private String uploadImageAPI(Uri imageUri) throws IOException, JSONException {

        //Đường dẫn của server, cái này trong source chính đã để trong folder API_CONFIG
        String SERVER = "http://13.215.179.128";
        String API_VERSION = "api/v1";

        //Dưới nãy giữ y chang, không cần suy nghĩ
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        File file = getFileFromUri(this, imageUri);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create( file.getAbsoluteFile(),
                                MediaType.parse("application/octet-stream")
                        ))
                .build();
        Request request = new Request.Builder()
                .url(SERVER+"/" + API_VERSION + "/media/upload")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();

        //Log.d("Response", response.body().string());
        return  response.body().string();
    }

    private void registerResult (){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            File test = new File(imageUri.getPath());
                            transactionImage.setImageURI(imageUri);
                            File file = new File(imageUri.getPath());

                            //Đây là url của ảnh sau khi upload lên server
                            //Sau khi có url này, thực hiện chèn vào các field API nào mà có "image"
                            String response = uploadImageAPI(imageUri);
                            Log.d("Response", response);
                            JSONObject json = new JSONObject(response);
                            String imageUrl = json.getJSONObject("data").getString("picture_url");

                            //Log ra để xem url của ảnh
                            Log.d("Data", imageUrl);
                            url = imageUrl;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private File getFileFromUri(Context context, Uri uri) {
        File file = null;
        try {
            String fileName = getFileName(context, uri);
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                file = new File(context.getCacheDir(), fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}