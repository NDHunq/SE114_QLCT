package com.example.qlct.Analysis;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;
import com.example.qlct.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisExpenseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView detail;
    PieChart pieChart;
    ArrayList<GetAllTransactionsEntity_quyen> listTransactions;
    String id_wallet;
    ArrayList<GetAllCategoryEntity> listCategory;
    String currency;
    TextView total_expense;
    ArrayList<GetAllTransactionsEntity_quyen> listExpense;
    Bundle bundle;
    String date;
    public AnalysisExpenseFragment() {
        // Required empty public constructor
    }

    public AnalysisExpenseFragment(ArrayList<GetAllTransactionsEntity_quyen> listTransactions,String id_wallet,ArrayList<GetAllCategoryEntity> listCategory,String currency,String date) {
        bundle=new Bundle();
        ArrayList<GetAllTransactionsEntity_quyen> listTransactionsCopy = new ArrayList<>();
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
            listTransactionsCopy.add(transactionCopy);
        }
        bundle.putSerializable("listTransactions", listTransactionsCopy);
        bundle.putSerializable("listCategory",listCategory);
        bundle.putString("currency",currency);
        bundle.putString("id_wallet",id_wallet);
        bundle.putString("date",date);
        this.listTransactions = listTransactions;
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
     * @return A new instance of fragment AnalysisExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisExpenseFragment newInstance(String param1, String param2) {
        AnalysisExpenseFragment fragment = new AnalysisExpenseFragment();
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
        View view=inflater.inflate(R.layout.fragment_analysis_expense, container, false);
        detail=view.findViewById(R.id.detail_exp);
        pieChart=view.findViewById(R.id.piechart);
        total_expense=view.findViewById(R.id.total_expense);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), AnalysisDetailExpense.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
        SetUpPieChart();
        return view;

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

            listExpense=new ArrayList<>();
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
            Log.d("Expense",listExpense.size()+"");
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
            for(int i=0;i<listExpense.size();i++)
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
            for(int i=4;i<listExpense.size();i++)
            {
                sum+=Float.parseFloat(listExpense.get(i).amount);
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
}