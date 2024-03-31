package com.example.qlct;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisNetIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisNetIncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView see_detail;
    LineChart lineChart;

    public AnalysisNetIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisNetIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisNetIncomeFragment newInstance(String param1, String param2) {
        AnalysisNetIncomeFragment fragment = new AnalysisNetIncomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_analysis_net_income, container, false);
        see_detail=view.findViewById(R.id.see_detail_net_income);
        lineChart=view.findViewById(R.id.line_chart);
        see_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AnalysisDetailNetIncome.class);
                startActivity(intent);
            }
        });
        Draw();

        return view;
    }
    public void Draw()
    {
        // Data for line 1
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(1, 1000000));
        entries1.add(new Entry(2, 2000000));
        entries1.add(new Entry(3, 1500000));
        entries1.add(new Entry(4, 1800000));
        entries1.add(new Entry(5, 2100000));
        entries1.add(new Entry(6, 2200000));
        entries1.add(new Entry(7, 2300000));
        entries1.add(new Entry(8, 1800000));
        entries1.add(new Entry(9, 2000000));
        entries1.add(new Entry(10, 1900000));
        entries1.add(new Entry(11, 1600000));
        entries1.add(new Entry(12, 1500000));


        // Data for line 2
        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1, 1200000));
        entries2.add(new Entry(2, 1300000));
        entries2.add(new Entry(3, 1400000));
        entries2.add(new Entry(4, 1500000));
        entries2.add(new Entry(5, 1304000));
        entries2.add(new Entry(6, 2000000));
        entries2.add(new Entry(7, 1800000));
        entries2.add(new Entry(8, 1800000));
        entries2.add(new Entry(9, 1700000));
        entries2.add(new Entry(10, 1600000));
        entries2.add(new Entry(11, 1750000));
        entries2.add(new Entry(12, 1300000));

        LineDataSet dataSet1 = new LineDataSet(entries1, "Income");
        dataSet1.setColor(Color.parseColor("#177715")); // Set the line color
        dataSet1.setDrawValues(false);

        LineDataSet dataSet2 = new LineDataSet(entries2, "Expense");
        dataSet2.setColor(Color.parseColor("#FF0000")); // Set the line color
        dataSet2.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSet1);
        lineData.addDataSet(dataSet2);

        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
// Create a custom ValueFormatter
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value); // Convert float value to year string
            }
        };

// Apply the formatter to the x-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
// Disable right y-axis
        lineChart.getAxisRight().setEnabled(false);
//Annotation
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(14f);
        legend.setFormSize(14f);

        lineChart.invalidate();
    }
}