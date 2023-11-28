package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Adapter.Invoice_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.OrderAdapter;
import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Dao.OrderItemDao;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.R;

public class Order_Fragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    Toolbar toolbar;
    OrderDao orderDao;
    Invoice_Adapter invoiceAdapter;
    ArrayList<DetailsOrder> list = new ArrayList<>();
    OrderItemDao orderItemDao;

    public static final String TAG = "Order_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        orderDao = new OrderDao(getContext());
        toolbar = view.findViewById(R.id.toolbar_order);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Đơn mua");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderItemDao = new OrderItemDao(getContext());
        list = orderItemDao.SelectAllJoin();
        recyclerView = view.findViewById(R.id.RecyclerView_Order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        invoiceAdapter = new Invoice_Adapter(getContext(), list);
        recyclerView.setAdapter(invoiceAdapter);
        return view;
    }
}