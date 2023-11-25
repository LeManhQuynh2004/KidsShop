package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Product_Customer_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Home_Fragment extends Fragment {
    View view;
    ImageView imageShow;
    int index;
    private Handler handler;
    public static final String TAG = "Home_Fragment";
    RecyclerView recyclerView_new, recyclerView_discount, recyclerView_suggest;
    ProductDao productDao;
    ArrayList<Product> list_product = new ArrayList<>();
    ArrayList<Product> list_product_new = new ArrayList<>();
    ArrayList<Product> list_product_discount = new ArrayList<>();
    int Columns = 2;
    UserDao userDao;
    CartItemDao cartItemDao;
    ArrayList<CartItem> list_CartItem = new ArrayList<>();
    TextView txt_quantity;
    Product_Customer_Adapter productCustomerAdapter, productCustomerAdapter_new, productCustomerAdapter_discount;
    int[] imageResIds = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView_new = view.findViewById(R.id.recyclerView_Product_new);
        userDao = new UserDao(getContext());
        recyclerView_discount = view.findViewById(R.id.recyclerView_Product_discount);
        recyclerView_suggest = view.findViewById(R.id.recyclerView_Product_suggest);
        productDao = new ProductDao(getContext());
        ShowQuantityCartItem();
        view.findViewById(R.id.img_bag_home).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.BottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.menu_bag);
            menuItem.setChecked(true);
        });
        list_product = productDao.SelectAll();
        list_product_new = productDao.SelectAllNew(1);
        Log.e(TAG, "onCreateView: " + list_product_new.size());
        list_product_discount = productDao.SelectAllNew(2);
        CreateAdapter();
        layout_HORIZONTAL(recyclerView_suggest, productCustomerAdapter_new);
        layout_HORIZONTAL(recyclerView_discount, productCustomerAdapter_discount);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), Columns);
        recyclerView_new.setLayoutManager(gridLayoutManager);
        recyclerView_new.setAdapter(productCustomerAdapter);

        imageShow = view.findViewById(R.id.imageShow);
        imageShow.setImageResource(R.drawable.banner1);
        handler = new Handler(Looper.getMainLooper());
        startRead();
        return view;
    }
    private void CreateAdapter(){
        productCustomerAdapter = new Product_Customer_Adapter(getContext(), list_product, list_CartItem);
        productCustomerAdapter_new = new Product_Customer_Adapter(getContext(), list_product_new, list_CartItem);
        productCustomerAdapter_discount = new Product_Customer_Adapter(getContext(), list_product_discount, list_CartItem);
    }

    private void layout_HORIZONTAL(RecyclerView recyclerView, Product_Customer_Adapter productCustomerAdapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productCustomerAdapter);
    }

    private void ShowQuantityCartItem() {
        txt_quantity = view.findViewById(R.id.tv_Quantity_CartItem_Product);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        Log.e(TAG, "ShowQuantityCartItem: " + email);
        if (email.equals("")) {
            txt_quantity.setText(0+"");
        } else {
            User user = userDao.SelectID(email);
            if (user != null) {
                cartItemDao = new CartItemDao(getContext());
                list_CartItem = cartItemDao.SelectUser(String.valueOf(user.getId()));
                txt_quantity.setText(list_CartItem.size() + "");
            }
        }
    }

    private void startRead() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play();
                startRead();
            }
        }, 3000);
    }

    public void play() {
        index++;
        if (index >= imageResIds.length) {
            index = 0;
        }
        imageShow.setImageResource(imageResIds[index]);
    }
}