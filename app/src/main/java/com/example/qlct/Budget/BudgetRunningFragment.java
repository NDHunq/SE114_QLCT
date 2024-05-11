package com.example.qlct.Budget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qlct.API_Entity.GetAllBudget;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Utils.CategoryAPIUntill;
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
    ArrayList<GetAllBudget> allBudgets = new ArrayList<>();
    ArrayList<GetAllCategoryy> listCate;
    public BudgetRunningFragment() {
    }
    public BudgetRunningFragment(ArrayList<GetAllBudget> allBudgets) {
        this.allBudgets = allBudgets;
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
        GetAllCategory();
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
        String from="";
        String to="";
        if(allBudgets != null)
            for(int i=0;i<allBudgets.size();i++){
                if(allBudgets.get(i).getBudget_type().equals("NO_RENEW")) {
                    if (allBudgets.get(i).getNo_renew_date_unit().equals("DAY") ) {
                        from = "";
                        to = allBudgets.get(i).getNo_renew_date();
                    }
                    else {
                        String[] dates = allBudgets.get(i).getNo_renew_date().split(" ");
                        if(dates.length == 2) {
                            from ="From: "+ dates[0];
                            to = "To: "+dates[1];
                        }
                    }
                }
                else {
                    if(allBudgets.get(i).getRenew_date_unit().equals("Custom"))
                    {
                        from="Renew at "+"\n"+allBudgets.get(i).getCustom_renew_date().substring(0,10);
                        to="";
                    }
                    else {
                        from="From: "+allBudgets.get(i).getCreate_at().substring(0,10);
                        to="Renew "+allBudgets.get(i).getRenew_date_unit();
                    }

                }
                Budget budget = new Budget(GetNameCategory(allBudgets.get(i).getCategory_id()),Double.valueOf(allBudgets.get(i).getLimit_amount()) ,Double.valueOf(allBudgets.get(i).getExpensed_amount()) ,from,to,allBudgets.get(i).getCategory().getPicture());
                    list.add(budget);
            }
        else{
            Log.d("BudgetRunningFragment", "allBudgets is null");
        }
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
    void GetAllCategory()
    {
        listCate=new CategoryAPIUntill().getAllCategoryys();
    }
    String GetNameCategory(String id) {
        for(int i = 0; i < listCate.size(); i++) {
            if(listCate.get(i).getId().equals(id)) {
                return listCate.get(i).getName();
            }
        }
        return "";
    }
}