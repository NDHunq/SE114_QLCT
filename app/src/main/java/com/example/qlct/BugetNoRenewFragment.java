package com.example.qlct;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    LinearLayout time;
    LinearLayout from_to;
    TextView from,to;
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
        time=view.findViewById(R.id.time);
        from_to=view.findViewById(R.id.from_to);
        from=view.findViewById(R.id.from);
        to=view.findViewById(R.id.to);
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
                int dayy = calendar.get(Calendar.DAY_OF_MONTH);
                int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int yearr = calendar.get(Calendar.YEAR);
                date.setText(dayy + "/" + monthh + "/" + yearr);
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="month";
                int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int yearr = calendar.get(Calendar.YEAR);
                date.setText( monthh + "/" + yearr);
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                day.setBackground(null);
                year.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="year";
                int yearr = calendar.get(Calendar.YEAR);
                date.setText(yearr+"");
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                month.setBackground(null);
                day.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="week";
                Calendar calendar = Calendar.getInstance();
                // Lấy ngày, tháng, năm hiện tại
                int dayy = calendar.get(Calendar.DAY_OF_MONTH);
                int monthh = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                int yearr = calendar.get(Calendar.YEAR);
                date.setText(getStartAndEndOfWeek(dayy + "/" + monthh + "/" + yearr));
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                day.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                timespan.setBackground(null);
                time.setVisibility(View.VISIBLE);
                from_to.setVisibility(View.GONE);
            }
        });
        timespan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status="timespan";
                v.setBackground(getResources().getDrawable(R.drawable.grey_background));
                week.setBackground(null);
                month.setBackground(null);
                year.setBackground(null);
                day.setBackground(null);
                time.setVisibility(View.GONE);
                from_to.setVisibility(View.VISIBLE);
                from = view.findViewById(R.id.from);
                to = view.findViewById(R.id.to);
                from.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        from.setText(format.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                });
                to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        if(compareDates(from.getText().toString(),format.format(calendar.getTime()))==1 || compareDates(from.getText().toString(),format.format(calendar.getTime()))==0)
                                        {
                                            Toast.makeText(getContext(), "Ngày kết thúc không được nhỏ hơn hoặc bằng ngày bắt đầu", Toast.LENGTH_LONG).show();
                                        }
                                        else if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime())))
                                        {
                                            Toast.makeText(getContext(), "Ngày kết thúc không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                            to.setText(format.format(calendar.getTime()));

                                    }
                                }, year, month, day);
                        // Hiển thị DatePickerDialog
                        datePickerDialog.show();
                    }
                });
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
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tham chiếu đến MyDialogFragment
                MyDialogFragment dialogFragment = (MyDialogFragment) getParentFragment();
                if (dialogFragment != null) {
                    if (getActivity() instanceof AddBudget) {
                        AddBudget grandpa = (AddBudget) getActivity();
                        if(status=="timespan")
                        {
                            grandpa.SetData(from.getText().toString()+" - "+to.getText().toString());
                        }
                        else
                            grandpa.SetData(date.getText().toString());

                    } else {
                        // Handle the case where the parent activity is not an instance of AddBudget
                    }dialogFragment.dismiss();
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
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime()))==true)
                                        {
                                            Toast.makeText(getContext(), "Ngày không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
                                        }
                                        else
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
                                year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())+1+"");
                            }
                        });
                        down.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                int currentYear = calendar.get(Calendar.YEAR);
                                if(Integer.parseInt(year_lbl.getText().toString())>currentYear)
                                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                                else
                                    Toast.makeText(getContext(), "Năm không được nhỏ hơn "+currentYear, Toast.LENGTH_LONG).show();
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
                                SimpleDateFormat outputFormat = new SimpleDateFormat("M/yyyy", Locale.ENGLISH);

                                try {
                                    Date daTe = inputFormat.parse(inputDate);
                                    String outputDate = outputFormat.format(daTe);
                                    date.setText(outputDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                dialog.dismiss();
                            }
                        });
                    }
                        break;
                    case "year":
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
                        String[] years={"2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035"};
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
                                year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())+1+"");
                            }
                        });
                        down.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar calendar = Calendar.getInstance();
                                int currentYear = calendar.get(Calendar.YEAR);
                                if(Integer.parseInt(year_lbl.getText().toString())>currentYear)
                                    year_lbl.setText(Integer.parseInt(year_lbl.getText().toString())-1+"");
                                else
                                    Toast.makeText(getContext(), "Năm không được nhỏ hơn "+currentYear, Toast.LENGTH_LONG).show();
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
                                date.setText(year_lbl.getText().toString());
                                dialog.dismiss();
                            }
                        });
                    }
                        break;
                    case "week":
                    {
                        final Calendar b = Calendar.getInstance();
                        int year = b.get(Calendar.YEAR);
                        int month = b.get(Calendar.MONTH);
                        int day = b.get(Calendar.DAY_OF_MONTH);

                        // Tạo một DatePickerDialog mới
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // Định dạng ngày được chọn và đặt nó vào TextView
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        if(isEndDateLessThanCurrent(getStartAndEndOfWeek(format.format(calendar.getTime())))==true)
                                        {
                                            Toast.makeText(getContext(), "Ngày kết thúc không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                            date.setText(getStartAndEndOfWeek(format.format(calendar.getTime())));
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
    public boolean isEndDateLessThanCurrent(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            // Tách chuỗi ngày thành hai chuỗi riêng biệt
            String[] dates = dateString.split(" - ");
            // Chuyển đổi chuỗi ngày thứ hai thành đối tượng Date
            Date endDate = format.parse(dates[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date currentDate = calendar.getTime();
            // So sánh ngày thứ hai với ngày hiện tại
            return endDate.before(currentDate) ;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int compareDates(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);

            if (d1.before(d2)) {
                return -1; // date1 nhỏ hơn date2
            } else if (d1.after(d2)) {
                return 1; // date1 lớn hơn date2
            } else {
                return 0; // date1 bằng date2
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}