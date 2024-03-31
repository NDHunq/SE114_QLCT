package com.example.qlct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetRenewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetRenewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    TextView apply;

    public BudgetRenewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetRenewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetRenewFragment newInstance(String param1, String param2) {
        BudgetRenewFragment fragment = new BudgetRenewFragment();
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
        View view=inflater.inflate(R.layout.fragment_budget_renew, container, false);
        listView=view.findViewById(R.id.listViewRN);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Dayly");
        arrayList.add("Weekly");
        arrayList.add("Monthly");
        arrayList.add("Yearly");
        arrayList.add("Custom");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                arrayList
        );
        listView.setAdapter(adapter);
        apply=view.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tham chiếu đến MyDialogFragment
                MyDialogFragment dialogFragment = (MyDialogFragment) getParentFragment();
                if (dialogFragment != null) {
                    // Đóng dialog
                    dialogFragment.dismiss();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),arrayList.get(position),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}