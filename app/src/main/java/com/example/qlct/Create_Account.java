package com.example.qlct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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

import com.example.qlct.API_Entity.RegisterEntity;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Create_Account extends AppCompatActivity {
TextView nextcreateaccount;
TextInputEditText phone;
    TextInputEditText username;
TextView errorphone;
int flag=1;
TextView errorpass;
    EditText enterpass;
    EditText repeatpass;
    ImageButton showpass;
    ImageButton showpass1;
    ImageButton back;
    TextView errorinfo;
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
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nextcreateaccount=findViewById(R.id.nextcreateacount);
        phone=findViewById(R.id.phone);
        username=findViewById(R.id.username);
        back=findViewById(R.id.back);
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());

        repeatpass=findViewById(R.id.repeatpass);
        repeatpass.setTransformationMethod(new PasswordTransformationMethod());
        nextcreateaccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (username.getText().toString().isEmpty() ||
//                        phone.getText().toString().isEmpty() ||
//                        enterpass.getText().toString().isEmpty() ||
//                        repeatpass.getText().toString().isEmpty()) {
//                    flag = 0;
//                    errorinfo.setText("Please fill in all the information");
//                }
//                if (enterpass.getText().toString().equals(repeatpass.getText().toString())) {
//                    errorpass.setText("");
//                }
//                else {
//                    flag = 0;
//                    errorpass.setText("Password does not match");
//
//                }
                if (validate()) {
//                    if (flag == 1) {
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
                    //}
                }
                else
                {
                    Toast.makeText(Create_Account.this, "Error(s) has occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUser();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enterpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
                validateRepeatPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        repeatpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateRepeatPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean validatePhoneNumber(){
        TextInputLayout phoneLayout = findViewById(R.id.phone_layout);
        TextInputEditText phone = findViewById(R.id.phone);

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

    private boolean validateUser(){
        TextInputLayout userLayout = findViewById(R.id.username_layout);
        TextInputEditText user = findViewById(R.id.username);

        if(!user.getText().toString().equals("")){
            userLayout.setError(null);
            user.setTextColor(getResources().getColor(R.color.xanhnen, null));
            return true;
        }
        else{
            userLayout.setError("Username is empty!");
            user.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }
    }

    private boolean validatePassword(){
        TextInputLayout passLayout = findViewById(R.id.enterpass_layout);
        TextInputEditText pass = findViewById(R.id.enterpass);

        if (pass.getText().toString().equals("")){
            passLayout.setError("Password is empty!");
            pass.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }
        else{
            passLayout.setError(null);
            pass.setTextColor(getResources().getColor(R.color.xanhnen, null));
            return true;
        }
    }

    private boolean validateRepeatPassword(){
        TextInputLayout repeatLayout = findViewById(R.id.repeatpass_layout);
        TextInputEditText repeat = findViewById(R.id.repeatpass);
        TextInputLayout passLayout = findViewById(R.id.enterpass_layout);
        TextInputEditText pass = findViewById(R.id.enterpass);

        if (!repeat.getText().toString().equals("")){
            if(!repeat.getText().toString().equals(pass.getText().toString())){
                repeatLayout.setError("Password does not match!");
                repeat.setTextColor(getResources().getColor(R.color.errorColor, null));
                return false;
            }
            repeatLayout.setError(null);
            repeat.setTextColor(getResources().getColor(R.color.xanhnen, null));
            return true;
        }
        else{
            repeatLayout.setError("Repeat password is empty!");
            repeat.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }
    }

    private boolean validate(){
        boolean phoneValid = validatePhoneNumber();
        boolean userValid = validateUser();
        boolean passValid = validatePassword();
        boolean repeatValid = validateRepeatPassword();
        return phoneValid && userValid && passValid && repeatValid;
    }
}