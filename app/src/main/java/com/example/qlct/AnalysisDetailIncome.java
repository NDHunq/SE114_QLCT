package com.example.qlct;

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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AnalysisDetailIncome extends AppCompatActivity {
    ImageView exit;
    List<AnalysisExpense> list;
    ListView listView;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_detail_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        exit=this.findViewById(R.id.exit_Income);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list=new ArrayList<>();
        AnhXa();
        Analysis_Expense_Adapter adapter=new Analysis_Expense_Adapter(list,this,R.layout.analysis_expense_list_item);
        listView.setAdapter(adapter);
    }
    void AnhXa(){
        listView=this.findViewById(R.id.listvieww);
        pieChart=this.findViewById(R.id.piechart);
        list.add(new AnalysisExpense(Color.BLACK,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.YELLOW,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.RED,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.BLUE,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.GREEN,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.BLACK,R.drawable.dish,"Food",20,2000000));

        ArrayList<PieEntry> categories=new ArrayList<>();
        categories.add(new PieEntry(20,"Tiền lương"));
        categories.add(new PieEntry(30,"Tiền thưởng"));
        categories.add(new PieEntry(50,"Tiền lãi"));
        categories.add(new PieEntry(40,"Tiền bán hàng"));
        categories.add(new PieEntry(60,"Tiền khác"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFFA8AA0"));
        colors.add(Color.parseColor("#FF5DC5E3"));
        colors.add(Color.parseColor("#FF65CBB6"));
        colors.add(Color.parseColor("#FFF9DE74"));
        colors.add(Color.parseColor("#FF89D889"));



        PieDataSet pieDataSet=new PieDataSet(categories,"");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(R.color.black);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setLabel("");

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Income (%)");
        pieChart.setUsePercentValues(true);
        pieChart.animate();
        pieChart.setDrawEntryLabels(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(14f);
        legend.setTextSize(14f);
        legend.setDrawInside(false);

    }
}