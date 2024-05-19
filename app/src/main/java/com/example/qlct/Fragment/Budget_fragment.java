package com.example.qlct.Fragment;

import static com.example.qlct.R.drawable.background_bo_phai;
import static com.example.qlct.R.drawable.background_bo_trai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.qlct.API_Entity.GetAllBudget;
import com.example.qlct.API_Utils.BudgetAPIUtil;
import com.example.qlct.Budget.AddBudget;
import com.example.qlct.Budget.BudgetFinishFragment;
import com.example.qlct.Budget.BudgetRunningFragment;
import com.example.qlct.Notification.Notificaiton_activity;
import com.example.qlct.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Budget_fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView create_bud_butt, running, finish;
    ImageView bell;
    LinearLayout running_budget;
    ArrayList<GetAllBudget> allBudgets = new ArrayList<>();
    TextView remaining;
    TextView total_spent;
    TextView total_budget;
    SeekBar seekBar;
    public Budget_fragment() {
        // Required empty public constructor
    }
    public static Budget_fragment newInstance(String param1, String param2) {
        Budget_fragment fragment = new Budget_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_budget_fragment, container, false);
        create_bud_butt=view.findViewById(R.id.create_bud_butt);
        running=view.findViewById(R.id.running);
        finish=view.findViewById(R.id.finish);
        bell = view.findViewById(R.id.bell);
        remaining=view.findViewById(R.id.remaining);
        total_spent=view.findViewById(R.id.total_spent);
        total_budget=view.findViewById(R.id.total_budget);
        running_budget=view.findViewById(R.id.running_budget);
        seekBar=view.findViewById(R.id.seekBar);
        //CALL API
        allBudgets=new BudgetAPIUtil().getAllBudgets();
        if(allBudgets==null){
            Log.d("Budget","null");
        }
        else
            Log.d("Budget",allBudgets.size()+"");
        double totalBudget=0;
        double totalSpent=0;
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            for (int i = 0; i < allBudgets.size(); i++) {
                totalBudget += Double.parseDouble(allBudgets.get(i).getLimit_amount());
                totalSpent += Double.parseDouble(allBudgets.get(i).getExpensed_amount());
            }
            total_budget.setText(df.format(totalBudget));
            total_spent.setText(df.format(totalSpent));
            remaining.setText(df.format(totalBudget - totalSpent));
            seekBar.setMax((int) totalBudget);
            seekBar.setProgress((int) totalSpent);
            seekBar.setEnabled(false);
        } catch (Exception e){
            Log.d("Budget",e.getMessage());
        }
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notificaiton_activity.class);
                startActivity(intent);
            }
        });
        create_bud_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AddBudget.class));
            }
        });
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment child=new BudgetRunningFragment(allBudgets);
        transaction.replace(R.id.budget_container,child);
        transaction.commit();
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running.setBackgroundTintList(null);
                running.setBackgroundResource(background_bo_trai);
                finish.setBackgroundResource(background_bo_phai);
                finish.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ACACAC")));
                running.setTextColor(Color.parseColor("#1EABED"));
                finish.setTextColor(Color.BLACK);
                running_budget.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager2=getChildFragmentManager();
                FragmentTransaction transaction2=fragmentManager2.beginTransaction();
                Fragment child1=new BudgetRunningFragment(allBudgets);
                transaction2.replace(R.id.budget_container,child1);
                transaction2.commit();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running.setBackgroundResource(background_bo_trai);
                finish.setBackgroundTintList(null);
                finish.setBackgroundResource(background_bo_phai);
                running.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ACACAC")));
                finish.setTextColor(Color.parseColor("#1EABED"));
                running.setTextColor(Color.BLACK);
                running_budget.setVisibility(View.GONE);

                FragmentManager fragmentManager3=getChildFragmentManager();
                FragmentTransaction transaction3=fragmentManager3.beginTransaction();
                Fragment child2=new BudgetFinishFragment(allBudgets);
                transaction3.replace(R.id.budget_container,child2);
                transaction3.commit();
            }
        });

        return view;
    }
}