package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.ChangePassWord;

public class NewPassword extends AppCompatActivity {

ImageButton backnewpass;
EditText enterpass;
    EditText currentpass;
EditText repeatpass;
ImageButton showpass;
ImageButton showpass1;
Button save;
    ImageButton showpass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backnewpass=findViewById(R.id.backnewpass);
        backnewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(NewPassword.this,Setting.class);
                startActivity(myintent);
            }
        });
        save=findViewById(R.id.save);
        currentpass=findViewById(R.id.currentpass);
        currentpass.setTransformationMethod(new PasswordTransformationMethod());
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());

        repeatpass=findViewById(R.id.repeatpass);
        repeatpass.setTransformationMethod(new PasswordTransformationMethod());
        showpass = findViewById(R.id.showpass);
        showpass1=findViewById(R.id.showpass1);
        showpass2=findViewById(R.id.showpass2);
        save.setOnClickListener(new View.OnClickListener() {
           int flag=1;
            @Override
            public void onClick(View v) {
                if(currentpass.getText().toString().isEmpty()||enterpass.getText().toString().isEmpty()||repeatpass.getText().toString().isEmpty())
                {
                    flag=0;
                }
                if(enterpass.getText().toString().equals(repeatpass.getText().toString()))
                {
                    flag=1;
                }
                else
                {
                    flag=0;
                }

                ChangePassWord changePassWord = new ChangePassWord(currentpass.getText().toString(),enterpass.getText().toString());
                Intent myintent=new Intent(NewPassword.this, Login_Signin.class);
                startActivity(myintent);
            }
        });
        showpass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentpass.getTransformationMethod() != null) {
                    currentpass.setTransformationMethod(null);
                } else {
                    currentpass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
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
    }
}