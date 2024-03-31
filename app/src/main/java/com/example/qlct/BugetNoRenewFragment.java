package com.example.qlct;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.lang.reflect.Field;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BugetNoRenewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BugetNoRenewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView apply;
    TextView date;
    TextView day;
    TextView month;
    TextView year;
    TextView week;
    TextView timespan;
    ImageView back;
    ImageView next;
    static String status;
    private OnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
    public BugetNoRenewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BugetNoRenewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BugetNoRenewFragment newInstance(String param1, String param2) {
        BugetNoRenewFragment fragment = new BugetNoRenewFragment();
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
        View view = inflater.inflate(R.layout.fragment_buget_no_renew, container, false);
        apply=view.findViewById(R.id.apply);
        date=view.findViewById(R.id.date);
        day=view.findViewById(R.id.day);
        month=view.findViewById(R.id.month);
        year=view.findViewById(R.id.year);
        week=view.findViewById(R.id.week);
        timespan=view.findViewById(R.id.time_span);
        back=view.findViewById(R.id.back);
        next=view.findViewById(R.id.next);
        status="day";
        Calendar calendar = Calendar.getInstance();
        // Lấy ngày, tháng, năm hiện tại
        int dayy = calendar.get(Calendar.DAY_OF_MONTH);
        int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int yearr = calendar.get(Calendar.YEAR);
        date.setText(dayy + "/" + monthh + "/" + yearr);
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="day";
                Calendar calendar = Calendar.getInstance();
                // Lấy ngày, tháng, năm hiện tại
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int year = calendar.get(Calendar.YEAR);
                date.setText(day + "/" + month + "/" + year);
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="month";
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int year = calendar.get(Calendar.YEAR);
                date.setText( month + "/" + year);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="year";
                int year = calendar.get(Calendar.YEAR);
                date.setText(year+"");
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="week";
                Calendar calendar = Calendar.getInstance();
                // Lấy ngày, tháng, năm hiện tại
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int year = calendar.get(Calendar.YEAR);
                date.setText(getStartAndEndOfWeek(day + "/" + month + "/" + year));
            }
        });
        timespan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="timespan";
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "day":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích ngày từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng ngày lên 1
                        calendar.add(Calendar.DAY_OF_MONTH, 1);

                        // Định dạng ngày mới và đặt nó vào TextView
                        date.setText(format.format(calendar.getTime()));
                    }
                        break;
                    case "month":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích tháng từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng tháng lên 1
                        calendar.add(Calendar.MONTH, 1);

                        // Định dạng tháng mới và đặt nó vào TextView
                        date.setText(format.format(calendar.getTime()));
                    }
                        break;
                    case "year":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.getDefault());
                        try {
                            // Phân tích năm từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng năm lên 1
                        calendar.add(Calendar.YEAR, 1);

                        // Định dạng năm mới và đặt nó vào TextView
                        date.setText(format.format(calendar.getTime()));

                    }
                        break;
                    case "week":
                    {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Calendar calendarStart = Calendar.getInstance();
                        Calendar calendarEnd = Calendar.getInstance();
                        try {
                            // Phân tích ngày từ TextView
                            String[] dates = date.getText().toString().split(" - ");
                            calendarStart.setTime(format.parse(dates[0]));
                            calendarEnd.setTime(format.parse(dates[1]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Tăng ngày lên 7 (1 tuần)
                        calendarStart.add(Calendar.DAY_OF_MONTH, 7);
                        calendarEnd.add(Calendar.DAY_OF_MONTH, 7);

                        // Định dạng ngày mới và đặt nó vào TextView
                        date.setText(format.format(calendarStart.getTime()) + " - " + format.format(calendarEnd.getTime()));

                    }
                        break;
                    case "timespan":
                    {

                    }
                        break;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "day":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích ngày từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm ngày đi 1
                        calendar.add(Calendar.DAY_OF_MONTH, -1);

                        // Định dạng ngày mới và đặt nó vào TextView
                        date.setText(format.format(calendar.getTime()));
                    }
                    break;
                    case "month":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
                        try {
                            // Phân tích tháng từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm tháng đi 1
                        calendar.add(Calendar.MONTH, -1);

                        // Định dạng tháng mới và đặt nó vào TextView
                        date.setText(format.format(calendar.getTime()));
                    }
                    break;
                    case "year":
                    {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.getDefault());
                        try {
                            // Phân tích năm từ TextView
                            calendar.setTime(format.parse(date.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm năm đi 1
                        calendar.add(Calendar.YEAR, -1);

                        // Định dạng năm mới và đặt nó vào TextView
                        date.setText(format.format(calendar.getTime()));

                    }
                    break;
                    case "week":
                    {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Calendar calendarStart = Calendar.getInstance();
                        Calendar calendarEnd = Calendar.getInstance();
                        try {
                            // Phân tích ngày từ TextView
                            String[] dates = date.getText().toString().split(" - ");
                            calendarStart.setTime(format.parse(dates[0]));
                            calendarEnd.setTime(format.parse(dates[1]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Giảm ngày đi 7 (1 tuần)
                        calendarStart.add(Calendar.DAY_OF_MONTH, -7);
                        calendarEnd.add(Calendar.DAY_OF_MONTH, -7);

                        // Định dạng ngày mới và đặt nó vào TextView
                        date.setText(format.format(calendarStart.getTime()) + " - " + format.format(calendarEnd.getTime()));

                    }
                    break;
                    case "timespan":
                    {

                    }
                    break;
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tham chiếu đến MyDialogFragment
                MyDialogFragment dialogFragment = (MyDialogFragment) getParentFragment();
                if (dialogFragment != null) {
                    // Đóng dialog
                    dialogFragment.dismiss();
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "day":
                    {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault());
                                        date.setText(format.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                        break;
                    case "month":
                    {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_month_picker);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                    }
                        break;
                    case "year":
                    {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_year_picker);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                    }
                        break;
                    case "week":
                    {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault());
                                        date.setText(format.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                        break;
                    case "timespan":
                    {}
                        break;
                }

            }
        });
        return view;
    }
    public String getStartAndEndOfWeek(String inputDate) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try {
            // Phân tích ngày từ input
            calendar.setTime(format.parse(inputDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Đặt calendar về ngày đầu tuần (Thứ 2)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        // In ra ngày thứ 2
        System.out.println("Monday: " + format.format(calendar.getTime()));
        result=result+ format.format(calendar.getTime())+" - ";
        // Đặt calendar về ngày cuối tuần (Chủ nhật)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        // In ra ngày chủ nhật
        result=result+ format.format(calendar.getTime());
        return result;
    }
}