package com.example.qlct.Analysis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qlct.API_Entity.GetAllCategoryEntity;
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
import java.util.Locale;

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
    ArrayList<GetAllTransactionsEntity_quyen> listTransactions;
    String id_wallet;
    ArrayList<GetAllCategoryEntity> listCategory;
    String currency;
    String date;
    ArrayList<GetAllTransactionsEntity_quyen> listIncome;
    ArrayList<GetAllTransactionsEntity_quyen> listExpense;
    TextView income;
    TextView expense;
    TextView net_income;
    Bundle bundle;

    public AnalysisNetIncomeFragment() {
        // Required empty public constructor
    }

    public AnalysisNetIncomeFragment(ArrayList<GetAllTransactionsEntity_quyen> listTransactions, String id_wallet,ArrayList<GetAllCategoryEntity> listCategory,String currency,String date) {
        ArrayList<GetAllTransactionsEntity_quyen> lisrI = new ArrayList<>();
        for (GetAllTransactionsEntity_quyen transaction : listTransactions) {
            GetAllTransactionsEntity_quyen transactionCopy = new GetAllTransactionsEntity_quyen(
                    transaction.id,
                    transaction.user_id,
                    transaction.amount,
                    transaction.category_id,
                    transaction.wallet_id,
                    transaction.notes,
                    transaction.picture,
                    transaction.transaction_date,
                    transaction.transaction_type,
                    transaction.currency_unit,
                    transaction.target_wallet_id,
                    transaction.wallet,
                    transaction.category
            );
            lisrI.add(transactionCopy);
        }
        this.listTransactions = lisrI;
        this.id_wallet=id_wallet;
        this.listCategory=listCategory;
        this.currency=currency;
        this.date=date;
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
        income=view.findViewById(R.id.income);
        expense=view.findViewById(R.id.expense);
        net_income=view.findViewById(R.id.net_income);

        see_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AnalysisDetailNetIncome.class);
                bundle=new Bundle();
                ArrayList<GetAllTransactionsEntity_quyen> lisrI = new ArrayList<>();
                for (GetAllTransactionsEntity_quyen transaction : listIncome) {
                    GetAllTransactionsEntity_quyen transactionCopy = new GetAllTransactionsEntity_quyen(
                            transaction.id,
                            transaction.user_id,
                            transaction.amount,
                            transaction.category_id,
                            transaction.wallet_id,
                            transaction.notes,
                            transaction.picture,
                            transaction.transaction_date,
                            transaction.transaction_type,
                            transaction.currency_unit,
                            transaction.target_wallet_id,
                            transaction.wallet,
                            transaction.category
                    );
                    lisrI.add(transactionCopy);
                }
                bundle.putSerializable("listIncome", lisrI);
                ArrayList<GetAllTransactionsEntity_quyen> listE = new ArrayList<>();
                for (GetAllTransactionsEntity_quyen transaction : listExpense) {
                    GetAllTransactionsEntity_quyen transactionCopy = new GetAllTransactionsEntity_quyen(
                            transaction.id,
                            transaction.user_id,
                            transaction.amount,
                            transaction.category_id,
                            transaction.wallet_id,
                            transaction.notes,
                            transaction.picture,
                            transaction.transaction_date,
                            transaction.transaction_type,
                            transaction.currency_unit,
                            transaction.target_wallet_id,
                            transaction.wallet,
                            transaction.category
                    );
                    listE.add(transactionCopy);
                }
                bundle.putSerializable("listExpense", listE);
                bundle.putString("currency",currency);
                bundle.putString("date",date);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        SplitList();
        Log.d("INCOME",String.valueOf(listIncome.size()) );
        Log.d("EXPENSE",String.valueOf(listExpense.size()));
        AdjustList(listIncome,date);
        AdjustList(listExpense,date);
        Log.d("INCOME",String.valueOf(listIncome.size()) );
        Log.d("EXPENSE",String.valueOf(listExpense.size()));
        SortListByDate(listIncome);
        SortListByDate(listExpense);
        Draw();
        SetText();
        return view;
    }
    void SetText() {
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
    void SplitList() {
        listIncome = new ArrayList<>();
        listExpense = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        int inputYear, inputMonth = -1;

        // Parse the date string
        if (date.contains("-")) {
            String[] parts = date.split("-");
            inputMonth = Integer.parseInt(parts[0]);
            inputYear = Integer.parseInt(parts[1]);
        } else {
            inputYear = Integer.parseInt(date);
        }

        for (GetAllTransactionsEntity_quyen transaction : listTransactions) {
            try {
                // Parse the transaction date
                Date transactionDate = format.parse(transaction.transaction_date);
                calendar.setTime(transactionDate);
                int transactionYear = calendar.get(Calendar.YEAR);
                int transactionMonth = calendar.get(Calendar.MONTH) + 1; // getMonth() returns the month starting from 0

                // Check if the transaction date matches the input date
                if (transactionYear == inputYear && (inputMonth == -1 || transactionMonth == inputMonth)) {
                    if (transaction.transaction_type.equals("INCOME"))
                        listIncome.add(transaction);
                    else if (transaction.transaction_type.equals("EXPENSE"))
                        listExpense.add(transaction);
                    else if (transaction.wallet_id.equals(id_wallet))
                        listExpense.add(transaction);
                    else if (transaction.target_wallet_id.equals(id_wallet))
                        listIncome.add(transaction);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    void AdjustList(ArrayList<GetAllTransactionsEntity_quyen> list, String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat keyFormat;
        Calendar calendar = Calendar.getInstance();
        int inputYear, inputMonth = -1;

        // Parse the date string
        if (date.contains("-")) {
            String[] parts = date.split("-");
            inputMonth = Integer.parseInt(parts[0]);
            inputYear = Integer.parseInt(parts[1]);
            keyFormat = new SimpleDateFormat("dd");
        } else {
            inputYear = Integer.parseInt(date);
            keyFormat = new SimpleDateFormat("MM");
        }

        int i = 0;
        while (i < list.size()) {
            GetAllTransactionsEntity_quyen transaction = list.get(i);
            try {
                // Parse the transaction date
                Date transactionDate = format.parse(transaction.transaction_date);
                calendar.setTime(transactionDate);
                int transactionYear = calendar.get(Calendar.YEAR);
                int transactionMonth = calendar.get(Calendar.MONTH) + 1; // getMonth() returns the month starting from 0

                // Check if the transaction date matches the input date
                if (transactionYear == inputYear && (inputMonth == -1 || transactionMonth == inputMonth)) {
                    String key = keyFormat.format(transactionDate);
                    int j = i + 1;
                    while (j < list.size()) {
                        GetAllTransactionsEntity_quyen otherTransaction = list.get(j);
                        Date otherTransactionDate = format.parse(otherTransaction.transaction_date);
                        calendar.setTime(otherTransactionDate);
                        String otherKey = keyFormat.format(otherTransactionDate);
                        Log.d("Transs",otherKey+"-"+key);
                        // Check if the other transaction date matches the current transaction date
                        if (otherKey.equals(key)) {
                            // If the other transaction date matches, add the amount to the current transaction and remove the other transaction
                            float currentAmount = Float.parseFloat(list.get(i).amount);
                            float otherAmount = Float.parseFloat(otherTransaction.amount);
                            list.get(i).amount = String.valueOf(currentAmount + otherAmount);                            list.remove(j);
                        } else {
                            j++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
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
void SortListByDate(ArrayList<GetAllTransactionsEntity_quyen> list) {
    list.sort((t1, t2) -> {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = format.parse(t1.transaction_date);
            Date date2 = format.parse(t2.transaction_date);
            return date1.compareTo(date2);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    });
    }
}