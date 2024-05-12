package com.example.qlct.Budget;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.qlct.Category2;
import com.example.qlct.Category_Add;
import com.example.qlct.Category_adapter2;
import com.example.qlct.Fragment.MyDialogFragment;
import com.example.qlct.OnDataPass;
import com.example.qlct.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddBudget extends AppCompatActivity implements OnDataPass {
    ImageView date_picker;
    TextView apply;
    Dialog dialog ;
    TextView renew;
    TextView noRenew;
    TextView crr;
    FrameLayout frameLayout;
    EditText date;
    String currency;
    ImageButton done;
    LinearLayout Select_category;
    int sc=1;
    int sb=1;
    TextView Category;
    TextInputEditText  amount;
    private ListView categoryListView;
    private List<Category2> categoryList;
    ImageView hinhanh;
    private TextView exit_budget;
    TextView type;
    String date_unit="";
    ArrayList<GetAllCategoryy> list;
    Switch switch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        date_picker=this.findViewById(R.id.date_pickerr);
        exit_budget=this.findViewById(R.id.exit_budget);
        date=this.findViewById(R.id.date);
        crr=this.findViewById(R.id.crr);
        done=this.findViewById(R.id.done);
        Category=this.findViewById(R.id.Category_txt);
        amount=this.findViewById(R.id.Amount_txtbox);
        Select_category=this.findViewById(R.id.Select_category);
        hinhanh=this.findViewById(R.id.hinhanh);
        type=this.findViewById(R.id.type);
        switch1 = this.findViewById(R.id.switch1);
        GetAllCategory();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetAPIUtil budgetAPIUtil = new BudgetAPIUtil();
                Boolean enable_notification= switch1.isChecked();
                if(type.getText()=="Renew") {
                    if(String.valueOf(date.getText())=="Daily"||String.valueOf(date.getText())=="Weekly" || String.valueOf(date.getText())=="Monthly" || String.valueOf(date.getText())=="Yearly")
                    {
                        CreateRenewBudgetEntity createRenewBudgetEntity = new CreateRenewBudgetEntity(GetIDCategory(Category.getText().toString()), Double.parseDouble(amount.getText().toString()), date.getText().toString(), enable_notification);
                        budgetAPIUtil.createRenewBudget(createRenewBudgetEntity);
                    }
                    else
                    {
                        String realDate=date.getText().toString();;
                        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            Date date = originalFormat.parse(realDate);
                            realDate = targetFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        CreateRenewBudgetEntity createRenewBudgetEntity = new CreateRenewBudgetEntity(GetIDCategory(Category.getText().toString()), Double.parseDouble(amount.getText().toString()),"Custom", realDate, enable_notification);
                        budgetAPIUtil.createRenewBudget(createRenewBudgetEntity);
                    }
                }
                else {
                    String realDate=date.getText().toString();;
                    if(date_unit!="Day")
                    {
                        realDate=realDate.replace(" - ", " ");
                    }
                    CreateNoRenewBudgetEntity createNoRenewBudgetEntity = new CreateNoRenewBudgetEntity(GetIDCategory(Category.getText().toString()), Double.parseDouble(amount.getText().toString()), date_unit.toUpperCase(), realDate, enable_notification);
                    budgetAPIUtil.createNoRenewBudget(createNoRenewBudgetEntity);
                }
                finish();
            }
        });
        crr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });

        exit_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        Select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogSelectCate();
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
    void ShowDialogSelectCate()
    {
        final Dialog dialog = new Dialog(AddBudget.this);

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
        Category_adapter2 categoryAdapter = new Category_adapter2(dialog.getContext(), R.layout.category_list_item, categoryList);
        categoryListView.setAdapter(categoryAdapter);

        TextView addnew = dialog.findViewById(R.id.select_category_addnew_btn);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddCategoryIntent = new Intent(dialog.getContext(), Category_Add.class);
                startActivity(AddCategoryIntent);
            }
        });
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category2 clickedCategory = categoryList.get(position);
                Category.setText(clickedCategory.getCategory_name());
                //hinhanh.setImageResource(clickedCategory.getImage());
                Glide.with(AddBudget.this).load(clickedCategory.getImage()).into(hinhanh);
                dialog.dismiss();
            }
        });
    }
    private void AnhXaCategory(){
        categoryList = new ArrayList<>();
        ArrayList<GetAllCategoryy> list = new CategoryAPIUntill().getAllCategoryys();
        for (int i = 0; i < list.size(); i++) {
            categoryList.add(new Category2(list.get(i).getName(), list.get(i).getPicture()));
        }
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
