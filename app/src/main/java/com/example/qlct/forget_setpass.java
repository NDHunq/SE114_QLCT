package com.example.qlct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.ForgetPass;
import com.example.qlct.API_Entity.ForgetPassRP;
import com.example.qlct.API_Entity.OTPResponse;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class forget_setpass extends AppCompatActivity {
    EditText enterpass;
    EditText repeatpass;
    ImageButton showpass;
    ImageButton showpass1;
    ImageButton backnewpass;
    String otp_code;
    String phone;
    TextView savepass;

    TextView errorpass;
    TextView errorfill;
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
        setContentView(R.layout.activity_forget_setpass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        otp_code=getIntent().getStringExtra("OTP");
        phone=getIntent().getStringExtra("phone");
        if (phone.startsWith("0")) {
            phone = "+84" + phone.substring(1);
            Log.d("Phone", "phone_convert: " + phone);
        }
        Log.d("OTP", "onCreate: "+otp_code);
        backnewpass=findViewById(R.id.backnewpass);
        backnewpass.setOnClickListener(v -> finish());
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());
        errorpass=findViewById(R.id.errorpass);
        errorfill=findViewById(R.id.errorfill);
        repeatpass=findViewById(R.id.repeatpass);
        repeatpass.setTransformationMethod(new PasswordTransformationMethod());
        savepass=findViewById(R.id.savepass);
        savepass.setOnClickListener(new View.OnClickListener() {
            int flag1=1;
            int flag=1;

            @Override
            public void onClick(View v) {
                if(enterpass.getText().toString().isEmpty()||repeatpass.getText().toString().isEmpty())
                {
                    flag=0;
                    errorfill.setText("Please fill all the fields");
                    Log.d("newpass", "thong tin trong "+flag);
                }
                else {
                    flag=1;
                    errorfill.setText("");
                    Log.d("newpass", "thong tin co "+flag);
                }
                if(enterpass.getText().toString().equals(repeatpass.getText().toString()))
                {
                    flag1=1;
                    Log.d("newpass", "pass dung "+flag1);
                    errorpass.setText("");
                }
                else
                {
                    Log.d("newpass", "pass sai "+flag1);
                    flag1=0;
                    errorpass.setText("Password does not match");
                }



                if(flag1==1&&flag==1){
                    Log.d("Phone", "onClick: " + phone);
                    Log.d("Phone", "onClick: " + otp_code);
                    Log.d("Phone", "onClick: " + enterpass.getText().toString());
                    ForgetPass forgetPass = new ForgetPass(enterpass.getText().toString(), otp_code, phone);
                    UserAPiUtil userAPiUtil = new UserAPiUtil();
                    Log.d("Phone", "1");
                    userAPiUtil.resetPassword(forgetPass, new UserAPiUtil.OnTaskCompleted() {

                        @Override
                        public void onTaskCompleted(String result) {
                            Log.d("Phone", "2");
                            if (result != null) {
                                Gson gson = new Gson();
                                ForgetPassRP forgetPassRP  = gson.fromJson(result, ForgetPassRP.class);
                                Log.d("Phone", "3");
                                if(forgetPassRP.getStatus().getCode() == 200) {
                                    Log.d("Phone", "4");
                                    Intent myintent = new Intent(forget_setpass.this, Login_Signin.class);
                                    startActivity(myintent);
                                    Log.d("Phone", "onClick: " + "code dung" + forgetPassRP.getStatus().getCode());
                                } else {
                                    Log.d("Phone", "onClick: " + "code khong dung" + forgetPassRP.getStatus().getCode());
                                    Log.d("Phone", "5");
                                }

                            } else {
                                Log.d("Phone", "onClick: " + "forgetPassRP is null");
                            }
                        }
                    });
                }
            }
        });

    }
}