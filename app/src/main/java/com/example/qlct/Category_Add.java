package com.example.qlct;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Category_Add extends AppCompatActivity {
    int sb=1;
    int sc=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView ic = findViewById(R.id.icon);
        ic.setOnClickListener(v -> {
       showDialog();
        });
        LinearLayout income= findViewById(R.id.incomeclick);
        LinearLayout expense= findViewById(R.id.expenseclick);
        income.setOnClickListener(v -> {
            income.setBackgroundResource(R.drawable.nenxanhlacay7dp);
            TextView incomeText = findViewById(R.id.incometxt);
            incomeText.setTextColor(getResources().getColor(R.color.white));
            TextView expenseText = findViewById(R.id.expensetxt);
            expenseText.setTextColor(getResources().getColor(R.color.black));
            expense.setBackgroundResource(R.drawable.hinhchunhatvien7dp);
            ImageView incomeimg = findViewById(R.id.incomeimg);
            ImageView expenseimg = findViewById(R.id.expenseimg);
            incomeimg.setImageResource(R.drawable.downarrow_white);
            expenseimg.setImageResource(R.drawable.uparrow_black);
        });
        expense.setOnClickListener(v -> {
            TextView incomeText = findViewById(R.id.incometxt);
            incomeText.setTextColor(getResources().getColor(R.color.black));
            TextView expenseText = findViewById(R.id.expensetxt);
            expenseText.setTextColor(getResources().getColor(R.color.white));

            expense.setBackgroundResource(R.drawable.nendo7dp);
            income.setBackgroundResource(R.drawable.hinhchunhatvien7dp);
            ImageView incomeimg = findViewById(R.id.incomeimg);
            ImageView expenseimg = findViewById(R.id.expenseimg);
            incomeimg.setImageResource(R.drawable.downarrow_black);
            expenseimg.setImageResource(R.drawable.uparrow_white);

        });
        TextView cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            finish();
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

    private  void showDialog() {
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

        bo1.setOnClickListener(v -> {
         bo1.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);


        });
        bo2.setOnClickListener(v -> {
            bo2.setBackgroundResource(R.drawable.nenluachon);
            bo1.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);

        });
        bo3.setOnClickListener(v -> {
            bo3.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);

        });
        bo4.setOnClickListener(v -> {
            bo4.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo6.setBackgroundResource(0);

        });
        bo5.setOnClickListener(v -> {
            bo5.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo1.setBackgroundResource(0);
            bo6.setBackgroundResource(0);

        });
        bo6.setOnClickListener(v -> {
            bo6.setBackgroundResource(R.drawable.nenluachon);
            bo2.setBackgroundResource(0);
            bo3.setBackgroundResource(0);
            bo4.setBackgroundResource(0);
            bo5.setBackgroundResource(0);
            bo1.setBackgroundResource(0);

        });

        ConstraintLayout co1 = dialog.findViewById(R.id.co1);
        ConstraintLayout co2 = dialog.findViewById(R.id.co2);
        ConstraintLayout co3 = dialog.findViewById(R.id.co3);
        ConstraintLayout co4 = dialog.findViewById(R.id.co4);
        ConstraintLayout co5 = dialog.findViewById(R.id.co5);
        ConstraintLayout co6 = dialog.findViewById(R.id.co6);
        co1.setOnClickListener(
                v -> {
                    sc=1;
                    co1.setBackgroundResource(R.drawable.nenluachon);
                    co2.setBackgroundResource(0);
                    co3.setBackgroundResource(0);
                    co4.setBackgroundResource(0);
                    co5.setBackgroundResource(0);
                    co6.setBackgroundResource(0);
                }
        );
        co2.setOnClickListener(
                v -> {
                    sc=2;
                    co2.setBackgroundResource(R.drawable.nenluachon);
                    co1.setBackgroundResource(0);
                    co3.setBackgroundResource(0);
                    co4.setBackgroundResource(0);
                    co5.setBackgroundResource(0);
                    co6.setBackgroundResource(0);
                }
        );
        co3.setOnClickListener(
                v -> {
                    sc=3;
                    co3.setBackgroundResource(R.drawable.nenluachon);
                    co2.setBackgroundResource(0);
                    co1.setBackgroundResource(0);
                    co4.setBackgroundResource(0);
                    co5.setBackgroundResource(0);
                    co6.setBackgroundResource(0);
                }
        );

        co4.setOnClickListener(
                v -> {
                    sc=4;
                    co4.setBackgroundResource(R.drawable.nenluachon);
                    co2.setBackgroundResource(0);
                    co3.setBackgroundResource(0);
                    co1.setBackgroundResource(0);
                    co5.setBackgroundResource(0);
                    co6.setBackgroundResource(0);
                }
        );
        co5.setOnClickListener(
                v -> {
                    sc=5;
                    co5.setBackgroundResource(R.drawable.nenluachon);
                    co2.setBackgroundResource(0);
                    co3.setBackgroundResource(0);
                    co4.setBackgroundResource(0);
                    co1.setBackgroundResource(0);
                    co6.setBackgroundResource(0);
                }
        );
        co6.setOnClickListener(
                v -> {
                    sc=6;
                    co6.setBackgroundResource(R.drawable.nenluachon);
                    co2.setBackgroundResource(0);
                    co3.setBackgroundResource(0);
                    co4.setBackgroundResource(0);
                    co5.setBackgroundResource(0);
                    co1.setBackgroundResource(0);
                }
        );
        bo1.setBackgroundResource(R.drawable.nenluachon);
        co1.setBackgroundResource(R.drawable.nenluachon);
        TextView apply = dialog.findViewById(R.id.apply);
        apply.setOnClickListener(v -> {
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


}