package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    public static final String TAG = "CartFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        toolbar = view.findViewById(R.id.Toolbar_CartItem);
        userDao = new UserDao(getContext());
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Giỏ hàng của bạn");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
        Log.e(TAG, "onCreateView: " + email);
        if (email.equals("")) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
        } else {
            User user = userDao.SelectID(email);
            Log.e(TAG, "onCreateView: "+email);
            if (user != null) {
                listView = view.findViewById(R.id.listViewCartItem);
                tv_total_price = view.findViewById(R.id.tv_total_price_cart);
                Log.e(TAG, "TextView: " + tv_total_price.getText().toString());
                cartItemDao = new CartItemDao(getContext());
                userDao = new UserDao(getContext());
                list = cartItemDao.SelectUser(String.valueOf(user.getId()));
                Log.e(TAG, "Size: "+list.size());
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
                checkBox = view.findViewById(R.id.chk_All);
                checkBox.setOnClickListener(view1 -> {
                    if (checkBox.isChecked()) {
                        for (int i = 0; i < list.size(); i++) {
                            CartItem cartItem = list.get(i);
                            cartItem.setStatus(1);
                            cartItemDao.updateData(cartItem);
                            cartItemAdapter.notifyDataSetChanged();
                        }
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            Log.e(TAG, "onCreateView: " + checkBox.isChecked());
                            CartItem cartItem = list.get(i);
                            cartItem.setStatus(0);
                            cartItemDao.updateData(cartItem);
                            cartItemAdapter.notifyDataSetChanged();
                        }
                    }
                });
                Total_Price();
            }
        }
        return view;
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