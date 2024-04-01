package com.example.qlct;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    RadioButton month,year;
    ImageView back,next ;
    TextView date;
    ImageView bell;
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
    final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());


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

        AnhXa(view);

        return view;
    }

    public void AnhXa(View view) {
        month=view.findViewById(R.id.month);
        month.setChecked(true);
        year=view.findViewById(R.id.year);
        back=view.findViewById(R.id.back);
        next=view.findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        date = view.findViewById(R.id.date);
        bell = view.findViewById(R.id.bell);

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Notificaiton.class);
                startActivity(intent);
            }
        });
        month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    date.setText(monthFormat.format(Calendar.getInstance().getTime()));
                    next.setVisibility(View.INVISIBLE);
                    calendar.setTime(Calendar.getInstance().getTime());
                }
            }
        });

        year.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    date.setText(yearFormat.format(Calendar.getInstance().getTime()));
                    next.setVisibility(View.INVISIBLE);
                    calendar.setTime(Calendar.getInstance().getTime());
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month.isChecked()) {
                    calendar.add(Calendar.MONTH, 1);
                    date.setText(monthFormat.format(calendar.getTime()));
                    if ((calendar.getTime().getMonth()==(Calendar.getInstance().getTime().getMonth()))&&(calendar.getTime().getYear()==(Calendar.getInstance().getTime().getYear()))) {
                        next.setVisibility(View.INVISIBLE);
                    }

                } else if (year.isChecked()) {
                    calendar.add(Calendar.YEAR, 1);
                    date.setText(yearFormat.format(calendar.getTime()));
                    if (calendar.getTime().getYear()==(Calendar.getInstance().getTime().getYear())) {
                        next.setVisibility(View.INVISIBLE);
                    }
                }

                // If the new date is equal to or after the current date, hide the next button

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Update the date TextView
                if (month.isChecked()) {
                    calendar.add(Calendar.MONTH, -1);
                    date.setText(monthFormat.format(calendar.getTime()));
                } else if (year.isChecked()) {
                    calendar.add(Calendar.YEAR, -1);
                    date.setText(yearFormat.format(calendar.getTime()));
                }

                next.setVisibility(View.VISIBLE);
            }
        });
    }
}