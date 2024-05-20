package com.example.qlct.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.API_Entity.GetAllCategoryEntity;
import com.example.qlct.API_Entity.GetAllTransactionsEntity_quyen;
import com.example.qlct.API_Entity.GetAllWalletsEntity;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.API_Utils.CategoryAPIUtil;
import com.example.qlct.API_Utils.TransactionAPIUtil;
import com.example.qlct.API_Utils.WalletAPIUtil;
import com.example.qlct.Analysis.AnalysisExpenseFragment;
import com.example.qlct.Analysis.AnalysisIcomeFragment;
import com.example.qlct.Analysis.AnalysisNetIncomeFragment;
import com.example.qlct.Notification.Notificaiton;
import com.example.qlct.R;
import com.example.qlct.SelectWallet_Adapter;
import com.example.qlct.Wallet_hdp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Analysis_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Analysis_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioButton month,year;
    ImageView back,next ;
    TextView date;
    ImageView bell;
    LinearLayout select_wallet;
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat monthFormat = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
    final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private ListView walletListView;

    private ArrayList<Wallet_hdp> walletList;
    TextView wallet_name;
    ArrayList<GetAllTransactionsEntity_quyen> listTransactions;
    ArrayList<GetAllWalletsEntity> listwallet;
    ArrayList<GetAllCategoryEntity> listCategory;
    String id_wallet;


    public Analysis_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Analysis_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Analysis_fragment newInstance(String param1, String param2) {
        Analysis_fragment fragment = new Analysis_fragment();
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
        View view=inflater.inflate(R.layout.fragment_analysis_fragment, container, false);

        TransactionAPIUtil transactionAPIUtil = new TransactionAPIUtil();
        listTransactions = transactionAPIUtil.getAllTransactionsAPI();
        WalletAPIUtil walletAPIUtil = new WalletAPIUtil();

        listwallet = walletAPIUtil.getAllWalletAPI();
        CategoryAPIUtil categoryAPIUtil = new CategoryAPIUtil();
        listCategory = categoryAPIUtil.getAllCategory();
        AnhXa(view);
       Load(listTransactions,"Total",listCategory,date.getText().toString());

        return view;
    }
    public void Load(ArrayList<GetAllTransactionsEntity_quyen> listtrans,String id_wallet,ArrayList<GetAllCategoryEntity> listCategory,String Date)
    {
        ArrayList<GetAllTransactionsEntity_quyen> listTransCopy = new ArrayList<>();
        ArrayList<GetAllTransactionsEntity_quyen> listTransCopy1 = new ArrayList<>();
        ArrayList<GetAllTransactionsEntity_quyen> listTransCopy2 = new ArrayList<>();
        for (GetAllTransactionsEntity_quyen transaction : listtrans) {
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
            listTransCopy.add(transactionCopy);
            listTransCopy1.add(transactionCopy);
            listTransCopy2.add(transactionCopy);
        }
        Log.d("ListTransCopy", listTransCopy.size()+"");
        Log.d("ListTransCopy1", listTransCopy1.size()+"");
        Log.d("ListTransCopy2", listTransCopy2.size()+"");
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment child= new AnalysisNetIncomeFragment(listTransCopy,id_wallet,listCategory,getCurrencyUnitById(id_wallet),Date);
        transaction.replace(R.id.ChildFrag1,child);
        transaction.commit();

        FragmentManager fragmentManager1=getChildFragmentManager();
        FragmentTransaction transaction1=fragmentManager1.beginTransaction();
        Fragment child1= new AnalysisExpenseFragment(listTransCopy1,id_wallet,listCategory,getCurrencyUnitById(id_wallet),Date);
        transaction1.replace(R.id.ChildFrag2,child1);
        transaction1.commit();

        FragmentManager fragmentManager2=getChildFragmentManager();
        FragmentTransaction transaction2=fragmentManager2.beginTransaction();
        Fragment child2= new AnalysisIcomeFragment(listTransCopy2,id_wallet,listCategory,getCurrencyUnitById(id_wallet),Date);
        transaction2.replace(R.id.ChildFrag3,child2);
        transaction2.commit();
    }
    public void AnhXa(View view) {
        month=view.findViewById(R.id.month);
        month.setChecked(true);
        year=view.findViewById(R.id.year);
        back=view.findViewById(R.id.back);
        next=view.findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        date = view.findViewById(R.id.date);
        bell = view.findViewById(R.id.bell);
        select_wallet = view.findViewById(R.id.select_wallet);
        wallet_name = view.findViewById(R.id.wallet_name);

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notificaiton.class);
                startActivity(intent);
            }
        });
        month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    date.setText(monthFormat.format(Calendar.getInstance().getTime()));
                    next.setVisibility(View.INVISIBLE);
                    calendar.setTime(Calendar.getInstance().getTime());

                }
            }
        });

        year.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    date.setText(yearFormat.format(Calendar.getInstance().getTime()));
                    next.setVisibility(View.INVISIBLE);
                    calendar.setTime(Calendar.getInstance().getTime());

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month.isChecked()) {
                    calendar.add(Calendar.MONTH, 1);
                    date.setText(monthFormat.format(calendar.getTime()));
                    if ((calendar.getTime().getMonth()==(Calendar.getInstance().getTime().getMonth()))&&(calendar.getTime().getYear()==(Calendar.getInstance().getTime().getYear()))) {
                        next.setVisibility(View.INVISIBLE);
                    }

                } else if (year.isChecked()) {
                    calendar.add(Calendar.YEAR, 1);
                    date.setText(yearFormat.format(calendar.getTime()));
                    if (calendar.getTime().getYear()==(Calendar.getInstance().getTime().getYear())) {
                        next.setVisibility(View.INVISIBLE);
                    }
                }

                // If the new date is equal to or after the current date, hide the next button

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Update the date TextView
                if (month.isChecked()) {
                    calendar.add(Calendar.MONTH, -1);
                    date.setText(monthFormat.format(calendar.getTime()));
                } else if (year.isChecked()) {
                    calendar.add(Calendar.YEAR, -1);
                    date.setText(yearFormat.format(calendar.getTime()));
                }

                next.setVisibility(View.VISIBLE);
            }
        });
        select_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWalletDialog();
            }
        });
        try{
            wallet_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Do nothing
                }

                @Override
                public void afterTextChanged(Editable s) {
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
                    if (wallet_name.getText().toString().equals("Total")) {
                        Load(lisrI, "Total", listCategory, date.getText().toString());
                    } else {
                        id_wallet = getWalletIdByName(wallet_name.getText().toString());
                        Load(lisrI, id_wallet, listCategory, date.getText().toString());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                if (wallet_name.getText().toString().equals("Total")) {
                    Load(lisrI, "Total", listCategory, date.getText().toString());
                } else {
                    id_wallet = getWalletIdByName(wallet_name.getText().toString());
                    Load(lisrI, id_wallet, listCategory, date.getText().toString());
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiaLog();
            }
        });
    }
    void ShowDiaLog(){
        if(date.getText().length()==4)
        {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_year_picker);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            GridView gridView = dialog.findViewById(R.id.gridView);
            TextView year_lbl = dialog.findViewById(R.id.year);
            TextView cancel = dialog.findViewById(R.id.cancel);
            TextView ok = dialog.findViewById(R.id.ok);
            ImageView up = dialog.findViewById(R.id.up);
            ImageView down = dialog.findViewById(R.id.down);
            String[] years={"2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024"};
            ArrayAdapter yearAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, years) ;
            gridView.setAdapter(yearAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    year_lbl.setText(years[position]);
                }
            });
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    if(Integer.parseInt(year_lbl.getText().toString())<currentYear)
                        year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())+1+"");
                    else
                        Toast.makeText(getContext(), "Năm không được lớn hơn "+currentYear, Toast.LENGTH_LONG).show();
                }
            });
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isYearLessThanCurrent(year_lbl.getText().toString()))
                    {
                        Toast.makeText(getContext(), "Năm không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        date.setText(year_lbl.getText().toString());
                    }
                    dialog.dismiss();
                }
            });
        }
        else {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_month_picker);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            GridView gridView = dialog.findViewById(R.id.gridView);
            TextView month_lbl = dialog.findViewById(R.id.month);
            TextView year_lbl = dialog.findViewById(R.id.year);
            TextView cancel = dialog.findViewById(R.id.cancel);
            TextView ok = dialog.findViewById(R.id.ok);
            ImageView up = dialog.findViewById(R.id.up);
            ImageView down = dialog.findViewById(R.id.down);
            String[] month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            ArrayAdapter monthAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, month) ;
            gridView.setAdapter(monthAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    month_lbl.setText(month[position]);
                }
            });
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int currentYear = calendar.get(Calendar.YEAR);
                    if(Integer.parseInt(year_lbl.getText().toString())<currentYear)
                        year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())+1+"");
                    else
                        Toast.makeText(getContext(), "Năm không được lớn hơn "+currentYear, Toast.LENGTH_LONG).show();
                }
            });
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputDate = month_lbl.getText().toString()+" "+year_lbl.getText().toString();
                    SimpleDateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH);

                    try {
                        Date daTe = inputFormat.parse(inputDate);
                        String outputDate = outputFormat.format(daTe);
                        if(isDateLessThanCurrent(outputDate))
                        {
                            Toast.makeText(getContext(), "Tháng không được lớn hơn hiện tại", Toast.LENGTH_LONG).show();
                        }
                        else
                            date.setText(outputDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();
                }
            });
        }
    }
    public boolean isDateLessThanCurrent(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date inputDate = format.parse(dateString);

            // Lấy tháng và năm hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1

            // Lấy tháng và năm từ chuỗi ngày
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);
            int inputYear = inputCalendar.get(Calendar.YEAR);
            int inputMonth = inputCalendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1

            // So sánh tháng và năm
            if (inputYear < currentYear || (inputYear == currentYear && inputMonth <= currentMonth)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isYearLessThanCurrent(String yearString) {
        // Chuyển đổi chuỗi năm thành số nguyên
        int inputYear = Integer.parseInt(yearString);

        // Lấy năm hiện tại
        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);

        // So sánh năm
        if (inputYear <= currentYear) {
            return false;
        } else {
            return true;
        }
    }
    String getWalletIdByName(String walletName) {
        for (GetAllWalletsEntity wallet : listwallet) {
            if (wallet.name.equals(walletName)) {
                return wallet.id;
            }
        }
        return null; // Return null if no matching wallet is found
    }
    private void showWalletDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_select_wallet);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationn;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bgh);
        dialog.show();

        walletListView = dialog.findViewById(R.id.select_wallet_listview);
        AnhXaWallet();
        SelectWallet_Adapter adapter = new SelectWallet_Adapter(getActivity(), R.layout.select_wallet_item_list, walletList);
        walletListView.setAdapter(adapter);
        walletListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy item được chọn từ walletList
                wallet_name.setText(walletList.get(position).getWalletName());
                dialog.dismiss();
            }
        });
    }
    private void AnhXaWallet(){
        try{
            walletList = new ArrayList<Wallet_hdp>();
            ArrayList<GetAllWalletsEntity> parseAPIList = new WalletAPIUtil().getAllWalletAPI();
            //Chạy vòng lặp để lấy ra các field cần thiết cho hiển thị ra Views
            for (GetAllWalletsEntity item : parseAPIList) {
                walletList.add(new Wallet_hdp(item.id, item.name, item.amount, R.drawable.wallet, item.currency_unit));
            }
            walletList.add(new Wallet_hdp("total", "Total", "", R.drawable.wallet, ""));
            Log.d("Get_wallet_data_object", walletList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    String getCurrencyUnitById(String id_wallet) {
        for (GetAllWalletsEntity wallet : listwallet) {
            if (wallet.id.equals(id_wallet)) {
                String currency_unit = wallet.currency_unit;
                if (currency_unit.equals("VND")) {
                    return "đ";
                } else if (currency_unit.equals("USD")) {
                    return "$";
                }
            }
        }
        return "đ";
    }
}