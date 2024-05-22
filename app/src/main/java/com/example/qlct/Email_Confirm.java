package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.qlct.API_Entity.LoginResponse;
import com.example.qlct.API_Entity.SharedDaTa;
import com.example.qlct.API_Entity.UserProfile;

public class Email_Confirm extends AppCompatActivity {
TextView next;
TextView phone;
ImageButton backemmailconfirm_next;
    private int lastEditTextIndex = 0;
    // Tạo một mảng chứa tất cả các EditText
    EditText[] editTexts = new EditText[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_confirm_next);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        next=findViewById(R.id.next);
        phone=findViewById(R.id.phone);
        UserProfile userProfile = SharedDaTa.getInstance().getUserProfile();
        String phoneNumber = userProfile.getData().getPhone_number();
        phoneNumber="0"+ phoneNumber.substring(3);
        phone.setText(phoneNumber);
        backemmailconfirm_next=findViewById(R.id.backemailconfirm_next);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myintent=new Intent(Email_Confirm.this, NewPassword.class);
//                startActivity(myintent);
//            }
//        });
        backemmailconfirm_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Email_Confirm.this, Setting.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myintent);
            }
        });
        editTexts[0] = findViewById(R.id.editText1);
        editTexts[1] = findViewById(R.id.editText2);
        editTexts[2] = findViewById(R.id.editText3);
        editTexts[3] = findViewById(R.id.editText4);
        editTexts[4] = findViewById(R.id.editText5);
        editTexts[5] = findViewById(R.id.editText6);
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