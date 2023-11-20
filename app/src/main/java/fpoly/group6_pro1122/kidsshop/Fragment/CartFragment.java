package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CartItem_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.Cart;
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
    TextView textView;
    UserDao userDao;

    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        toolbar = view.findViewById(R.id.Toolbar_CartItem);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Rỏ hàng của bạn");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = view.findViewById(R.id.listViewCartItem);
        textView = view.findViewById(R.id.tv_total_price_cart);
        cartItemDao = new CartItemDao(getContext());
        userDao = new UserDao(getContext());
        list = cartItemDao.SelectAll();
        cartItemAdapter = new CartItem_Adapter(getContext(), list);
        listView.setAdapter(cartItemAdapter);
        cartItemAdapter.setItemClickListener(new OnChange_Price() {
            @Override
            public void UpdateItem(int total_price) {
                textView.setText("$" + total_price);
            }
        });
        return view;
    }
}