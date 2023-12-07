package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    EditText ed_search_Product_Show;

    Product_Customer_Adapter productCustomerAdapter;
    ArrayList<Product> list = new ArrayList<>();
    ArrayList<Product> temp_list = new ArrayList<>();
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
        temp_list = productDao.SelectAll();
        ed_search_Product_Show = view.findViewById(R.id.ed_search_Product_Show);
        list_CartItem = cartItemDao.SelectAll();
        CreateToolbar();
        productCustomerAdapter = new Product_Customer_Adapter(getContext(), list, list_CartItem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productCustomerAdapter);
        SearchProduct();
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

    private void SearchProduct() {
        ed_search_Product_Show.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                list.clear();

                for (Product product : temp_list) {
                    if (product.getProduct_name().contains(query)) {
                        list.add(product);
                    }
                }
                productCustomerAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
