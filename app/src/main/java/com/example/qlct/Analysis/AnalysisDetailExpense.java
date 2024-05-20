package com.example.qlct.Analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AnalysisDetailExpense extends AppCompatActivity {
    ImageView exit;
    List<AnalysisExpense> list;
    ListView listView;
    PieChart pieChart;
    ArrayList<GetAllTransactionsEntity_quyen> listTransactions;
    ArrayList<GetAllTransactionsEntity_quyen> listExpense;
    ArrayList<GetAllCategoryEntity> listCategory;
    String id_wallet;
    TextView total_expense;
    String currency;
    ArrayList<Integer> colors;
    String date;
    TextView date_lbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_detail_expense);
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
            date = bundle.getString("date");
        }
        exit=this.findViewById(R.id.exit_expense);
        listView=this.findViewById(R.id.listvieww);
        total_expense=this.findViewById(R.id.total_expense);
        pieChart=this.findViewById(R.id.piechart);
        date_lbl=this.findViewById(R.id.date_lbl);
        date_lbl.setText(date);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list=new ArrayList<>();
        SetUpPieChart();
        AnhXa();
        Analysis_Expense_Adapter adapter=new Analysis_Expense_Adapter(list,this,R.layout.analysis_expense_list_item);
        listView.setAdapter(adapter);
    }
    void AnhXa(){
        for(int i = 0; i< listExpense.size(); i++)
        {
            int color;
            int hinh=R.drawable.dish;
            if(i<=4)
            {
                color=colors.get(i);
            }
            else
            {
                color=colors.get(4);
            }
            if(listExpense.get(i).category_id!=null)
            {
                list.add(new AnalysisExpense(color,hinh,getCategoryNameById(listExpense.get(i).category_id),roundTwoDecimals(Float.parseFloat(listExpense.get(i).amount)/ TotalExpense(listExpense)*100),formatString(listExpense.get(i).amount),currency));
            }
            else
            {
                list.add(new AnalysisExpense(color,hinh,"Transfer",roundTwoDecimals(Float.parseFloat(listExpense.get(i).amount)/ TotalExpense(listExpense)*100),formatString(listExpense.get(i).amount),currency));
            }
        }


    }
    void SetUpPieChart()
    {
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String[] dateParts = date.split("-");
            int month=-1,year;
            if(date.length()==4)
                year=Integer.parseInt(date);
            else
            {
                month=Integer.parseInt(dateParts[0]);
                year=Integer.parseInt(dateParts[1]);
            }

            listExpense =new ArrayList<>();
            if(id_wallet.equals("Total")){
                for(GetAllTransactionsEntity_quyen transaction:listTransactions){
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionYear = calendar.get(Calendar.YEAR);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1;
                    if(transaction.transaction_type.equals("EXPENSE") && transactionYear==year && (month==-1 || transactionMonth==month)){
                        listExpense.add(transaction);
                    }
                }
            }
            else
                for(GetAllTransactionsEntity_quyen transaction:listTransactions){
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionYear = calendar.get(Calendar.YEAR);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1;
                    if(transaction.transaction_type.equals("EXPENSE") && transaction.wallet_id.equals(id_wallet) && transactionYear==year && (month==-1 || transactionMonth==month)){
                        listExpense.add(transaction);
                    }
                    else
                    if(transaction.transaction_type.equals("TRANSFER")&&transaction.wallet_id.equals(id_wallet) && transactionYear==year && (month==-1 || transactionMonth==month)){
                        listExpense.add(transaction);
                    }
                }
            Log.d("Expense", listExpense.size()+"");
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        Collections.sort(listExpense, new Comparator<GetAllTransactionsEntity_quyen>() {
            @Override
            public int compare(GetAllTransactionsEntity_quyen t1, GetAllTransactionsEntity_quyen t2) {
                return Float.compare(Float.parseFloat(t2.amount), Float.parseFloat(t1.amount));
            }
        });
        AdjustList(listExpense);
        total_expense.setText(formatString(String.valueOf(TotalExpense(listExpense)))+" "+currency);
        ArrayList<PieEntry> categories=new ArrayList<>();
        if(listExpense.size()==0)
        {
            categories.add(new PieEntry(100,"No expense"));
        } else if (listExpense.size()<=4) {
            for(int i = 0; i< listExpense.size(); i++)
            {
                if(listExpense.get(i).category_id!=null)
                    categories.add(new PieEntry(Float.parseFloat(listExpense.get(i).amount),getCategoryNameById(listExpense.get(i).category_id)));
                else
                    categories.add(new PieEntry(Float.parseFloat(listExpense.get(i).amount),"Transfer"));
            }
        }
        else {
            for(int i=0;i<4;i++)
            {
                if(listExpense.get(i).category_id!=null)
                    categories.add(new PieEntry(Float.parseFloat(listExpense.get(i).amount),getCategoryNameById(listExpense.get(i).category_id)));
                else
                    categories.add(new PieEntry(Float.parseFloat(listExpense.get(i).amount),"Transfer"));
            }
            float sum=0;
            for(int i = 4; i< listExpense.size(); i++)
            {
                sum+=Float.parseFloat(listExpense.get(i).amount);
            }
            categories.add(new PieEntry(sum,"Others"));
        }

        colors = new ArrayList<>();
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
        pieChart.setCenterText("Expense (%)");
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
    void AdjustList(ArrayList<GetAllTransactionsEntity_quyen> listExpense)
    {
        for(int i=0;i<listExpense.size();i++)
        {
            if(listExpense.get(i).category_id!=null)
                for(int j=i+1;j<listExpense.size();j++)
                {
                    if(listExpense.get(i).category_id.equals(listExpense.get(j).category_id))
                    {
                        listExpense.get(i).amount=String.valueOf(Float.parseFloat(listExpense.get(i).amount)+Float.parseFloat(listExpense.get(j).amount));
                        listExpense.remove(j);
                        j--;
                    }
                }
            else
                for(int j=i+1;j<listExpense.size();j++)
                {
                    if(listExpense.get(j).transaction_type.equals("TRANSFER"))
                    {
                        listExpense.get(i).amount=String.valueOf(Float.parseFloat(listExpense.get(i).amount)+Float.parseFloat(listExpense.get(j).amount));
                        listExpense.remove(j);
                        j--;
                    }
                }

        }
    }
    Float TotalExpense(ArrayList<GetAllTransactionsEntity_quyen> listExpense)
    {
        float sum=0;
        for(GetAllTransactionsEntity_quyen transaction:listExpense)
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
    public double roundTwoDecimals(double d) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public double convertStringToDouble(String input) {
        String processedInput = input.replace(".", "");
        double value = Double.parseDouble(processedInput);
        return value / 1000;
    }
}