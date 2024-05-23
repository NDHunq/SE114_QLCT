package com.example.qlct.Home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class Home_New_wallet extends AppCompatActivity {
    String currency;
    String getcurrency;

    private boolean isUserInteracting = true;

    int sc=1;
    int sb=1;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_wallet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextInputEditText ammount = findViewById(R.id.Amount_txtbox);
        TextInputEditText name =findViewById(R.id.Walletname_txtbox);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateWalletName();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

        ImageButton upload = findViewById(R.id.addnewbut);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText walletname = findViewById(R.id.Walletname_txtbox);
                TextInputEditText amount = findViewById(R.id.Amount_txtbox);
                WalletAPIUtil test = new WalletAPIUtil();
                if(!validate())
                {
                    Toast.makeText(Home_New_wallet.this, "Please enter all required fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CreateWalletEntity createWalletEntity = new CreateWalletEntity(name.getText().toString(),Integer.parseInt(ammount.getText().toString().replaceAll("[.,]", "")),getcurrency);
                    WalletAPIUtil WalletAPIUtil = new WalletAPIUtil();
                    WalletAPIUtil.createWalletAPI(createWalletEntity);
                    setResult(Activity.RESULT_OK);
                    Toast.makeText(Home_New_wallet.this, "Create wallet successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        ImageButton cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView currency = findViewById(R.id.txtCurrency);
        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  showDialog();
                }
        });

        ammount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isUserInteracting && !charSequence.toString().isEmpty()) {
                    isUserInteracting = false; // Set to false before changing text programmatically
                    try {
                        // Remove thousands separators before parsing
                        String cleanString = charSequence.toString().replaceAll("[.,]", "");
                        double amount = Double.parseDouble(cleanString);
                        TextView crr = findViewById(R.id.crr);
                        String currency = "";
                        switch (crr.getText().toString()) {
                            case "$":
                                currency = "USD";
                                break;
                            case "đ":
                                currency = "VND";
                                break;
                            case "€":
                                currency = "EUR";
                                break;
                            case "¥":
                                currency = "CNY";
                                break;
                        }
                        String formattedAmount = formatCurrency(amount, currency);
                        ammount.setText(formattedAmount);
                        ammount.setSelection(formattedAmount.length()); // Move cursor to the end
                    } catch (Exception e) {
                        // Handle or log the error
                        e.printStackTrace();
                    }
                    validateAmount();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isUserInteracting = true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null)
        {
            Uri selectedImage = data.getData();
            ImageView img = findViewById(R.id.hinhanh);
            img.setImageURI(selectedImage);
            CardView ic = findViewById(R.id.icon);
            ic.setCardBackgroundColor(Color.WHITE);
        }
    }

    private void showDialog()
    { final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_currency);
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        LinearLayout bo1 = dialog.findViewById(R.id.bo1);
        LinearLayout bo2 = dialog.findViewById(R.id.bo2);
        LinearLayout bo3 = dialog.findViewById(R.id.bo3);
        LinearLayout bo4 = dialog.findViewById(R.id.bo4);

        TextView txt1 = findViewById(R.id.crr);
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
                TextView txt = findViewById(R.id.crr);
                TextView txt2 = findViewById(R.id.txtCurrency);
                TextInputLayout amountLayout = findViewById(R.id.amountlayout);
                txt.setText(currency);
                if(currency.equals("$"))
                {
                    txt2.setText("United States Dollar");
                    getcurrency="USD";
                    amountLayout.setPrefixText("$");
                }
                else if(currency.equals("đ"))
                {
                    txt2.setText("Việt Nam Đồng");
                    getcurrency="VND";
                    amountLayout.setPrefixText("đ");
                }
                else if(currency.equals("€"))
                {
                    txt2.setText("Euro");
                    getcurrency="EUR";
                    amountLayout.setPrefixText("€");
                }
                else if(currency.equals("¥"))
                {
                    txt2.setText("Yaun Renminbi");
                    getcurrency="CNY";
                    amountLayout.setPrefixText("¥");
                }
                dialog.dismiss();
            }
        });
    }
    private  void showDialog2()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_icon_category);
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        ConstraintLayout bo1 = dialog.findViewById(R.id.bo1);
        ConstraintLayout bo2 = dialog.findViewById(R.id.bo2);
        ConstraintLayout bo3 = dialog.findViewById(R.id.bo3);
        ConstraintLayout bo4 = dialog.findViewById(R.id.bo4);
        ConstraintLayout bo5 = dialog.findViewById(R.id.bo5);
        ConstraintLayout bo6 = dialog.findViewById(R.id.bo6);
        ConstraintLayout bo7 = dialog.findViewById(R.id.bo7);
        ConstraintLayout bo8 = dialog.findViewById(R.id.bo8);
        ConstraintLayout bo9 = dialog.findViewById(R.id.bo9);
        ConstraintLayout bo10 = dialog.findViewById(R.id.bo10);
        ConstraintLayout bo11 = dialog.findViewById(R.id.bo11);
        ConstraintLayout bo12 = dialog.findViewById(R.id.bo12);
        ConstraintLayout bo13 = dialog.findViewById(R.id.bo13);
        ConstraintLayout bo14 = dialog.findViewById(R.id.bo14);
        ConstraintLayout bo15 = dialog.findViewById(R.id.bo15);
        ConstraintLayout bo16 = dialog.findViewById(R.id.bo16);
        ConstraintLayout bo17 = dialog.findViewById(R.id.bo17);
        ConstraintLayout bo18 = dialog.findViewById(R.id.bo18);


        bo1.setOnClickListener(v -> {
            sb=1;
            bo1.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);


        });
        bo2.setOnClickListener(v -> {
            bo2.setBackgroundResource(R.drawable.nenluachon);
            bo1.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo3.setOnClickListener(v -> {
            bo3.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo4.setOnClickListener(v -> {
            bo4.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo5.setOnClickListener(v -> {
            bo5.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo6.setOnClickListener(v -> {
            bo6.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo7.setOnClickListener(v -> {
            bo7.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo8.setOnClickListener(v -> {
            bo8.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo9.setOnClickListener(v -> {
            bo9.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo10.setOnClickListener(v -> {
            bo10.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo11.setOnClickListener(v -> {
            bo11.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo12.setOnClickListener(v -> {
            bo12.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });

        bo13.setOnClickListener(v -> {
            bo13.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });

        bo14.setOnClickListener(v -> {
            bo14.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });

        bo15.setOnClickListener(v -> {
            bo15.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo16.setOnClickListener(v -> {
            bo16.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });
        bo17.setOnClickListener(v -> {
            bo17.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo18.setBackgroundResource(0);

        });

        bo18.setOnClickListener(v -> {
            bo18.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);
            bo7.setBackgroundResource(0);
            bo8.setBackgroundResource(0);
            bo9.setBackgroundResource(0);
            bo10.setBackgroundResource(0);
            bo11.setBackgroundResource(0);
            bo12.setBackgroundResource(0);
            bo13.setBackgroundResource(0);
            bo14.setBackgroundResource(0);
            bo15.setBackgroundResource(0);
            bo16.setBackgroundResource(0);
            bo17.setBackgroundResource(0);
            bo1.setBackgroundResource(0);

        });




        bo1.setBackgroundResource(R.drawable.nenluachon);

        TextView apply = dialog.findViewById(R.id.apply);
        apply.setOnClickListener(v -> {
            if(sb==1)
            {
                ImageView ic = findViewById(R.id.hinhanh);
                ic.setBackgroundResource(R.drawable.anh1);
            }
            if(sc==1)
            {
                CardView ic = findViewById(R.id.icon);
                ic.setCardBackgroundColor(Color.parseColor("#FF0000"));
            }
            if (sc==2)
            {
                CardView ic = findViewById(R.id.icon);
                ic.setCardBackgroundColor(Color.parseColor("#FF7A00"));
            }
            if (sc==3)
            {
                CardView ic = findViewById(R.id.icon);
                ic.setCardBackgroundColor(Color.parseColor("#FFF500"));
            }
            if (sc==4)
            {
                CardView ic = findViewById(R.id.icon);
                ic.setCardBackgroundColor(Color.parseColor("#5CC2F2"));
            }
            if (sc==5)
            {
                CardView ic = findViewById(R.id.icon);
                ic.setCardBackgroundColor(Color.parseColor("#177715"));
            }
            if (sc==6)
            {
                CardView ic = findViewById(R.id.icon);
                ic.setCardBackgroundColor(Color.parseColor("#890AEC"));
            }

            dialog.dismiss();
        });
        TextView upload = dialog.findViewById(R.id.upload);
        upload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,3);
            dialog.dismiss();
        });
    }

    private boolean validateWalletName() {
        TextInputLayout walletNameLayout = findViewById(R.id.walletlayout);
        TextInputEditText walletName = findViewById(R.id.Walletname_txtbox);
        if (walletName.getText().toString().isEmpty()) {
            walletNameLayout.setError("Please enter wallet name!");
            return false;
        }
        walletNameLayout.setError(null);
        return true;
    }

    private boolean validateAmount() {
        TextInputLayout amountLayout = findViewById(R.id.amountlayout);
        TextInputEditText amount = findViewById(R.id.Amount_txtbox);
        if (amount.getText().toString().isEmpty()) {
            amountLayout.setError("Please enter amount!");
            amount.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
            amountLayout.setPrefixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.errorColor, null)));
            return false;
        }
        amountLayout.setError(null);
        amount.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnen, null)));
        amountLayout.setPrefixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.xanhnen, null)));
        return true;
    }

    private boolean validate(){
        boolean isWalletNameValid = validateWalletName();
        boolean isAmountValid = validateAmount();
        return isWalletNameValid && isAmountValid;
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
}