package com.example.qlct.Budget;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qlct.API_Entity.GetAllBudget;
import com.example.qlct.API_Entity.GetAllCategoryy;
import com.example.qlct.API_Utils.CategoryAPIUntill;
import com.example.qlct.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BudgetRunningFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListView listView;
    List<Budget> list;
    TextView day,week,month,year,future;
    ArrayList<GetAllBudget> allBudgets = new ArrayList<>();
    ArrayList<GetAllCategoryy> listCate;
    public BudgetRunningFragment() {
    }
    public BudgetRunningFragment(ArrayList<GetAllBudget> allBudgets) {
        this.allBudgets = allBudgets;
    }

    public static BudgetRunningFragment newInstance(String param1, String param2) {
        BudgetRunningFragment fragment = new BudgetRunningFragment();
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
        View view =inflater.inflate(R.layout.fragment_budget_running, container, false);
        listView=view.findViewById(R.id.listviewRunning);
        GetAllCategory();
        Anhxa(view);
        Load(list);
        return view;
    }
    public void Load(List<Budget> listt)
    {
        Budget_adapter adapter=new Budget_adapter(getContext(),R.layout.budget_list_item,listt);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AdjustBudget.class);
                Budget budget = listt.get(position);
                intent.putExtra("budget", budget);

                startActivity(intent);

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    boolean CheckThisDayBetween(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fromDate = LocalDate.parse(from.substring(6), formatter);
        LocalDate toDate = LocalDate.parse(to.substring(4), formatter);
        LocalDate today = LocalDate.now();
        return ((today.isAfter(fromDate) || today.isEqual(fromDate)) && (today.isBefore(toDate) || today.isEqual(toDate)));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    boolean CheckThisDay(String day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fromDate = LocalDate.parse(day, formatter);
        LocalDate today = LocalDate.now();
        return today.isEqual(fromDate);
    }
    boolean CheckThisWeek(String day) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date inputDate = format.parse(day);

            // Lấy tuần của năm từ ngày nhập
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);
            int inputWeek = inputCalendar.get(Calendar.WEEK_OF_YEAR);

            // Lấy tuần hiện tại của năm
            Calendar currentCalendar = Calendar.getInstance();
            int currentWeek = currentCalendar.get(Calendar.WEEK_OF_YEAR);

            // So sánh tuần
            return inputWeek == currentWeek;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisWeekBetween(String from, String to) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date fromDate = format.parse(from.substring(6));
            Date toDate = format.parse(to.substring(4));

            // Lấy tuần của năm từ ngày nhập
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            int fromWeek = fromCalendar.get(Calendar.WEEK_OF_YEAR);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(toDate);
            int toWeek = toCalendar.get(Calendar.WEEK_OF_YEAR);

            // Lấy tuần hiện tại của năm
            Calendar currentCalendar = Calendar.getInstance();
            int currentWeek = currentCalendar.get(Calendar.WEEK_OF_YEAR);

            // So sánh tuần
            return (fromWeek <= currentWeek && currentWeek <= toWeek);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisMonth(String day) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date inputDate = format.parse(day);

            // Lấy tháng của năm từ ngày nhập
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);
            int inputMonth = inputCalendar.get(Calendar.MONTH);

            // Lấy tháng hiện tại của năm
            Calendar currentCalendar = Calendar.getInstance();
            int currentMonth = currentCalendar.get(Calendar.MONTH);

            // So sánh tháng
            return inputMonth == currentMonth;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisMonthBetween(String from, String to) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date fromDate = format.parse(from.substring(6));
            Date toDate = format.parse(to.substring(4));

            // Lấy tháng của năm từ ngày nhập
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            int fromMonth = fromCalendar.get(Calendar.MONTH);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(toDate);
            int toMonth = toCalendar.get(Calendar.MONTH);

            // Lấy tháng hiện tại của năm
            Calendar currentCalendar = Calendar.getInstance();
            int currentMonth = currentCalendar.get(Calendar.MONTH);

            // So sánh tháng
            return (fromMonth <= currentMonth && currentMonth <= toMonth);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisYear(String day) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date inputDate = format.parse(day);

            // Lấy năm từ ngày nhập
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);
            int inputYear = inputCalendar.get(Calendar.YEAR);

            // Lấy năm hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);

            // So sánh năm
            return inputYear == currentYear;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisYearBetween(String from, String to) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date fromDate = format.parse(from.substring(6));
            Date toDate = format.parse(to.substring(4));

            // Lấy năm từ ngày nhập
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            int fromYear = fromCalendar.get(Calendar.YEAR);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(toDate);
            int toYear = toCalendar.get(Calendar.YEAR);

            // Lấy năm hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);

            // So sánh năm
            return (fromYear <= currentYear && currentYear <= toYear);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisFuture(String day) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date inputDate = format.parse(day);

            // Lấy năm từ ngày nhập
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);
            int inputYear = inputCalendar.get(Calendar.YEAR);

            // Lấy năm hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);

            // So sánh năm
            return inputYear > currentYear;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    boolean CheckThisFutureBetween(String from, String to) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            // Phân tích chuỗi ngày thành đối tượng Date
            Date fromDate = format.parse(from.substring(6));
            Date toDate = format.parse(to.substring(4));

            // Lấy năm từ ngày nhập
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(fromDate);
            int fromYear = fromCalendar.get(Calendar.YEAR);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(toDate);
            int toYear = toCalendar.get(Calendar.YEAR);

            // Lấy năm hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            int currentYear = currentCalendar.get(Calendar.YEAR);

            // So sánh năm
            return (fromYear > currentYear && currentYear < toYear);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    void Anhxa(View view){
        day=view.findViewById(R.id.day);
        week=view.findViewById(R.id.week);
        month=view.findViewById(R.id.month);
        year=view.findViewById(R.id.year);
        future=view.findViewById(R.id.future);
        list=new ArrayList<>();
        String from="";
        String to="";
        if(allBudgets != null)
            for(int i=0;i<allBudgets.size();i++){
                if(allBudgets.get(i).getBudget_type().equals("NO_RENEW")) {
                    if (allBudgets.get(i).getNo_renew_date_unit().equals("DAY") ) {
                        from = "";
                        to = allBudgets.get(i).getNo_renew_date();
                    }
                    else {
                        String[] dates = allBudgets.get(i).getNo_renew_date().split(" ");
                        if(dates.length == 2) {
                            from ="From: "+ dates[0];
                            to = "To: "+dates[1];
                        }
                    }
                }
                else {
                    if(allBudgets.get(i).getRenew_date_unit().equals("Custom"))
                    {
                        String realDate = allBudgets.get(i).getCustom_renew_date().substring(0,10);
                        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        try {
                            Date date = originalFormat.parse(realDate);
                            realDate = targetFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        from="Renew at "+"\n"+realDate;
                        to="";
                    }
                    else {
                        from=allBudgets.get(i).getCreate_at().substring(0,10);
                        to="Renew "+allBudgets.get(i).getRenew_date_unit();
                    }

                }
                Budget budget = new Budget(GetNameCategory(allBudgets.get(i).getCategory_id()),Double.valueOf(allBudgets.get(i).getLimit_amount()) ,Double.valueOf(allBudgets.get(i).getExpensed_amount()) ,from,to,allBudgets.get(i).getCategory().getPicture(),allBudgets.get(i).getBudget_type(),allBudgets.get(i).getId(),allBudgets.get(i).getCurrency_unit());
                    list.add(budget);
            }
        else{
            Log.d("BudgetRunningFragment", "allBudgets is null");
        }
        day.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                future.setBackground(null);
                try{
                    List<Budget> list1=new ArrayList<>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).getType().equals("RENEW"))
                        {
                            list1.add(list.get(i));
                        }
                        else
                        {
                            if(list.get(i).getFromDate().equals(""))
                            {
                                if(CheckThisDay(list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }
                            else
                            {
                                if(CheckThisDayBetween(list.get(i).getFromDate(),list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }

                        }
                    }
                    Load(list1);
                }
                catch (Exception e)
                {
                    Log.d("BudgetRunningFragment", e.toString());
                }
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                day.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                future.setBackground(null);
                try{
                    List<Budget> list1=new ArrayList<>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).getType().equals("RENEW"))
                        {
                            list1.add(list.get(i));
                        }
                        else
                        {
                            if(list.get(i).getFromDate().equals(""))
                            {
                                if(CheckThisWeek(list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }
                            else
                            {
                                if(CheckThisWeekBetween(list.get(i).getFromDate(),list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }

                        }
                    }
                    Load(list1);
                }
                catch (Exception e)
                {
                    Log.d("BudgetRunningFragment", e.toString());
                }
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                day.setBackground(null);
                year.setBackground(null);
                future.setBackground(null);
                try{
                    List<Budget> list1=new ArrayList<>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).getType().equals("RENEW"))
                        {
                            list1.add(list.get(i));
                        }
                        else
                        {
                            if(list.get(i).getFromDate().equals(""))
                            {
                                if(CheckThisMonth(list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }
                            else
                            {
                                if(CheckThisMonthBetween(list.get(i).getFromDate(),list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }

                        }
                    }
                    Load(list1);
                }
                catch (Exception e)
                {
                    Log.d("BudgetRunningFragment", e.toString());
                }
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                month.setBackground(null);
                day.setBackground(null);
                future.setBackground(null);
                try{
                    List<Budget> list1=new ArrayList<>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).getType().equals("RENEW"))
                        {
                            list1.add(list.get(i));
                        }
                        else
                        {
                            if(list.get(i).getFromDate().equals(""))
                            {
                                if(CheckThisYear(list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }
                            else
                            {
                                if(CheckThisYearBetween(list.get(i).getFromDate(),list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }

                        }
                    }
                    Load(list1);
                }
                catch (Exception e)
                {
                    Log.d("BudgetRunningFragment", e.toString());
                }

            }
        });
        future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.grey_background2));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                day.setBackground(null);
                try{
                    List<Budget> list1=new ArrayList<>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(list.get(i).getType().equals("RENEW"))
                        {
                            list1.add(list.get(i));
                        }
                        else
                        {
                            if(list.get(i).getFromDate().equals(""))
                            {
                                if(CheckThisFuture(list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }
                            else
                            {
                                if(CheckThisFutureBetween(list.get(i).getFromDate(),list.get(i).getToDate()))
                                {
                                    list1.add(list.get(i));
                                }
                            }

                        }
                    }
                    Load(list1);
                }
                catch (Exception e)
                {
                    Log.d("BudgetRunningFragment", e.toString());
                }
            }
        });

    }
    void GetAllCategory()
    {
        listCate=new CategoryAPIUntill().getAllCategoryys();
    }
    String GetNameCategory(String id) {
        for(int i = 0; i < listCate.size(); i++) {
            if(listCate.get(i).getId().equals(id)) {
                return listCate.get(i).getName();
            }
        }
        return "";
    }
}