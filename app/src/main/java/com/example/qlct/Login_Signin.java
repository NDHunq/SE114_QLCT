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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.LoginEntity;
import com.example.qlct.API_Entity.UserProfile;
import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;
import com.example.qlct.API_Entity.SharedPrefManager;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class Login_Signin extends AppCompatActivity {

Button signin;
TextView errorinfo;
TextView errorphone;
API_Config api_config;
    EditText enterpass;
    TextInputEditText phone;
    MaterialButton login;
    ImageButton showpass;
    TextView forgetpass;

    //Unfocus EditText khi click ra ngoai
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
        setContentView(R.layout.activity_login_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            try {
                Log.d("MyApp", SharedPrefManager.getInstance(this).getMyVariable());
            }
            catch (Exception e)
            {
                Log.d("MyApp", "Error: " + e.toString());
            }

            return insets;

        });
        if (SharedPrefManager.getInstance(this).getMyVariable() != null) {
            Log.d("Login", "Token: " + SharedPrefManager.getInstance(this).getMyVariable());
            api_config = new API_Config();
            api_config.setTestLoginToken(SharedPrefManager.getInstance(this).getMyVariable());
            Log.d("Token", api_config.TEST_LOGIN_TOKEN);
            UserAPiUtil userAPiUtil = new UserAPiUtil();
//            userAPiUtil.getUserProfile(new UserAPiUtil.OnTaskCompleted() {
//                @Override
//                public void onTaskCompleted(String result) {
//                    Log.d("Profile", "response: " + result);
//                    Gson gson = new Gson();
//                    if (result == null || result.isEmpty()) {
//                        Log.d("Profile", "Result is null or empty");
//                        return;
//                    }
//                    UserProfile userProfile = gson.fromJson(result, UserProfile.class);
//                    if (userProfile == null) {
//                        Log.d("Profile", "null");
//                    } else {
//                        Log.d("Profile", "not null");
//                    }
//                    Log.d("Profile", "userProfile: " + userProfile.getStatus().getCode());
//                    if (userProfile.getStatus().getCode() == 200) {
//
//                        SharedDaTa.getInstance().setUserProfile(userProfile);
//                    } else {
//                        Log.d("Profile", "failed");
//                    }
//                }
//            });
            UserProfile userProfile=userAPiUtil.getUserProfile();

            // User is already logged in, navigate to MainActivity
            Log.d("Token", SharedPrefManager.getInstance(Login_Signin.this).getMyVariable());
            startActivity(new Intent(this, MainActivity.class));
        }
        else
    {
        Log.d("MyApp", "Token is null");
        // User is not logged in, navigate to LoginActivity
        Log.d("MyApp", "Start to login");
    }

        signin=findViewById(R.id.signin);
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());
//        showpass = findViewById(R.id.showpass);
        phone=findViewById(R.id.phone);
        login=findViewById(R.id.Login);
        forgetpass=findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Login_Signin.this, Phone.class);
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
//        showpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (enterpass.getTransformationMethod() != null) {
//                    enterpass.setTransformationMethod(null);
//                } else {
//                    enterpass.setTransformationMethod(new PasswordTransformationMethod());
//                }
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(validate())
                    {
                        String phone_convert = phone.getText().toString();
                        if (phone_convert.startsWith("0")) {
                            phone_convert = "+84" + phone_convert.substring(1);
                        }
                        LoginEntity loginEntity = new LoginEntity(phone_convert, enterpass.getText().toString());
                        //Gọi API login
                        UserAPiUtil userAPiUtil = new UserAPiUtil();

                        userAPiUtil.Login(loginEntity, new UserAPiUtil.OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String result) {
                                Log.d("Login","1");

                                // Handle the result here
                                Log.d("Login", "response: " + result);
                                Log.d("Login", "2");
                                Gson gson = new Gson();
                                // Kiểm tra nếu result là null hoặc trống
                                if (result == null || result.isEmpty()) {
                                    Log.d("Login", "Result is null or empty");
                                    return;
                                }
                                Log.d("Login", "excute login");
                                Log.d("Login", "3");
                                LoginResponse loginResponse = gson.fromJson(result, LoginResponse.class);
                                if(loginResponse==null)
                                {
                                    Log.d("Login","null");
                                }
                                else
                                {
                                    Log.d("Login","not null");
                                }
                                Log.d("Login", "loginResponse: " + loginResponse.getStatus().getCode());
                                Log.d("Login", "4");

                                if (validateStatusCode(loginResponse.getStatus().getCode())) {
                                    SharedPrefManager.getInstance(Login_Signin.this).saveMyVariable(loginResponse.getData().getToken());
                                    api_config = new API_Config();
                                    api_config.setTestLoginToken(SharedPrefManager.getInstance(Login_Signin.this).getMyVariable());
                                    UserAPiUtil userAPiUtil = new UserAPiUtil();
//                                userAPiUtil.getUserProfile(new UserAPiUtil.OnTaskCompleted() {
//                                    @Override
//                                    public void onTaskCompleted(String result) {
//                                        Log.d("Profile", "response: " + result);
//                                        Gson gson = new Gson();
//                                        if (result == null || result.isEmpty()) {
//                                            Log.d("Profile", "Result is null or empty");
//                                            return;
//                                        }
//                                        UserProfile userProfile = gson.fromJson(result, UserProfile.class);
//                                        if (userProfile == null) {
//                                            Log.d("Profile", "null");
//                                        } else {
//                                            Log.d("Profile", "not null");
//                                        }
//                                        Log.d("Profile", "userProfile: " + userProfile.getStatus().getCode());
//                                        if (userProfile.getStatus().getCode() == 200) {
//                                            SharedDaTa.getInstance().setUserProfile(userProfile);
//                                        } else {
//                                            Log.d("Profile", "failed");
//                                        }
//                                    }
//                                });
                                    Intent myintent = new Intent(Login_Signin.this, MainActivity.class);
                                    startActivity(myintent);
                                } else {
                                    Log.d("login", "failed");
                                    Toast.makeText(Login_Signin.this, "Error(s) has occured!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(Login_Signin.this, "Error(s) has occured!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Log.d("Login", "Error: " + e.toString());
                }

                TextInputEditText phone = findViewById(R.id.phone);
                TextInputEditText enterpass = findViewById(R.id.enterpass);

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

                enterpass.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        validatePassword();
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
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

    private boolean validatePassword(){
        TextInputLayout passwordLayout = findViewById(R.id.enterpass_layout);
        TextInputEditText password = findViewById(R.id.enterpass);

        if (!password.getText().toString().equals("")) {
            passwordLayout.setError(null);
            password.setTextColor(getResources().getColor(R.color.xanhnen, null));
            return true;
        } else {
            passwordLayout.setError("Password is empty!");
            password.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }
    }

    private boolean validateStatusCode(int statusCode){
        TextInputLayout phoneLayout = findViewById(R.id.phone_layout);
        TextInputEditText phone = findViewById(R.id.phone);
        TextInputLayout passwordLayout = findViewById(R.id.enterpass_layout);
        TextInputEditText password = findViewById(R.id.enterpass);

        if(statusCode == 200){
            phoneLayout.setError(null);
            phone.setTextColor(getResources().getColor(R.color.xanhnen, null));
            passwordLayout.setError(null);
            password.setTextColor(getResources().getColor(R.color.xanhnen, null));
            return true;
        }
        else{
            phoneLayout.setError("Invalid phone number!");
            phone.setTextColor(getResources().getColor(R.color.errorColor, null));
            passwordLayout.setError("Invalid password!");
            password.setTextColor(getResources().getColor(R.color.errorColor, null));
            return false;
        }


    }

    private boolean validate(){
        boolean phoneValid = validatePhoneNumber();
        boolean passwordValid = validatePassword();
        return phoneValid && passwordValid;
    }
}