package com.example.qlct.Budget;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlct.Fragment.MyDialogFragment;
import com.example.qlct.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetRenewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetRenewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    TextView apply;
    String date;
    String status;

    public BudgetRenewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetRenewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetRenewFragment newInstance(String param1, String param2) {
        BudgetRenewFragment fragment = new BudgetRenewFragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_budget_renew, container, false);
        listView=view.findViewById(R.id.listViewRN);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Dayly");
        arrayList.add("Weekly");
        arrayList.add("Monthly");
        arrayList.add("Yearly");
        arrayList.add("Custom");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                arrayList
        );
        listView.setAdapter(adapter);
        apply=view.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tham chiếu đến MyDialogFragment
                MyDialogFragment dialogFragment = (MyDialogFragment) getParentFragment();
                if (dialogFragment != null) {
                    if (getActivity() instanceof AddBudget) {
                        AddBudget grandpa = (AddBudget) getActivity();
                        grandpa.SetData(date);
                        grandpa.SetType("Renew");
                        grandpa.SetDateUnit(status);

                    }
                    else
                    {
                        AdjustBudget grandpa = (AdjustBudget) getActivity();
                        grandpa.SetData(date);
                        grandpa.SetType("Renew");
                        grandpa.SetDateUnit(status);
                    }
                    // Đóng dialog
                    dialogFragment.dismiss();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                date= arrayList.get(position);
                status=arrayList.get(position);
                String type=parent.getItemAtPosition(position).toString();
                if(!(type.equals("Daily")||type.equals("Weekly")||type.equals("Monthly")||type.equals("Yearly")))
                {
                    status="Custom";
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
                                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    if(isEndDateLessThanCurrent(". - "+format.format(calendar.getTime()))==true)
                                    {
                                        Toast.makeText(getContext(), "Ngày không được nhỏ hơn hiện tại", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        arrayList.set(position, format.format(calendar.getTime()));
                                        adapter.notifyDataSetChanged();
                                        date= arrayList.get(position);
                                    }
                                }
                            }, year, month, day);
                    // Hiển thị DatePickerDialog
                    datePickerDialog.show();
                }
            }
        });
        return view;
    }
}