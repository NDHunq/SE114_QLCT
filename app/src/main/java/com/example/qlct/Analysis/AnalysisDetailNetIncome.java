package com.example.qlct.Analysis;

import android.content.Intent;
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

import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;
import com.example.qlct.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AnalysisDetailNetIncome extends AppCompatActivity {
    ListView listView;
    List<AnalysisNetIcome> arraylist;
    ImageView exit;
    LineChart lineChart;
    ArrayList<GetAllTransactionsEntity_quyen> listIncome;
    ArrayList<GetAllTransactionsEntity_quyen> listExpense;
    String currency;
    String date;
    TextView income;
    TextView expense;
    TextView net_income;
    TextView date_lbl;

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

        Intent intent = getIntent();

        // Nhận Bundle từ Intent
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            // Nhận dữ liệu từ Bundle
            listIncome = (ArrayList<GetAllTransactionsEntity_quyen>) bundle.getSerializable("listIncome");
            listExpense = (ArrayList<GetAllTransactionsEntity_quyen>) bundle.getSerializable("listExpense");
            currency = bundle.getString("currency");
            date = bundle.getString("date");

        }
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
        try {
            SetText();
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
        Analysis_NetIncome_Adapter adapter=new Analysis_NetIncome_Adapter(arraylist,this,R.layout.analysis_net_income_list_item,currency);
        listView.setAdapter(adapter);
    }
    void AnhXa(){
        lineChart=this.findViewById(R.id.line_chart);
        listView=this.findViewById(R.id.listvieww);
        income=this.findViewById(R.id.income);
        expense=this.findViewById(R.id.expense);
        date_lbl=this.findViewById(R.id.date_lbl);
        net_income=this.findViewById(R.id.net_income);

        Map<Integer, Double> incomeMap = new HashMap<>();
        Map<Integer, Double> expenseMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date.length() == 4) {
            for (GetAllTransactionsEntity_quyen transaction : listIncome) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1; // getMonth() returns the month starting from 0
                    incomeMap.put(transactionMonth, Double.valueOf(transaction.amount));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (GetAllTransactionsEntity_quyen transaction : listExpense) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1; // getMonth() returns the month starting from 0
                    expenseMap.put(transactionMonth, Double.valueOf(transaction.amount));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 12; i++) {
                double incomeValue = incomeMap.getOrDefault(i, 0.0);
                double expenseValue = expenseMap.getOrDefault(i, 0.0);
                if(incomeValue == 0 && expenseValue == 0) {
                    continue;
                }
                arraylist.add(new AnalysisNetIcome(i, incomeValue, expenseValue));
            }
        } else {
            for (GetAllTransactionsEntity_quyen transaction : listIncome) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionDay = calendar.get(Calendar.DAY_OF_MONTH);
                    incomeMap.put(transactionDay, Double.valueOf(transaction.amount));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (GetAllTransactionsEntity_quyen transaction : listExpense) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionDay = calendar.get(Calendar.DAY_OF_MONTH);
                    expenseMap.put(transactionDay, Double.valueOf(transaction.amount));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 31; i++) {
                double incomeValue = incomeMap.getOrDefault(i, 0.0);
                double expenseValue = expenseMap.getOrDefault(i, 0.0);
                if(incomeValue == 0 && expenseValue == 0) {
                    continue;
                }
                arraylist.add(new AnalysisNetIcome(i, incomeValue, expenseValue));
            }
        }


    }
    void SetText() {
        date_lbl.setText(date);
        float totalIncome = 0;
        float totalExpense = 0;
        for (GetAllTransactionsEntity_quyen transaction : listIncome) {
            totalIncome += Float.parseFloat(transaction.amount);
        }
        for (GetAllTransactionsEntity_quyen transaction : listExpense) {
            totalExpense += Float.parseFloat(transaction.amount);
        }
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));

// Chuyển đổi totalIncome thành dạng có dấu phẩy
        String formattedIncome = format.format(totalIncome);

// Đặt text cho income
        income.setText(formattedIncome + " " + currency);
        expense.setText(format.format(totalExpense) + " " + currency);
        net_income.setText(format.format(totalIncome - totalExpense) + " " + currency);
    }
    public void Draw() {
        ArrayList<Entry> entriesIncome = new ArrayList<>();
        ArrayList<Entry> entriesExpense = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date.length() == 4) {
            for (GetAllTransactionsEntity_quyen transaction : listIncome) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1; // getMonth() returns the month starting from 0
                    entriesIncome.add(new Entry(transactionMonth,Float.valueOf(transaction.amount) ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (GetAllTransactionsEntity_quyen transaction : listExpense) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionMonth = calendar.get(Calendar.MONTH) + 1; // getMonth() returns the month starting from 0
                    entriesExpense.add(new Entry(transactionMonth, Float.valueOf(transaction.amount)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (GetAllTransactionsEntity_quyen transaction : listIncome) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionDay = calendar.get(Calendar.DAY_OF_MONTH);
                    entriesIncome.add(new Entry(transactionDay, Float.valueOf(transaction.amount)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (GetAllTransactionsEntity_quyen transaction : listExpense) {
                try {
                    Date transactionDate = format.parse(transaction.transaction_date);
                    calendar.setTime(transactionDate);
                    int transactionDay = calendar.get(Calendar.DAY_OF_MONTH);
                    entriesExpense.add(new Entry(transactionDay, Float.valueOf(transaction.amount)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        LineDataSet dataSetIncome = new LineDataSet(entriesIncome, "Income");
        dataSetIncome.setColor(Color.parseColor("#177715")); // Set the line color
        dataSetIncome.setDrawValues(false);

        LineDataSet dataSetExpense = new LineDataSet(entriesExpense, "Expense");
        dataSetExpense.setColor(Color.parseColor("#FF0000")); // Set the line color
        dataSetExpense.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(dataSetIncome);
        lineData.addDataSet(dataSetExpense);

        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);

        // Create a custom ValueFormatter
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value); // Convert float value to day or month string
            }
        };

        // Apply the formatter to the x-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Disable right y-axis
        lineChart.getAxisRight().setEnabled(false);

        // Annotation
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(14f);
        legend.setFormSize(14f);

        lineChart.invalidate();
    }
}