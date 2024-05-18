package com.example.qlct.Analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;
import com.example.qlct.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnalysisDetailIncome extends AppCompatActivity {
    ImageView exit;
    List<AnalysisExpense> list;
    ListView listView;
    PieChart pieChart;
    ArrayList<GetAllTransactionsEntity_quyen> listTransactions;
    ArrayList<GetAllTransactionsEntity_quyen> listIncome;
    ArrayList<GetAllCategoryEntity> listCategory;
    String id_wallet;
    TextView total_icome;
    String currency;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            listTransactions = (ArrayList<GetAllTransactionsEntity_quyen>) bundle.getSerializable("listTransactions");
            id_wallet = bundle.getString("id_wallet");
            listCategory = (ArrayList<GetAllCategoryEntity>) bundle.getSerializable("listCategory");
            currency = bundle.getString("currency");
        }
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
        SetUpPieChart();
    }
    void AnhXa(){
        listView=this.findViewById(R.id.listvieww);
        pieChart=this.findViewById(R.id.piechart);
        total_icome=this.findViewById(R.id.total_income);
        list.add(new AnalysisExpense(Color.BLACK,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.YELLOW,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.RED,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.BLUE,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.GREEN,R.drawable.dish,"Food",20,2000000));
        list.add(new AnalysisExpense(Color.BLACK,R.drawable.dish,"Food",20,2000000));
    }
    void SetUpPieChart()
    {
        try{
            listIncome=new ArrayList<>();
            if(id_wallet.equals("Total")){
                for(GetAllTransactionsEntity_quyen transaction:listTransactions){
                    if(transaction.transaction_type.equals("INCOME")){
                        listIncome.add(transaction);
                    }
                }
            }
            else
                for(GetAllTransactionsEntity_quyen transaction:listTransactions){
                    if(transaction.transaction_type.equals("INCOME") && transaction.wallet_id.equals(id_wallet)){
                        listIncome.add(transaction);
                    }
                    else
                    if(transaction.transaction_type.equals("TRANSFER")&&transaction.target_wallet_id.equals(id_wallet)){
                        listIncome.add(transaction);
                    }
                }
            Log.d("Income",listIncome.size()+"");
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        Collections.sort(listIncome, new Comparator<GetAllTransactionsEntity_quyen>() {
            @Override
            public int compare(GetAllTransactionsEntity_quyen t1, GetAllTransactionsEntity_quyen t2) {
                return Float.compare(Float.parseFloat(t2.amount), Float.parseFloat(t1.amount));
            }
        });
        AdjustList(listIncome);
        total_icome.setText(formatString(String.valueOf(TotalIncome(listIncome)))+" "+currency);
        ArrayList<PieEntry> categories=new ArrayList<>();
        if(listIncome.size()==0)
        {
            categories.add(new PieEntry(100,"No income"));
        } else if (listIncome.size()<=4) {
            for(int i=0;i<listIncome.size();i++)
            {
                if(listIncome.get(i).category_id!=null)
                    categories.add(new PieEntry(Float.parseFloat(listIncome.get(i).amount),getCategoryNameById(listIncome.get(i).category_id)));
                else
                    categories.add(new PieEntry(Float.parseFloat(listIncome.get(i).amount),"Transfer"));
            }
        }
        else {
            for(int i=0;i<4;i++)
            {
                if(listIncome.get(i).category_id!=null)
                    categories.add(new PieEntry(Float.parseFloat(listIncome.get(i).amount),getCategoryNameById(listIncome.get(i).category_id)));
                else
                    categories.add(new PieEntry(Float.parseFloat(listIncome.get(i).amount),"Transfer"));
            }
            float sum=0;
            for(int i=4;i<listIncome.size();i++)
            {
                sum+=Float.parseFloat(listIncome.get(i).amount);
            }
            categories.add(new PieEntry(sum,"Others"));
        }

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
        pieChart.animate();
        pieChart.setUsePercentValues(true);
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
    void AdjustList(ArrayList<GetAllTransactionsEntity_quyen> listIncome)
    {
        for(int i=0;i<listIncome.size();i++)
        {
            if(listIncome.get(i).category_id!=null)
                for(int j=i+1;j<listIncome.size();j++)
                {
                    if(listIncome.get(i).category_id.equals(listIncome.get(j).category_id))
                    {
                        listIncome.get(i).amount=String.valueOf(Float.parseFloat(listIncome.get(i).amount)+Float.parseFloat(listIncome.get(j).amount));
                        listIncome.remove(j);
                        j--;
                    }
                }
            else
                for(int j=i+1;j<listIncome.size();j++)
                {
                    if(listIncome.get(j).transaction_type.equals("TRANSFER"))
                    {
                        listIncome.get(i).amount=String.valueOf(Float.parseFloat(listIncome.get(i).amount)+Float.parseFloat(listIncome.get(j).amount));
                        listIncome.remove(j);
                        j--;
                    }
                }

        }
    }
    Float TotalIncome(ArrayList<GetAllTransactionsEntity_quyen> listIncome)
    {
        float sum=0;
        for(GetAllTransactionsEntity_quyen transaction:listIncome)
        {
            sum+=Float.parseFloat(transaction.amount);
        }
        return sum;
    }
    String getCategoryNameById(String id) {
        for (GetAllCategoryEntity category : listCategory) {
            if (category.id.equals(id)) {
                return category.name;
            }
        }
        return null;
    }
    public String formatString(String input) {
        String[] parts = input.split("\\.");
        StringBuilder sb = new StringBuilder(parts[0]);
        for (int i = sb.length() - 3; i > 0; i -= 3) {
            sb.insert(i, '.');
        }
        return sb.toString();
    }

}