package com.example.qlct.Budget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qlct.R;

import java.util.ArrayList;
import java.util.List;

public class BudgetRunningFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListView listView;
    List<Budget> list;
    TextView day,week,month,year,future;

    public BudgetRunningFragment() {
    }
    public static BudgetRunningFragment newInstance(String param1, String param2) {
        BudgetRunningFragment fragment = new BudgetRunningFragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_budget_running, container, false);
        listView=view.findViewById(R.id.listviewRunning);
        Anhxa(view);
        Budget_adapter adapter=new Budget_adapter(getContext(),R.layout.budget_list_item,list);
        listView.setAdapter(adapter);
        return view;
    }
    void Anhxa(View view){
        day=view.findViewById(R.id.day);
        week=view.findViewById(R.id.week);
        month=view.findViewById(R.id.month);
        year=view.findViewById(R.id.year);
        future=view.findViewById(R.id.future);
        list=new ArrayList<>();
        list.add(new Budget("Food","Vi1",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food","Vi1",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food","Vi1",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food","Vi1",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food","Vi1",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                future.setBackground(null);
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                day.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                future.setBackground(null);
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                day.setBackground(null);
                year.setBackground(null);
                future.setBackground(null);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                month.setBackground(null);
                day.setBackground(null);
                future.setBackground(null);
            }
        });
        future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                day.setBackground(null);
            }
        });

    }
}