package com.example.qlct;

import static com.example.qlct.R.drawable.background_bo_phai;
import static com.example.qlct.R.drawable.background_bo_trai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.TintInfo;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
public class Budget_fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView create_bud_butt, running, finish;
    ImageView bell;

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

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Notificaiton.class);
                startActivity(intent);
            }
        });
        create_bud_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),AddBudget.class));
            }
        });
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment child=new BudgetRunningFragment();
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

                FragmentManager fragmentManager2=getChildFragmentManager();
                FragmentTransaction transaction2=fragmentManager2.beginTransaction();
                Fragment child1=new BudgetRunningFragment();
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

                FragmentManager fragmentManager3=getChildFragmentManager();
                FragmentTransaction transaction3=fragmentManager3.beginTransaction();
                Fragment child2=new BudgetFinishFragment();
                transaction3.replace(R.id.budget_container,child2);
                transaction3.commit();
            }
        });

        return view;
    }
}