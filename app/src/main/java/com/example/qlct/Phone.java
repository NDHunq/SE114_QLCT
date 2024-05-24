package com.example.qlct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Phone extends AppCompatActivity {
    String phone_convert;
TextView nextphone;
ImageButton backphone;
TextInputEditText    phone;

TextInputLayout phoneLayout;
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
        phoneLayout=findViewById(R.id.phone_layout);

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePhoneNumber();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nextphone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                if (!validatePhoneNumber()) {
                    Toast.makeText(Phone.this, "Error(s) has occurred", Toast.LENGTH_SHORT).show();
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

    private boolean validatePhoneNumber(){
        phoneLayout = findViewById(R.id.phone_layout);
        phone = findViewById(R.id.phone);

        if(!phone.getText().toString().equals("")){
            if(phone.length()>=10&&phone.length()<=11&&phone.getText().toString().matches("[0-9]+"))
            {
                //errorphone.setText("");
                phoneLayout.setError(null);
                phone.setTextColor(getResources().getColor(R.color.xanhnen, null));
                return true;
            }
            phoneLayout.setError("Invalid phone number!");
            phone.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }
        else{
            phoneLayout.setError("Phone number is empty!");
            phone.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }
    }
}