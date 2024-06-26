package com.example.qlct.Analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.math.RoundingMode;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;
import com.example.qlct.R;
import com.example.qlct.doitiente;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    ArrayList<Integer> colors;
    String date;
    TextView date_lbl;
    doitiente doitiente=new doitiente();
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
            date = bundle.getString("date");
        }
        total_icome=this.findViewById(R.id.total_income);
        exit=this.findViewById(R.id.exit_Income);
        listView=this.findViewById(R.id.listvieww);
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
        DoiDonVi();
        AnhXa();
        Analysis_Expense_Adapter adapter=new Analysis_Expense_Adapter(list,this,R.layout.analysis_expense_list_item);
        listView.setAdapter(adapter);


    }
    void DoiDonVi()
    {
        for(GetAllTransactionsEntity_quyen transaction:listIncome)
        {
            if(id_wallet.equals("Total"))
            {
                if(transaction.currency_unit.equals("VND"))
                    transaction.amount=String.valueOf(Float.parseFloat(transaction.amount));
                else if(transaction.currency_unit.equals("USD"))
                    transaction.amount=String.valueOf(Float.parseFloat(transaction.amount)*doitiente.getUSDtoVND());
                else if(transaction.currency_unit.equals("CNY"))
                    transaction.amount=String.valueOf(Float.parseFloat(transaction.amount)*doitiente.getCNYtoVND());
                else if(transaction.currency_unit.equals("EUR"))
                    transaction.amount=String.valueOf(Float.parseFloat(transaction.amount)*doitiente.getUERtoVND());
            }
        }
    }
    Double TinhPhanTram(String a, String b) {
        a=a.replace(",", ".");
        b=b.replace(",", ".");
        double valueA = convertStringToNumber(a);
        double valueB = convertStringToNumber(b);

        return (valueA * 100) / valueB;
    }

    double convertStringToNumber(String str) {
        double value;
        char lastChar = str.charAt(str.length() - 1);

        switch (lastChar) {
            case 'K':
                value = Double.parseDouble(str.substring(0, str.length() - 1)) * 1;
                break;
            case 'B':
                value = Double.parseDouble(str.substring(0, str.length() - 1)) * 1000000;
                break;
            case 'M':
                value = Double.parseDouble(str.substring(0, str.length() - 1)) * 1000;
                break;
            default:
                value = Double.parseDouble(str);
                break;
        }

        return value;
    }
    void AnhXa(){
        for(int i=0;i<listIncome.size();i++)
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
            double kq=TinhPhanTram(String.valueOf(doitiente.formatValue(Double.parseDouble(listIncome.get(i).amount))) ,total_icome.getText().toString().substring(0,total_icome.getText().toString().length()-2));
            if(listIncome.get(i).category_id!=null)
            {
                list.add(new AnalysisExpense(color,listIncome.get(i).category.picture,getCategoryNameById(listIncome.get(i).category_id),roundTwoDecimals(kq),doitiente.formatValue(Double.parseDouble((listIncome.get(i).amount))),currency));
                Log.d("Expense",Double.parseDouble(listIncome.get(i).amount)+"/"+ TotalIncome(listIncome));
            }
            else
            {
                String transferImage="https://expense-management-backend-2tac.onrender.com/api/v1/media/1716342462182.jpg";
                list.add(new AnalysisExpense(color,transferImage,"Transfer",roundTwoDecimals(kq),doitiente.formatValue(Double.parseDouble (listIncome.get(i).amount)),currency));
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

            listIncome=new ArrayList<>();
            if(id_wallet.equals("Total")){
                for(GetAllTransactionsEntity_quyen transaction:listTransactions){
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionYear = calendar.get(Calendar.YEAR);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1;
                    if(transaction.transaction_type.equals("INCOME") && transactionYear==year && (month==-1 || transactionMonth==month)){
                        listIncome.add(transaction);
                    }
                }
            }
            else
                for(GetAllTransactionsEntity_quyen transaction:listTransactions){
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionYear = calendar.get(Calendar.YEAR);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1;
                    if(transaction.transaction_type.equals("INCOME") && transaction.wallet_id.equals(id_wallet) && transactionYear==year && (month==-1 || transactionMonth==month)){
                        listIncome.add(transaction);
                    }
                    else
                    if(transaction.transaction_type.equals("TRANSFER")&&transaction.target_wallet_id.equals(id_wallet) && transactionYear==year && (month==-1 || transactionMonth==month)){
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
        total_icome.setText(doitiente.formatValue(TotalIncome(listIncome))+" "+currency);
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
    Double TotalIncome(ArrayList<GetAllTransactionsEntity_quyen> listIncome)
    {
        Double sum= (double) 0;
        for(GetAllTransactionsEntity_quyen transaction:listIncome)
        {
            if(id_wallet.equals("Total"))
            {
                if(transaction.currency_unit.equals("VND"))
                    sum+=Float.parseFloat(transaction.amount);
                else if(transaction.currency_unit.equals("USD"))
                    sum+=Float.parseFloat(transaction.amount)*doitiente.getUSDtoVND();
                else if(transaction.currency_unit.equals("CNY"))
                    sum+=Float.parseFloat(transaction.amount)*doitiente.getCNYtoVND();
                else if(transaction.currency_unit.equals("EUR"))
                    sum+=Float.parseFloat(transaction.amount)*doitiente.getUERtoVND();
            }
            else
            {
                sum+=Float.parseFloat(transaction.amount);
            }

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