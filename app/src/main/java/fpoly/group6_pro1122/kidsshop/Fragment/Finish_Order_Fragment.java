package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Product_Customer_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class Finish_Order_Fragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<Product> list = new ArrayList<>();
    ArrayList<CartItem> list_CartItem = new ArrayList<>();
    ProductDao productDao;
    CartItemDao cartItemDao;
    Product_Customer_Adapter productCustomerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_finish__order, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_Product_order);
        productDao = new ProductDao(getContext());
        cartItemDao = new CartItemDao(getContext());
        list = productDao.SelectAll();
        list_CartItem = cartItemDao.SelectAll();
        productCustomerAdapter = new Product_Customer_Adapter(getContext(),list,list_CartItem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productCustomerAdapter);
        view.findViewById(R.id.bt_send_home).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_Fragment()).commit();
        });
        view.findViewById(R.id.bt_send_order).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Order_Fragment()).commit();
        });
        return view;
    }
}