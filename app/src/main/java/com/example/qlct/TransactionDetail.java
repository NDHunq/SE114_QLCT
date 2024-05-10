package com.example.qlct;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.List;

public class TransactionDetail extends AppCompatActivity {


    private ListView walletListView;

    private List<Wallet> walletList;

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
    }
}