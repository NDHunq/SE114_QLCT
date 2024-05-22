package com.example.qlct.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.qlct.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ViewTransaction extends AppCompatActivity {

    private TransactionDetail_TheGiaoDich clickedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_transaction), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();

        // Lấy thông tin của mục con từ Intent
        clickedItem = (TransactionDetail_TheGiaoDich) intent.getSerializableExtra("clickedItem");


        // Hiển thị thông tin của mục con
        if(clickedItem != null){
            Toast.makeText(this, clickedItem.getLoaiGiaoDich(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        MaterialButton incomeButton = findViewById(R.id.income_button);
        MaterialButton expenseButton = findViewById(R.id.expense_button);
        MaterialButton transferButton = findViewById(R.id.transfer_button);

        TextView categoryTextView = findViewById(R.id.select_category_txtview);
        TextView walletTextView = findViewById(R.id.select_wallet_txtview);

        LinearLayout categoryLayout = findViewById(R.id.expense_category_layout);
        LinearLayout fromWalletLayout = findViewById(R.id.from_wallet_layout);
        LinearLayout walletLayout = findViewById(R.id.this_wallet_layout);
        LinearLayout targetWalletLayout = findViewById(R.id.target_wallet_layout);

        ImageView categoryIcon = findViewById(R.id.category_icon);
        ImageView walletIcon = findViewById(R.id.wallet_icon);
        ImageView fromWalletIcon = findViewById(R.id.from_wallet_icon);
        ImageView targetWalletIcon = findViewById(R.id.target_wallet_icon);

        TextView categoryText = findViewById(R.id.category_txtview);
        TextView walletText = findViewById(R.id.wallet_name_txtview);
        TextView walletAmount = findViewById(R.id.wallet_amount_txtview);
        TextView fromWalletText = findViewById(R.id.from_wallet_txtview);
        TextView fromWalletAmount = findViewById(R.id.from_wallet_amount_txtview);
        TextView targetWalletText = findViewById(R.id.target_wallet_name_txtview);
        TextView targetWalletAmount = findViewById(R.id.target_wallet_amount_txtview);


        String transferType = clickedItem.getLoaiGiaoDich();

        if(transferType.equals("INCOME")){
            incomeButton.setVisibility(MaterialButton.VISIBLE);
            expenseButton.setVisibility(MaterialButton.GONE);
            transferButton.setVisibility(MaterialButton.GONE);

            setIncomeBackground(incomeButton, transferType);
            setExpenseBackground(expenseButton, transferType);
            setTransferBackground(transferButton, transferType);

            categoryTextView.setText("View Category");
            walletTextView.setText("View Wallet");
            categoryLayout.setVisibility(LinearLayout.VISIBLE);
            fromWalletLayout.setVisibility(LinearLayout.GONE);
            walletLayout.setVisibility(LinearLayout.VISIBLE);
            targetWalletLayout.setVisibility(LinearLayout.GONE);

            Glide.with(this).load(clickedItem.getHinhAnh()).into(categoryIcon);
            walletIcon.setImageResource(R.drawable.wallet);

            if(clickedItem.getDanhMuc() != null && clickedItem.getViTien() != null) {
                categoryText.setText(clickedItem.getDanhMuc().name);
                walletText.setText(clickedItem.getViTien().name);
                String currencySymbol = "";
                switch (clickedItem.getViTien().currency_unit){
                    case "USD":
                        currencySymbol = "$";
                        break;
                    case "VND":
                        currencySymbol = "đ";
                        break;
                    case "EUR":
                        currencySymbol = "€";
                        break;
                    case "CNY":
                        currencySymbol = "¥";
                        break;
                }
                String amount = formatCurrency(Double.parseDouble(clickedItem.getViTien().amount.toString()), clickedItem.getViTien().currency_unit) + " " + currencySymbol;
                walletAmount.setText(amount);
            }

        }
        else if(transferType.equals("EXPENSE")){
            incomeButton.setVisibility(MaterialButton.GONE);
            expenseButton.setVisibility(MaterialButton.VISIBLE);
            transferButton.setVisibility(MaterialButton.GONE);

            setIncomeBackground(incomeButton, transferType);
            setExpenseBackground(expenseButton, transferType);
            setTransferBackground(transferButton, transferType);

            categoryTextView.setText("View Category");
            walletTextView.setText("View Wallet");
            categoryLayout.setVisibility(LinearLayout.VISIBLE);
            fromWalletLayout.setVisibility(LinearLayout.GONE);
            walletLayout.setVisibility(LinearLayout.VISIBLE);
            targetWalletLayout.setVisibility(LinearLayout.GONE);

            Glide.with(this).load(clickedItem.getHinhAnh()).into(categoryIcon);
            walletIcon.setImageResource(R.drawable.wallet);

            if(clickedItem.getDanhMuc() != null && clickedItem.getViTien() != null) {
                categoryText.setText(clickedItem.getDanhMuc().name);
                walletText.setText(clickedItem.getViTien().name);
                String currencySymbol = "";
                switch (clickedItem.getViTien().currency_unit){
                    case "USD":
                        currencySymbol = "$";
                        break;
                    case "VND":
                        currencySymbol = "đ";
                        break;
                    case "EUR":
                        currencySymbol = "€";
                        break;
                    case "CNY":
                        currencySymbol = "¥";
                        break;
                }
                String amount = formatCurrency(Double.parseDouble(clickedItem.getViTien().amount.toString()), clickedItem.getViTien().currency_unit) + " " + currencySymbol;
                walletAmount.setText(amount);
            }
        }
        else {
            incomeButton.setVisibility(MaterialButton.GONE);
            expenseButton.setVisibility(MaterialButton.GONE);
            transferButton.setVisibility(MaterialButton.VISIBLE);

            setIncomeBackground(incomeButton, transferType);
            setExpenseBackground(expenseButton, transferType);
            setTransferBackground(transferButton, transferType);

            categoryTextView.setText("From Wallet");
            walletTextView.setText("To Wallet");
            categoryLayout.setVisibility(LinearLayout.GONE);
            fromWalletLayout.setVisibility(LinearLayout.VISIBLE);
            walletLayout.setVisibility(LinearLayout.GONE);
            targetWalletLayout.setVisibility(LinearLayout.VISIBLE);

            fromWalletIcon.setImageResource(R.drawable.wallet);
            targetWalletIcon.setImageResource(R.drawable.wallet);

            if(clickedItem.getViTien() != null && clickedItem.getViDuocNhanTien() != null) {
                fromWalletText.setText(clickedItem.getViTien().name);
                targetWalletText.setText(clickedItem.getViDuocNhanTien().name);
                String fromCurrencySymbol = "";
                switch (clickedItem.getViTien().currency_unit){
                    case "USD":
                        fromCurrencySymbol = "$";
                        break;
                    case "VND":
                        fromCurrencySymbol = "đ";
                        break;
                    case "EUR":
                        fromCurrencySymbol = "€";
                        break;
                    case "CNY":
                        fromCurrencySymbol = "¥";
                        break;
                }

                String targetCurrencySymbol = "";
                switch (clickedItem.getViDuocNhanTien().currency_unit){
                    case "USD":
                        targetCurrencySymbol = "$";
                        break;
                    case "VND":
                        targetCurrencySymbol = "đ";
                        break;
                    case "EUR":
                        targetCurrencySymbol = "€";
                        break;
                    case "CNY":
                        targetCurrencySymbol = "¥";
                        break;
                }
                String fromAmount = formatCurrency(Double.parseDouble(clickedItem.getViTien().amount.toString()), clickedItem.getViTien().currency_unit) + " " + fromCurrencySymbol;
                fromWalletAmount.setText(fromAmount);
                String targetAmount = formatCurrency(Double.parseDouble(clickedItem.getViDuocNhanTien().amount.toString()), clickedItem.getViDuocNhanTien().currency_unit) + " " + targetCurrencySymbol;
                targetWalletAmount.setText(targetAmount);
            }
        }

        MaterialButton currencyButton = findViewById(R.id.view_trans_currency_btn);
        currencyButton.setText(clickedItem.getDonViTien());

        TextInputLayout amountLayout = findViewById(R.id.Amount_txtbox_layout);
        TextInputEditText amountEditText = findViewById(R.id.Amount_txtbox);

        switch (currencyButton.getText().toString()){
            case "VND":
                amountLayout.setPrefixText("đ");
                break;
            case "USD":
                amountLayout.setPrefixText("$");
                break;
            case "EUR":
                amountLayout.setPrefixText("€");
                break;
            case "CNY":
                amountLayout.setPrefixText("¥");
                break;
        }
        amountEditText.setText(clickedItem.getSoTien());

        TextInputEditText noteEditText = findViewById(R.id.note_txtbox);
        noteEditText.setText(clickedItem.getGhiChu());

        TextInputEditText dateEditText = findViewById(R.id.select_date_txtbox);
        dateEditText.setText(convertDateFormat(clickedItem.getNgayThang()));


    }

    private void setIncomeBackground(MaterialButton income_btn, String transferType){
        if (transferType.equals("INCOME")){
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

    private void setExpenseBackground(MaterialButton expense_btn, String transferType){
        if (transferType.equals("EXPENSE")){
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

    private void setTransferBackground(MaterialButton transfer_btn, String transferType){
        if (transferType.equals("TRANSFER")){
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

    public String convertDateFormat(String inputDate) {
        DateTimeFormatter inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormat = DateTimeFormat.forPattern("dd/MM/yyyy");

        DateTime dateTime = inputFormat.parseDateTime(inputDate);
        return outputFormat.print(dateTime);
    }
}