package com.example.qlct.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.qlct.Budget.BudgetRenewFragment;
import com.example.qlct.Budget.BugetNoRenewFragment;
import com.example.qlct.R;

public class MyDialogFragment extends DialogFragment {
    TextView noRenew;
    TextView renew;
    TextView date;
    TextView apply;
    Activity parent;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);

        noRenew = dialog.findViewById(R.id.no_renew);
        renew = dialog.findViewById(R.id.renew);
        BugetNoRenewFragment fragment = new BugetNoRenewFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.container_renew, fragment).commit();

        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetRenewFragment fragment = new BudgetRenewFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.container_renew, fragment).commit();
                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#ACACAC"));
                noRenew.setBackgroundTintList(colorStateList);
                noRenew.setTextColor(Color.BLACK);
                renew.setBackgroundTintList(null);
                renew.setTextColor(Color.parseColor( "#1EABED"));


            }
        });
        noRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BugetNoRenewFragment fragment = new BugetNoRenewFragment();
                getChildFragmentManager().beginTransaction().replace(R.id.container_renew, fragment).commit();
                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#ACACAC"));
                renew.setBackgroundTintList(colorStateList);
                renew.setTextColor(Color.BLACK);
                noRenew.setBackgroundTintList(null);
                noRenew.setTextColor(Color.parseColor( "#1EABED"));
            }
        });

        return dialog;
    }
}