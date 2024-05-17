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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Utils.UserAPiUtil;

public class email_login extends AppCompatActivity {
Button next;
ImageButton back;
String id;
String phoneNumber;

    private int lastEditTextIndex = 0;
    // Tạo một mảng chứa tất cả các EditText
    EditText[] editTexts = new EditText[6];
    TextView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("phone","1");
        phoneNumber=getIntent().getStringExtra("phone");
        Log.d("phone","2");
        Log.d("phone",phoneNumber);
        next=findViewById(R.id.next);
        back=findViewById(R.id.back);
        phone=findViewById(R.id.phone);
        phone.setText(phoneNumber);

        next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UserAPiUtil userAPiUtil = new UserAPiUtil();
                                        userAPiUtil.verifyPhoneNumber(phoneNumber, id, new UserAPiUtil.OnTaskCompleted() {
                                            @Override
                                            public void onTaskCompleted(String result) {
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
        editTexts[0] = findViewById(R.id.editText1);
        editTexts[1] = findViewById(R.id.editText2);
        editTexts[2] = findViewById(R.id.editText3);
        editTexts[3] = findViewById(R.id.editText4);
        editTexts[4] = findViewById(R.id.editText5);
        editTexts[5] = findViewById(R.id.editText6);
        for(int i=0;i<6;i++)
        {
            id+=editTexts[i].getText();
        }
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