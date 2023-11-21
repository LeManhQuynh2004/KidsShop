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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.WishListAdapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.WishListDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.WishList;
import fpoly.group6_pro1122.kidsshop.R;

public class WishList_Fragment extends Fragment {
    View view;
    ListView listView;
    WishListDao wishListDao;
    ArrayList<WishList> list = new ArrayList<>();
    WishListAdapter wishListAdapter;
    public static final String TAG = "WishList_Fragment";
    Toolbar toolbar;
    CartItemDao cartItemDao;
    ProductDao productDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        toolbar = view.findViewById(R.id.Toolbar_WishList);
        cartItemDao = new CartItemDao(getContext());
        productDao = new ProductDao(getContext());
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Danh sách yêu thích");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        Log.e(TAG, "onCreateView: "+email);
        if (email.equals("")) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Login_Fragment()).commit();
        } else {
            listView = view.findViewById(R.id.listViewWishlist);
            wishListDao = new WishListDao(getContext());
            list = wishListDao.SelectAll();
            Log.d(TAG, "onCreateView: " + list.size());
            wishListAdapter = new WishListAdapter(getContext(), list);
            listView.setAdapter(wishListAdapter);
            view.findViewById(R.id.bt_addAllCart).setOnClickListener(view1 -> {
                for (int i = 0; i < list.size(); i++) {
                    Product product = productDao.SelectID(String.valueOf(list.get(i).getProduct_id()));
                    CartItem findcartItem = cartItemDao.getCartItemByProductId(product.getProduct_id());
                    if (findcartItem == null) {
                        CartItem cartItem = new CartItem();
                        cartItem.setUser_id(list.get(i).getUser_id());
                        cartItem.setProduct_id(list.get(i).getProduct_id());
                        cartItem.setQuantity(1);
                        cartItem.setTotal_price(1 * product.getProduct_price());
                        cartItemDao.insertData(cartItem);
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        wishListDao.deleteData(list.get(i));
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WishList_Fragment()).commit();
                    } else {
                        Toast.makeText(getContext(), "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                }
                wishListAdapter.notifyDataSetChanged();
            });
        }
        return view;
    }
}