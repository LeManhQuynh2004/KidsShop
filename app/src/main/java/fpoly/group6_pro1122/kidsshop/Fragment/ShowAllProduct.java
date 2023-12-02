package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Product_Customer_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class ShowAllProduct extends Fragment {
    View view;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ProductDao productDao;
    Product_Customer_Adapter productCustomerAdapter;
    ArrayList<Product> list = new ArrayList<>();
    CartItemDao cartItemDao;
    ArrayList<CartItem> list_CartItem = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_details, container, false);
        toolbar = view.findViewById(R.id.toolbar_category_details);
        productDao = new ProductDao(getContext());
        cartItemDao = new CartItemDao(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewDetails_category);
        list = productDao.SelectAll();
        list_CartItem = cartItemDao.SelectAll();
        CreateToolbar();
        productCustomerAdapter = new Product_Customer_Adapter(getContext(),list,list_CartItem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productCustomerAdapter);
        return view;
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Danh sách sản phẩm");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        });
    }
}
