package com.example.qlct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.android.material.textfield.TextInputEditText;

public class Phone extends AppCompatActivity {
    String phone_convert;
TextView nextphone;
ImageButton backphone;
TextInputEditText    phone;
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
        setContentView(R.layout.activity_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backphone=findViewById(R.id.backphone);
        nextphone=findViewById(R.id.nextphone);
        phone=findViewById(R.id.phone);

        nextphone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Vui lòng nhập số điện thoại");
                    return;
                } else {
                    try {
                        Log.d("Phone", "phone: " + phone.getText().toString());
                        phone_convert = phone.getText().toString();
                    } catch (Exception e) {
                        Log.d("Phone", "Error: " + e);
                    }
                    Log.d("Phone", "phone_convert: " + phone_convert);
                    if (phone_convert.startsWith("0")) {
                        phone_convert = "+84" + phone_convert.substring(1);
                        Log.d("Phone", "phone_convert: " + phone_convert);
                    }


                    Log.d("Phone", "onClick: " + phone_convert);

                    UserAPiUtil userAPiUtil = new UserAPiUtil();
                    userAPiUtil.SendOtp(phone_convert);

                    Intent myintent = new Intent(Phone.this, forget_pass.class);
                    myintent.putExtra("phone", phone.getText().toString());
                    startActivity(myintent);
                }
            }
        });
        backphone.setOnClickListener(v -> finish());
    }
}