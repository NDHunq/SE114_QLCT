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
import com.example.qlct.API_Entity.GetAllCategoryy;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisIcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisIcomeFragment extends Fragment {

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
    ArrayList<GetAllTransactionsEntity_quyen> listIncome;
    ArrayList<GetAllCategoryEntity> listCategory;
    String id_wallet;
    TextView total_icome;
    String currency;
    Bundle bundle;
    String date;
    public AnalysisIcomeFragment(ArrayList<GetAllTransactionsEntity_quyen> listTransactions,String id_wallet,ArrayList<GetAllCategoryEntity> listCategory, String currency,String date) {
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

    public AnalysisIcomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisIcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisIcomeFragment newInstance(String param1, String param2) {
        AnalysisIcomeFragment fragment = new AnalysisIcomeFragment();
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
        View view =inflater.inflate(R.layout.fragment_analysis_icome, container, false);
        detail=view.findViewById(R.id.detail_inc);
        pieChart=view.findViewById(R.id.piechart);
        total_icome=view.findViewById(R.id.total_icome);
        SetUpPieChart();

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), AnalysisDetailIncome.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });

        return view;
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
            Toast.makeText(getContext(),listIncome.size()+"", Toast.LENGTH_SHORT).show();
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