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

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CartItem_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Intefaces.OnChange_Price;
import fpoly.group6_pro1122.kidsshop.R;

public class CartFragment extends Fragment {
    View view;
    CartItemDao cartItemDao;
    ListView listView;
    ArrayList<CartItem> list = new ArrayList<>();
    CartItem_Adapter cartItemAdapter;
    TextView textView;
    CheckBox checkBox;
    UserDao userDao;
    Toolbar toolbar;
    public static final String TAG = "CartFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        toolbar = view.findViewById(R.id.Toolbar_CartItem);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Giỏ hàng của bạn");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        Log.e(TAG, "onCreateView: "+email);
        if (email.equals("")) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Login_Fragment()).commit();
        } else {
            listView = view.findViewById(R.id.listViewCartItem);
            textView = view.findViewById(R.id.tv_total_price_cart);
            cartItemDao = new CartItemDao(getContext());
            userDao = new UserDao(getContext());
            list = cartItemDao.SelectAll();
            cartItemAdapter = new CartItem_Adapter(getContext(), list);
            listView.setAdapter(cartItemAdapter);
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
        return view;
    }
    private void Total_Price(){
        cartItemAdapter.setItemClickListener(new OnChange_Price() {
            @Override
            public void UpdateItem(int total_price) {
                textView.setText("$" + total_price);
            }
        });
    }
}