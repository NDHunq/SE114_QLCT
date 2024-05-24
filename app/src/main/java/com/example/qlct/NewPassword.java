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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.ChangePassWord;
import com.example.qlct.API_Entity.SharedDaTa;
import com.example.qlct.API_Entity.SharedPrefManager;
import com.example.qlct.API_Entity.UserProfile;
import com.example.qlct.API_Utils.UserAPiUtil;

public class NewPassword extends AppCompatActivity {
    UserAPiUtil userAPiUtil = new UserAPiUtil();
    UserProfile userProfile=userAPiUtil.getUserProfile();

ImageButton backnewpass;
String pass=userProfile.getData().getPassword();
TextView errorpass;
TextView errorfill;
EditText enterpass;
TextView errorcurrentpass;
    EditText currentpass;
EditText repeatpass;
ImageButton showpass;
ImageButton showpass1;
TextView save;
    ImageButton showpass2;
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
                Intent myintent = new Intent(NewPassword.this, Setting.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Thêm cờ này
                Log.d("NewPass", "back to setting ");
                startActivity(myintent);
            }
        });
        errorcurrentpass=findViewById(R.id.errorcurrentpass);
        errorpass=findViewById(R.id.errorpass);
        errorfill=findViewById(R.id.errorfill);
        save=findViewById(R.id.save);
        currentpass=findViewById(R.id.currentpass);
        currentpass.setTransformationMethod(new PasswordTransformationMethod());
        enterpass=findViewById(R.id.enterpass);
        enterpass.setTransformationMethod(new PasswordTransformationMethod());

        repeatpass=findViewById(R.id.repeatpass);
        repeatpass.setTransformationMethod(new PasswordTransformationMethod());
        save.setOnClickListener(new View.OnClickListener() {
            int flag1=1;
           int flag=1;
           int flag2=1;
            @Override
            public void onClick(View v) {
                if(!currentpass.getText().toString().equals(pass))
                {
                    flag2=0;
                    errorcurrentpass.setText("Current password is incorrect");
                    Log.d("newpass", "pass sai "+flag2);
                }
                else
                {
                    flag2=1;
                    errorcurrentpass.setText("");
                    Log.d("newpass", "pass dung "+flag2);
                }
                if(currentpass.getText().toString().isEmpty()||enterpass.getText().toString().isEmpty()||repeatpass.getText().toString().isEmpty())
                {
                    flag=0;
                    errorfill.setText("Please fill all the fields");
                    Log.d("newpass", "thong tin trong "+flag);
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

                ChangePassWord changePassWord = new ChangePassWord(currentpass.getText().toString(),enterpass.getText().toString());
                if(flag==1&&flag1==1&&flag2==1  )
                {

                    errorfill.setText("");
                    errorpass.setText("");
                    userAPiUtil.changePassword(changePassWord, new UserAPiUtil.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            Log.d("NewPass", "response");
                        }
                });
                    try {
                        SharedPrefManager.getInstance(NewPassword.this).saveMyVariable(null);
                        Intent myintent = new Intent(NewPassword.this, Login_Signin.class);
                        startActivity(myintent);
                    } catch (Exception e) {
                        // Log lỗi hoặc xử lý ngoại lệ tại đây
                        Log.d("ErrorPassword", "Error: ", e);
                    }
                }

            }
        });

    }
}