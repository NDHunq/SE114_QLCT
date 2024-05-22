package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.qlct.API_Entity.OTPResponse;
import com.example.qlct.API_Utils.UserAPiUtil;
import com.google.gson.Gson;

public class email_login extends AppCompatActivity {
TextView create;
ImageButton back;
String id;
String phoneNumber;

    private int lastEditTextIndex = 0;
    // Tạo một mảng chứa tất cả các EditText
    EditText[] editTexts = new EditText[6];
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("phone1","0");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

        Log.d("phone1","1");
        phoneNumber=getIntent().getStringExtra("phone");
        Log.d("phone1","2");

        create=findViewById(R.id.create);
        back=findViewById(R.id.back);
        phone=findViewById(R.id.phone);
        phone.setText(phoneNumber);
        if (phoneNumber.startsWith("0")) {
            phoneNumber = "84" + phoneNumber.substring(1);
        }
        Log.d("phone1",phoneNumber);
        Log.d("phone1","2.5");
        create.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {

                                            for(int i=0;i<6;i++)
                                            {
                                                Log.d("phone",editTexts[i].getText().toString());
                                                id = id + editTexts[i].getText().toString();
                                            }
                                            Log.d("phone",id);
                                        } catch (Exception e) {
                                            Log.d("phone", "Exception occurred: "+ e.toString());
                                        }
                                        Toast.makeText(email_login.this, "Create account", Toast.LENGTH_SHORT).show();
                                        Log.d("phone1","onClick called");
                                        Log.d("phone1","3");
                                        Log.d("phone1",phoneNumber);

                                        UserAPiUtil userAPiUtil = new UserAPiUtil();
                                        userAPiUtil.verifyPhoneNumber(phoneNumber, id, new UserAPiUtil.OnTaskCompleted() {
                                            @Override
                                            public void onTaskCompleted(String result) {
                                                Gson gson = new Gson();
                                                OTPResponse otpResponse = gson.fromJson(result, OTPResponse.class);
                                                if(otpResponse.getStatus().getCode()==200)
                                                {
                                                    Intent myintent = new Intent(email_login.this,Login_Signin.class);
                                                    Log.d("create","success");
                                                    myintent.putExtra("phone",phoneNumber);
                                                    startActivity(myintent);
                                                }
                                                else
                                                {
                                                    Log.d("create","fail");
                                                }
                                                // handle the result here
                                                // for example, navigate to the next screen if the verification is successful
                                            }
                                        });
                                    }
                                });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
Log.d("phone1","4");
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
Log.d("phone1","5");
// Thêm TextWatcher vào tất cả các EditText
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(textWatcher);
        }
    }
}