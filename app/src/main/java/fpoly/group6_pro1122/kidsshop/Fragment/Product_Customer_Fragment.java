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

public class Product_Customer_Fragment extends Fragment {

    View view;
    Product_Customer_Adapter productCustomerAdapter;
    ArrayList<Product> list = new ArrayList<>();
    ProductDao productDao;
    RecyclerView recyclerView;
    CartItemDao cartItemDao;
    ArrayList<CartItem> list_cartItems = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        productDao = new ProductDao(getContext());
        list = productDao.SelectAll();
        cartItemDao = new CartItemDao(getContext());
        list_cartItems = cartItemDao.SelectAll();
        productCustomerAdapter = new Product_Customer_Adapter(getContext() ,list, list_cartItems);
        recyclerView = view.findViewById(R.id.recyclerView_Product_customer);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(productCustomerAdapter);
        return view;
    }
}