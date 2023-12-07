package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CategoryUser_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CategoryDao;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.R;


public class Category_User_Fragment extends Fragment {
    CategoryUser_Adapter adapter;
    CategoryDao categoryDao;
    RecyclerView recyclerView;
    ArrayList<Category> list = new ArrayList<>();
    ArrayList<Category> temp_list = new ArrayList<>();
    EditText ed_search_Category_Customer;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryDao = new CategoryDao(getContext());
        list = categoryDao.getAll();
        temp_list = categoryDao.getAll();
        toolbar = view.findViewById(R.id.toolbar_category_user);
        ed_search_Category_Customer = view.findViewById(R.id.ed_search_Category_Customer);
        CreateToolbar();
        adapter = new CategoryUser_Adapter(list,getContext());
        recyclerView = view.findViewById(R.id.recyclerView_Category_user);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        SearchCategory();
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thể loại sản phẩm");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_Fragment()).commit();
        });
    }
    private void SearchCategory() {
        ed_search_Category_Customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                list.clear();

                for (Category category : temp_list) {
                    if (category.getName().contains(query)) {
                        list.add(category);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}