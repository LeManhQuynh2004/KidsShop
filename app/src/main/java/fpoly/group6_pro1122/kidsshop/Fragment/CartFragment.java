package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CartItem_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Intefaces.OnChange_Price;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class CartFragment extends Fragment {
    View view;
    CartItemDao cartItemDao;
    ListView listView;
    ArrayList<CartItem> list = new ArrayList<>();
    CartItem_Adapter cartItemAdapter;
    TextView tv_total_price;
    CheckBox checkBox;
    UserDao userDao;
    Toolbar toolbar;
    String email;
    SharedPreferences sharedPreferences;
    public static final String TAG = "CartFragment";

    private void MinMap() {
        toolbar = view.findViewById(R.id.Toolbar_CartItem);
        userDao = new UserDao(getContext());
        listView = view.findViewById(R.id.listViewCartItem);
        tv_total_price = view.findViewById(R.id.tv_total_price_cart);
        cartItemDao = new CartItemDao(getContext());
        userDao = new UserDao(getContext());
        checkBox = view.findViewById(R.id.chk_All);
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        MinMap();
        CreateToolbar();

        if (email.equals("")) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
        } else {
            User user = userDao.SelectID(email);
            if (user != null) {
                list = cartItemDao.SelectUser(String.valueOf(user.getId()));
                cartItemAdapter = new CartItem_Adapter(getContext(), list);
                listView.setAdapter(cartItemAdapter);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setStatus(0);
                    cartItemDao.updateData(list.get(i));
                }
                view.findViewById(R.id.bt_payment).setOnClickListener(view1 -> {
                    if (list.size() == 0) {
                        Toast.makeText(getContext(), "Danh sách trống", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getStatus() == 0) {
                                Toast.makeText(getContext(), "Vui lòng nhấn chọn sản phẩm", Toast.LENGTH_SHORT).show();
                            } else {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Payment_Fragment()).commit();
                            }
                        }
                    }
                });

                checkBox.setOnClickListener(view1 -> {
                    if (checkBox.isChecked()) {
                        UpdateCartItem(1);
                    } else {
                       UpdateCartItem(0);
                    }
                });
                Total_Price();
            }
        }
        return view;
    }

    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Giỏ hàng của bạn");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_Fragment()).commit();
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.BottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.menu_home);
            menuItem.setChecked(true);
        });
    }

    private void UpdateCartItem(int i) {
        for (CartItem cartItem:list) {
            cartItem.setStatus(i);
            cartItemDao.updateData(cartItem);
            cartItemAdapter.notifyDataSetChanged();
        }
    }

    private void Total_Price() {
        cartItemAdapter.setItemClickListener(new OnChange_Price() {
            @Override
            public void UpdateItem(int total_price) {
                tv_total_price.setText("$" + total_price);
                Log.e(TAG, "UpdateItem: " + total_price);
                Log.e(TAG, "ValueTextview: " + tv_total_price.getText().toString());
            }
        });
    }
}