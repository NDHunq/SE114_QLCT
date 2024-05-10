package com.example.qlct.Budget;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.CreateBudgetEntity;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Utils.BudgetAPIUtil;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.AddTransaction;
import com.example.qlct.Category;
import com.example.qlct.Category_Add;
import com.example.qlct.Category_adapter;
import com.example.qlct.Fragment.MyDialogFragment;
import com.example.qlct.OnDataPass;
import com.example.qlct.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

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
    private List<Category> categoryList;
    ImageView hinhanh;
    private TextView exit_budget;
    ArrayList<GetAllCategoryy> list;
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
        GetAllCategory();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetAPIUtil budgetAPIUtil = new BudgetAPIUtil();
                CreateBudgetEntity createBudgetEntity = new CreateBudgetEntity(GetIDCategory(Category.getText().toString()), Double.parseDouble(amount.getText().toString()), date.getText().toString());
                budgetAPIUtil.createBudget(createBudgetEntity);
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
        Category_adapter categoryAdapter = new Category_adapter(dialog.getContext(), R.layout.category_list_item, categoryList);
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
                Category clickedCategory = categoryList.get(position);
                Category.setText(clickedCategory.getCategory_name());
                hinhanh.setImageResource(clickedCategory.getImage());
                dialog.dismiss();
            }
        });
    }
    private void AnhXaCategory(){
        categoryList = new ArrayList<com.example.qlct.Category>();
        categoryList.add(new Category("Food", R.drawable.dish));
        categoryList.add(new Category("Clothing", R.drawable.clothes));
        categoryList.add(new Category("Thu nhập", R.drawable.dish));
        categoryList.add(new Category("Food", R.drawable.dish));
        categoryList.add(new Category("Food", R.drawable.dish));
        categoryList.add(new Category("Food", R.drawable.dish));
        categoryList.add(new Category("Food", R.drawable.dish));
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
    public void onDataPass(String data) {
        date.setText(data);
    }

}
