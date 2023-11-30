package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Adapter.Invoice_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.OrderAdapter;
import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Dao.OrderItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Order_Fragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    Toolbar toolbar;
    OrderDao orderDao;
    Invoice_Adapter invoiceAdapter;
    ArrayList<DetailsOrder> list = new ArrayList<>();
    OrderItemDao orderItemDao;
    UserDao userDao;

    public static final String TAG = "Order_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        userDao = new UserDao(getContext());
        if(email != null){
            User user = userDao.SelectID(email);
            view = inflater.inflate(R.layout.fragment_invoice, container, false);
            recyclerView = view.findViewById(R.id.RecyclerView_Invoice);
            toolbar = view.findViewById(R.id.toolbar_invoice);
            CreateToolbar();
            orderItemDao = new OrderItemDao(getContext());
            Log.e(TAG, "onCreateView: "+user.getId());
            list = orderItemDao.SelectUserJoin(String.valueOf(user.getId()));
            invoiceAdapter = new Invoice_Adapter(getContext(),list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(invoiceAdapter);
        }else{
            Log.e(TAG, "onCreateView: "+"null");
        }
        return view;
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Đơn mua");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalInf_Fragment()).commit();
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.BottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.menu_account);
            menuItem.setChecked(true);
        });
    }
}