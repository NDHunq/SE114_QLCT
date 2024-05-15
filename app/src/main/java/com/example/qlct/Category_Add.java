package com.example.qlct;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
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
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.qlct.API_Entity.CreateCategoryEntity;
import com.example.qlct.API_Entity.CreateCategoryEntity_quyen;
import com.example.qlct.API_Entity.CreateWalletEntity;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;

public class Category_Add extends AppCompatActivity {
    int sb=1;
    int sc=1;
    int type=0;

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
        ImageButton upload = findViewById(R.id.button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText category_name = findViewById(R.id.categoryname);

                CategoryAPIUntill test = new CategoryAPIUntill();
                if(category_name.getText().toString().isEmpty())
                {


                    category_name.setError("Please enter wallet name");

                }

                else if ( test.doesCategoryExist(category_name.getText().toString()) == 1){
                    // Show an error message if the wallet name is "Total" or if it already exists
                    category_name.setError("Wallet_hdp name already exists");
                } else
                {

                    String ty="INCOME";
                    if(type==1)
                    {
                        ty="EXPENSE";
                    }
                    ImageView img = findViewById(R.id.hinhanh);
                    Drawable drawable = img.getBackground();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] byteArray = baos.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    CreateCategoryEntity_quyen create = new CreateCategoryEntity_quyen(category_name.getText().toString(), encoded, ty, "aaea42e6-591e-45b3-8d4f-e0a4b80e3c69");
                    CategoryAPIUntill CategoryAPIUntill = new CategoryAPIUntill();
                    CategoryAPIUntill.createCategoryAPI(create);
                    setResult(Activity.RESULT_OK);
                    finish();
                }


            }
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
            type=0;

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
            type=1;

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


    int kq=1;



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
            bo1.setBackgroundResource(R.drawable.nenluachon);

            kq=1;
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
            kq=2;
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
            kq=3;
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
            kq=4;
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
            kq=5;
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
            kq=6;
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
            kq=7;
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
            kq=8;
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
            kq=9;
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
            kq=10;
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
            kq=11;
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
            kq=12;
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
            kq=13;
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
            kq=14;
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
            kq=15;
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
            kq=16;
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
            kq=17;
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
            kq=18;
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
            if(kq==1)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh1);
            }
            if(kq==2)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh2);
            }
            if(kq==3)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh3);
            }
            if(kq==4)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh4);
            }
            if(kq==5)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh5);
            }
            if(kq==6)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh6);
            }
            if(kq==7)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh7);
            }
            if(kq==8)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh8);
            }
            if(kq==9)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh9);
            }
            if(kq==10)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh10);
            }
            if(kq==11)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh11);
            }
            if(kq==12)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh12);
            }
            if(kq==13)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh13);
            }

            if(kq==14)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh14);
            }
            if(kq==15)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh15);
            }
            if(kq==16)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh16);
            }

            if(kq==17)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh17);
            }
            if(kq==18)
            {
                ImageView img = findViewById(R.id.hinhanh);

                img.setBackgroundResource(R.drawable.anh18);
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
    private Bitmap createColoredBitmap(Bitmap originalBitmap, int color) {
        // Create a mutable bitmap with the same dimensions as the original bitmap
        Bitmap coloredBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), originalBitmap.getConfig());

        // Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(coloredBitmap);

        // Draw the color
        canvas.drawColor(color);

        // Draw the original bitmap on top of the color
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        return coloredBitmap;
    }


}