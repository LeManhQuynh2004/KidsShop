package fpoly.group6_pro1122.kidsshop.Fragment;

import android.graphics.Color;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.TopAdapter;
import fpoly.group6_pro1122.kidsshop.Dao.StatisticalDao;
import fpoly.group6_pro1122.kidsshop.Model.Top;
import fpoly.group6_pro1122.kidsshop.R;

public class StatisticalFragment extends Fragment {
    View view;
    StatisticalDao statisticalDao;
    ArrayList<Top> list = new ArrayList<>();
    Toolbar toolbar;
    RecyclerView recyclerView;
    TopAdapter topAdapter;
    ArrayList<BarEntry> entries = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistical, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_Top);
        toolbar = view.findViewById(R.id.Toolbar_Statistical);
        statisticalDao = new StatisticalDao(getContext());
        list = statisticalDao.ThongKeBanChay();
        topAdapter = new TopAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(topAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        itemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(itemDecoration);
        CreateToolbar();

        BarChart barChart = view.findViewById(R.id.barChart);
        for (int i = 0; i < list.size(); i++) {
            entries.add(new BarEntry(i,list.get(i).getQuantity()));
        }
        BarDataSet dataSet = new BarDataSet(entries, "Sản phẩm bán chạy");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(20f);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);

        barChart.setDrawValueAboveBar(true);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        return view;
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thống kê bán chạy");
    }
}