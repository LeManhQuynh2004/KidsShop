package fpoly.group6_pro1122.kidsshop.Fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Adapter.TopAdapter;
import fpoly.group6_pro1122.kidsshop.Dao.StatisticalDao;
import fpoly.group6_pro1122.kidsshop.Model.Top;
import fpoly.group6_pro1122.kidsshop.R;

public class Revenue_Statistics_Fragment extends Fragment {
    View view;
    EditText ed_start_date_statistics, ed_end_date_statistics;
    TextView tv_doanhThu;
    StatisticalDao statisticalDao;
    String startDate, endDate;
    RecyclerView recyclerView;
    ArrayList<Top> list = new ArrayList<>();
    TopAdapter topAdapter;
    Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_revenue__statistics, container, false);
        ed_start_date_statistics = view.findViewById(R.id.ed_start_date_statistics);
        ed_end_date_statistics = view.findViewById(R.id.ed_end_date_statistics);
        toolbar = view.findViewById(R.id.toolbar_revenue_statistics);
        CreateToolbar();
        statisticalDao = new StatisticalDao(getContext());
        tv_doanhThu = view.findViewById(R.id.tv_doanhThu);
        recyclerView = view.findViewById(R.id.RecyclerView_DoanhSo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.img_statistics_start_date).setOnClickListener(view1 -> {
            showDatePickerDialog(ed_start_date_statistics);
        });
        view.findViewById(R.id.img_statistics_end_date).setOnClickListener(view1 -> {
            showDatePickerDialog(ed_end_date_statistics);
        });
        tv_doanhThu.setOnClickListener(view1 -> {
            startDate = ed_start_date_statistics.getText().toString().trim();
            endDate = ed_end_date_statistics.getText().toString().trim();
            CreateRecyclerView();
            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            } else {
                int doanhThu = statisticalDao.DoanhThu(startDate, endDate);
                tv_doanhThu.setText("$" + doanhThu);
            }
        });
        return view;
    }
    private void CreateRecyclerView() {
        list = statisticalDao.ThongKeBanChay2(startDate, endDate);
        topAdapter = new TopAdapter(getContext(), list);
        recyclerView.setAdapter(topAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        itemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(itemDecoration);
    }
    private void showDatePickerDialog(EditText editText) {
        //Calender lấy ngày tháng hiện tại của máy tính
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //DatepickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(selectedDateCalendar.getTime());
                    editText.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thống kê doanh số");
    }
}