package com.example.qlct.Budget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.qlct.API_Entity.GetAllBudget;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.R;

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
    void Anhxa()
    {
        list=new ArrayList<>();
        list.add(new Budget("Food",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));
        list.add(new Budget("Food",2000000,5000,"21, March 2024","25 March 2024",R.drawable.dish));

    }
    void GetAllCategory()
    {
        listCate=new CategoryAPIUntill().getAllCategoryys();
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