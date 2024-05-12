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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.LoginEntity;
import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.gson.Gson;

public class Login_Signin extends AppCompatActivity {

Button signin;
    EditText enterpass;
    EditText phone;
    Button login;
    ImageButton showpass;
    TextView forgetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signin=findViewById(R.id.signin);
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());
        showpass = findViewById(R.id.showpass);
        phone=findViewById(R.id.phone);
        login=findViewById(R.id.Login);
        forgetpass=findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Login_Signin.this, forget_pass.class);
                startActivity(myintent);

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Login_Signin.this, Create_Account.class);
                startActivity(myintent);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginEntity loginEntity = new LoginEntity(phone.getText().toString(), enterpass.getText().toString());
                //Gọi API login
                UserAPiUtil userAPiUtil = new UserAPiUtil();
                try {
                    String response = userAPiUtil.Login(loginEntity);
                    // Parse the response
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                    Log.d("Login", "loginResponse: " + loginResponse.getStatus().getCode());

                    // Check if login was successful
                    if (loginResponse.getStatus().getCode() == 200) {
                        Log.d("Login", "Login successful");
                        // Login was successful, navigate to the main activity

                        Intent intent = new Intent(Login_Signin.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Login failed, show an error message
                        Toast.makeText(Login_Signin.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}