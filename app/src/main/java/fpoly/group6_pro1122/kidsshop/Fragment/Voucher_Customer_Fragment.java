package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Voucher_Customer_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Dao.VoucherDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Voucher_Customer_Fragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    VoucherDao voucherDao;
    Toolbar toolbar;
    UserDao userDao;
    ArrayList<Voucher> list = new ArrayList<>();
    Voucher_Customer_Adapter voucherCustomerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_voucher_customer, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_Voucher_Customer);
        voucherDao = new VoucherDao(getContext());
        userDao = new UserDao(getContext());
        toolbar = view.findViewById(R.id.toolbar_voucher);
        CreateToolbar();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        if(email != null){
            User user = userDao.SelectID(email);
            list = voucherDao.SelectUser_ID(String.valueOf(user.getId()));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        voucherCustomerAdapter = new Voucher_Customer_Adapter(getContext(),list);
        if(list.size() == 0){
            Toast.makeText(getContext(), "Hiện tại bạn chưa sở hữu voucher", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(voucherCustomerAdapter);
        return view;
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Chọn Voucher");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Payment_Fragment()).commit();
        });
    }
}