package com.example.qlct.Analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class AnalysisDetailNetIncome extends AppCompatActivity {
    ListView listView;
    List<AnalysisNetIcome> arraylist;
    ImageView exit;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_detail_net_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        exit=this.findViewById(R.id.exit_netIcome);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arraylist=new ArrayList<>();
        AnhXa();
        Draw();
        Analysis_NetIncome_Adapter adapter=new Analysis_NetIncome_Adapter(arraylist,this,R.layout.analysis_net_income_list_item);
        listView.setAdapter(adapter);
    }
    void AnhXa(){
        lineChart=this.findViewById(R.id.line_chart);
        listView=this.findViewById(R.id.listvieww);
        arraylist.add(new AnalysisNetIcome(2019,1000000,20000));
        arraylist.add(new AnalysisNetIcome(2020,1000000,20000));
        arraylist.add(new AnalysisNetIcome(2021,1000000,20000));
        arraylist.add(new AnalysisNetIcome(2022,1000000,20000));
        arraylist.add(new AnalysisNetIcome(2023,1000000,20000));
        arraylist.add(new AnalysisNetIcome(2024,1000000,20000));


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