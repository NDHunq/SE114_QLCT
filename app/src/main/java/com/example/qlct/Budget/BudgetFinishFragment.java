package com.example.qlct.Budget;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.qlct.API_Entity.GetAllBudget;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFinishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFinishFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    List<Budget> list;
    ArrayList<GetAllBudget> allBudgets = new ArrayList<>();
    ArrayList<GetAllCategoryy> listCate;
    public BudgetFinishFragment() {
        // Required empty public constructor
    }

    public BudgetFinishFragment( ArrayList<GetAllBudget> allBudgets) {
        this.allBudgets = allBudgets;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFinishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFinishFragment newInstance(String param1, String param2) {
        BudgetFinishFragment fragment = new BudgetFinishFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_budget_finish, container, false);
        listView=view.findViewById(R.id.listviewFinish);
        GetAllCategory();
        Anhxa();
        Budget_adapter adapter=new Budget_adapter(getContext(),R.layout.budget_list_item,list);
        listView.setAdapter(adapter);
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void Anhxa()
    {
        list=new ArrayList<>();
        String from="";
        String to="";
        if(allBudgets != null)
            for(int i=0;i<allBudgets.size();i++){
                if(allBudgets.get(i).getBudget_type().equals("NO_RENEW")) {
                    if (allBudgets.get(i).getNo_renew_date_unit().equals("DAY") ) {
                        from = "";
                        to = allBudgets.get(i).getNo_renew_date();
                        if(!DayIsBeforeNow(to))
                            break;
                    }
                    else {
                        String[] dates = allBudgets.get(i).getNo_renew_date().split(" ");
                        if(dates.length == 2) {
                            from ="From: "+ dates[0];
                            to = "To: "+dates[1];
                            if(!DayIsBeforeNow(dates[1]))
                                break;
                        }
                    }
                    Budget budget = new Budget(GetNameCategory(allBudgets.get(i).getCategory_id()),Double.valueOf(allBudgets.get(i).getLimit_amount()) ,Double.valueOf(allBudgets.get(i).getExpensed_amount()) ,from,to,allBudgets.get(i).getCategory().getPicture(),allBudgets.get(i).getBudget_type(),allBudgets.get(i).getId(),allBudgets.get(i).getCurrency_unit());
                    list.add(budget);
                    Log.d("Finishhhh", "Budget: " + budget.toString());
                }
            }
        else{
            Log.d("BudgetRunningFragment", "allBudgets is null");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    boolean DayIsBeforeNow(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate inputDate = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();

        return inputDate.isBefore(currentDate);
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
    String GetIDCategory(String name)
    {
        for(int i=0;i<listCate.size();i++)
        {
            if(listCate.get(i).getName().equals(name))
            {
                return listCate.get(i).getId();
            }
        }
        return "";
    }

}