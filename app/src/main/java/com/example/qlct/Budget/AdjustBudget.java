package com.example.qlct.Budget;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.qlct.API_Entity.CreateNoRenewBudgetEntity;
import com.example.qlct.API_Entity.CreateRenewBudgetEntity;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Utils.BudgetAPIUtil;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.Fragment.MyDialogFragment;
import com.example.qlct.OnDataPass;
import com.example.qlct.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdjustBudget extends AppCompatActivity implements OnDataPass {
    String currency;
    String date_unit;
    TextView exit_budget;
    TextView Category_txt;
    ImageButton xoa;
    ImageButton done;
    LinearLayout Select_category;
    TextView crr;
    ImageView date_pickerr;
    TextInputEditText Amount_txtbox;
    TextInputEditText date;
    TextView type;
    Switch switch1;
    Budget budget;
    ImageView hinhanh;
    ArrayList<GetAllCategoryy> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget_adjust);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        budget = (Budget) getIntent().getSerializableExtra("budget");
        GetAllCategory();
        AnhXa();
        Init();
    }
    void Init(){
        if(budget != null){
            Category_txt.setText(budget.getCategory());
            Amount_txtbox.setText(String.valueOf(budget.getMax_money()));
            type.setText(budget.getType());
            Glide.with(this)
                    .load(budget.getImage())
                    .into(hinhanh);
        }

    }
    void AnhXa(){
        exit_budget = findViewById(R.id.exit_budget);
        Category_txt = findViewById(R.id.Category_txt);
        xoa = findViewById(R.id.xoa);
        done = findViewById(R.id.done);
        Select_category = findViewById(R.id.Select_category);
        crr = findViewById(R.id.crr);
        date_pickerr = findViewById(R.id.date_pickerr);
        Amount_txtbox = findViewById(R.id.Amount_txtbox);
        date = findViewById(R.id.date);
        type = findViewById(R.id.type);
        switch1 = findViewById(R.id.switch1);
        hinhanh = findViewById(R.id.hinhanh);
        exit_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetAPIUtil budgetAPIUtil = new BudgetAPIUtil();
                if(type.getText().equals("NO_RENEW"))
                {
                    CreateNoRenewBudgetEntity createNoRenewBudgetEntity = new CreateNoRenewBudgetEntity(GetIDCategory(Category_txt.getText().toString()), Double.parseDouble(Amount_txtbox.getText().toString()), date_unit.toUpperCase(), date.getText().toString(), switch1.isChecked());
                    budgetAPIUtil.updateNoRenewBudget(budget.getId(),createNoRenewBudgetEntity);
                }
                else
                {
                    if((date.getText().equals("Daily")||date.getText().equals("Weekly")||date.getText().equals("Monthly")||date.getText().equals("Yearly")))
                    {
                        CreateRenewBudgetEntity createRenewBudgetEntity = new CreateRenewBudgetEntity(GetIDCategory(Category_txt.getText().toString()), Double.parseDouble(Amount_txtbox.getText().toString()), date.getText().toString(), switch1.isChecked());
                        budgetAPIUtil.updateRenewBudget(budget.getId(),createRenewBudgetEntity);
                    }
                    else
                    {
                        String realDate=date.getText().toString();
                        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            Date date = originalFormat.parse(realDate);
                            realDate = targetFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        CreateRenewBudgetEntity createRenewBudgetEntity = new CreateRenewBudgetEntity(GetIDCategory(Category_txt.getText().toString()), Double.parseDouble(Amount_txtbox.getText().toString()), date_unit, realDate, switch1.isChecked());
                        budgetAPIUtil.updateRenewBudget(budget.getId(),createRenewBudgetEntity);
                    }
                }
                finish();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetAPIUtil budgetAPIUtil = new BudgetAPIUtil();
                budgetAPIUtil.deleteBudget(budget.getId());
                finish();
            }
        });
        crr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });
        date_pickerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ShowDialog();
                } catch (Exception e) {
                    Log.d("aaaaaaaaaaaaaaaaaaaa", e.toString());
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
    }
    void GetAllCategory()
    {
        list=new CategoryAPIUntill().getAllCategoryys();
    }
    String GetIDCategory(String name)
    {
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getName().equals(name))
            {
                return list.get(i).getId();
            }
        }
        return "";
    }
    void ShowDialog()
    {
        MyDialogFragment dialogFragment;
        dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }
    private void showDialog1()
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
                txt.setText(currency);
                dialog.dismiss();
            }
        });
    }

    public void SetData(String data)
    {
        date.setText(data);
    }
    public void SetType(String data)
    {
        type.setText(data);
    }
    public void SetDateUnit(String unit)
    {
        date_unit=unit;
    }
    public void onDataPass(String data) {
        date.setText(data);
    }
}