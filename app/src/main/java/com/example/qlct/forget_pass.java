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

import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.OTPResponse;
import com.example.qlct.API_Entity.SharedDaTa;
import com.example.qlct.API_Entity.UserProfile;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.gson.Gson;

public class forget_pass extends AppCompatActivity {
    TextView error;
    TextView phone;
    ImageButton back;
    TextView next;
    String id;
    private int lastEditTextIndex = 0;
    // Tạo một mảng chứa tất cả các EditText
    EditText[] editTexts = new EditText[6];
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
        setContentView(R.layout.activity_forget_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        error=findViewById(R.id.error);
        editTexts[0] = findViewById(R.id.editText1);
        editTexts[1] = findViewById(R.id.editText2);
        editTexts[2] = findViewById(R.id.editText3);
        editTexts[3] = findViewById(R.id.editText4);
        editTexts[4] = findViewById(R.id.editText5);
        editTexts[5] = findViewById(R.id.editText6);
        id="";
        try {
            editTexts[0] = findViewById(R.id.editText1);
            editTexts[1] = findViewById(R.id.editText2);
            editTexts[2] = findViewById(R.id.editText3);
            editTexts[3] = findViewById(R.id.editText4);
            editTexts[4] = findViewById(R.id.editText5);
            editTexts[5] = findViewById(R.id.editText6);

        } catch (Exception e) {
            Log.d("phone", "Exception occurred: "+ e.toString());
        }
         phone=findViewById(R.id.phone);
        // Get the Intent that started this activity
        Intent intent = getIntent();

// Extract the phone number string from the Intent
        String phoneNumber = intent.getStringExtra("phone");

// Set the phone number to the TextView
        phone.setText(phoneNumber);
//        UserProfile userProfile = SharedDaTa.getInstance().getUserProfile();
//        String phoneNumber = userProfile.getData().getPhone_number();


        next=findViewById(R.id.next);
        back=findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if(editTexts[0].getText().toString().isEmpty()||editTexts[1].getText().toString().isEmpty()||editTexts[2].getText().toString().isEmpty()||editTexts[3].getText().toString().isEmpty()||editTexts[4].getText().toString().isEmpty()||editTexts[5].getText().toString().isEmpty())
                {
                    error.setText("Please fill all the fields");

                }
                else {
                    error.setText("");
                    try {

                        for(int i=0;i<6;i++)
                        {
                            Log.d("phone",editTexts[i].getText().toString());
                            id = id + editTexts[i].getText().toString();
                        }
                        Log.d("OTP",id);
                    } catch (Exception e) {
                        Log.d("OTP", "Exception occurred: "+ e.toString());
                    }
                    Intent myintent=new Intent(forget_pass.this, forget_setpass.class);
                    myintent.putExtra("OTP",id);
                    myintent.putExtra("phone",phoneNumber);
                    startActivity(myintent);
                }

            }


        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < editTexts.length - 1; i++) {
                    if (editTexts[i].getText().length() == 1 && i == lastEditTextIndex) {
                        editTexts[i + 1].requestFocus();
                        lastEditTextIndex = i + 1;
                        break;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không làm gì ở đây
            }
        };

// Thêm TextWatcher vào tất cả các EditText
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }
    }

}