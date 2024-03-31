package com.example.qlct;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBudget extends AppCompatActivity implements OnDataPass {
    ImageView date_picker;
    TextView apply;
    Dialog dialog ;
    TextView renew;
    TextView noRenew;
    FrameLayout frameLayout;
    TextView date;

    private TextView exit_budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        date_picker=this.findViewById(R.id.date_pickerr);
        exit_budget=this.findViewById(R.id.exit_budget);
        date=this.findViewById(R.id.date);

        exit_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

    }
    void ShowDialog()
    {
           MyDialogFragment dialogFragment;
           dialogFragment = new MyDialogFragment();
           dialogFragment.show(getSupportFragmentManager(), "tag");

    }

    public void onDataPass(String data) {
        date.setText(data);
    }

}
