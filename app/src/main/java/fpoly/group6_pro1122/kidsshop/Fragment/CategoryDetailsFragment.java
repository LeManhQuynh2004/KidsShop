package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Product_Customer_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;


public class CategoryDetailsFragment extends Fragment {

    ProductDao productDao;
    RecyclerView recyclerView;
    Toolbar toolbar;
    Product_Customer_Adapter productCustomerAdapter;
    ArrayList<CartItem> list_CartItem = new ArrayList<>();
    CartItemDao cartItemDao;

    ArrayList<Product> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewDetails_category);
        productDao = new ProductDao(getContext());
        toolbar = view.findViewById(R.id.toolbar_category_details);
        CreateToolbar();
        cartItemDao = new CartItemDao(getContext());
        Bundle bundle = getArguments();
        if (bundle!=null){
            int id = bundle.getInt("id",0);
            list = productDao.findID(String.valueOf(id)); // lấy danh sách sản phẩm thông qua id
            list_CartItem = cartItemDao.SelectAll();
            productCustomerAdapter = new Product_Customer_Adapter(getContext(),list,list_CartItem);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(productCustomerAdapter);
        }
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Sản phẩm");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Category_User_Fragment()).commit();
        });
    }
}