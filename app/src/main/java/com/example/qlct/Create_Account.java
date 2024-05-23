package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.RegisterEntity;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.android.material.textfield.TextInputEditText;

public class Create_Account extends AppCompatActivity {
TextView nextcreateaccount;
TextInputEditText phone;
    TextInputEditText username;
TextView errorphone;
int flag=1;
    EditText enterpass;
    EditText repeatpass;
    ImageButton showpass;
    ImageButton showpass1;
    ImageButton back;
    TextView errorinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        errorinfo=findViewById(R.id.errorinfo);
        nextcreateaccount=findViewById(R.id.nextcreateacount);
        errorphone=findViewById(R.id.errorphone);
        nextcreateaccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() ||
                        phone.getText().toString().isEmpty() ||
                        enterpass.getText().toString().isEmpty() ||
                        repeatpass.getText().toString().isEmpty()
                ) {
                    flag = 0;
                    errorinfo.setText("Please fill in all the information");
                }
                if (enterpass.getText().toString().equals(repeatpass.getText().toString())) {

                }
                else {
                    flag = 0;
                }
                if (phone.length() >= 10 && phone.length() <= 11 && phone.getText().toString().matches("[0-9]+")) {
                    if (flag == 1) {
                        Log.d("Register1", flag + "");
                        String phone_convert = phone.getText().toString();
                        if (phone_convert.startsWith("0")) {
                            phone_convert = "+84" + phone_convert.substring(1);
                        }
                        try {
                            RegisterEntity registerEntity = new RegisterEntity(username.getText().toString(), phone_convert, enterpass.getText().toString());
                            UserAPiUtil userAPiUtil = new UserAPiUtil();
                            userAPiUtil.RegisterUser(registerEntity);
                            Log.d("Register1", "Request successful");
                        } catch (Exception e) {
                            Log.d("Register1", "Request failed with status code: " + e.getMessage());
                        }
                        Log.d("Register1", "2");
                        Intent myintent = new Intent(Create_Account.this, email_login.class);
                        Log.d("Register1", "3");
                    myintent.putExtra("phone", phone.getText().toString());
                    Log.d("Register1", "4");
                        startActivity(myintent);
Log.d("Register1", "da intent thanh cong");

                    }
                }
                else
                {
                    errorphone.setText("Please enter a valid phone number");
                }

            }
        });


        phone=findViewById(R.id.phone);
        username=findViewById(R.id.username);
        back=findViewById(R.id.back);
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());

        repeatpass=findViewById(R.id.repeatpass);
        repeatpass.setTransformationMethod(new PasswordTransformationMethod());
        showpass = findViewById(R.id.showpass);
        showpass1=findViewById(R.id.showpass1);
        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterpass.getTransformationMethod() != null) {
                    enterpass.setTransformationMethod(null);
                } else {
                    enterpass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        showpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeatpass.getTransformationMethod() != null) {
                    repeatpass.setTransformationMethod(null);
                } else {
                    repeatpass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}