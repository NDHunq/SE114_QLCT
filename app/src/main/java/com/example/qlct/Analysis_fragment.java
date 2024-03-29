package com.example.qlct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Analysis_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Analysis_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Analysis_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Analysis_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Analysis_fragment newInstance(String param1, String param2) {
        Analysis_fragment fragment = new Analysis_fragment();
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
        View view=inflater.inflate(R.layout.fragment_analysis_fragment, container, false);
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment child= new AnalysisNetIncomeFragment();
        transaction.replace(R.id.ChildFrag1,child);
        transaction.commit();

        FragmentManager fragmentManager1=getChildFragmentManager();
        FragmentTransaction transaction1=fragmentManager1.beginTransaction();
        Fragment child1= new AnalysisExpenseFragment();
        transaction1.replace(R.id.ChildFrag2,child1);
        transaction1.commit();

        FragmentManager fragmentManager2=getChildFragmentManager();
        FragmentTransaction transaction2=fragmentManager2.beginTransaction();
        Fragment child2= new AnalysisIcomeFragment();
        transaction2.replace(R.id.ChildFrag3,child2);
        transaction2.commit();
        return view;
    }
}