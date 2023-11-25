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
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.WishListAdapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Dao.WishListDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.Model.WishList;
import fpoly.group6_pro1122.kidsshop.R;

public class WishList_Fragment extends Fragment {
    View view;
    ListView listView;
    WishListDao wishListDao;
    ArrayList<WishList> list = new ArrayList<>();
    WishListAdapter wishListAdapter;
    UserDao userDao;
    public static final String TAG = "WishList_Fragment";
    Toolbar toolbar;
    CartItemDao cartItemDao;
    CheckBox chk_status;
    ProductDao productDao;
    String email;
    SharedPreferences sharedPreferences;

    private void MinMap() {
        toolbar = view.findViewById(R.id.Toolbar_WishList);
        chk_status = view.findViewById(R.id.chk_All_Wishlist);
        userDao = new UserDao(getContext());
        cartItemDao = new CartItemDao(getContext());
        productDao = new ProductDao(getContext());
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
        listView = view.findViewById(R.id.listViewWishlist);
        wishListDao = new WishListDao(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        MinMap();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Danh sách yêu thích");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (email.equals("")) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
        } else {
            CreateListViewWishList();
            chk_status.setOnClickListener(view1 -> {
                if (chk_status.isChecked()) {
                    UpdateWishList(1);
                } else {
                    UpdateWishList(0);
                }
            });
            view.findViewById(R.id.bt_addAllCart).setOnClickListener(view1 -> {
                int count = 0;
                for (WishList item : list) {
                    if (item.getStatus() == 1) {
                        count++;
                    }
                }
                if (count > 0) {
                    User user = userDao.SelectID(email);

                    if (user != null) {
                        for (WishList item : list) {
                            if (item.getStatus() == 1) {
                                Product product = productDao.SelectID(String.valueOf(item.getProduct_id()));
                                CartItem findCartItem = cartItemDao.getCartItemByProductId(user.getId(), product.getProduct_id());
                                if (findCartItem == null) {
                                    CartItem cartItem = new CartItem();
                                    cartItem.setUser_id(item.getUser_id());
                                    cartItem.setProduct_id(item.getProduct_id());
                                    cartItem.setQuantity(1);
                                    cartItem.setTotal_price(1 * product.getProduct_price());
                                    cartItemDao.insertData(cartItem);
                                    wishListDao.deleteData(item);
                                } else {
                                    Toast.makeText(getContext(), "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        wishListAdapter.notifyDataSetChanged();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WishList_Fragment()).commit();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn sản phẩm cần thêm", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }

    private void CreateListViewWishList() {
        list = wishListDao.SelectAll();
        wishListAdapter = new WishListAdapter(getContext(), list);
        listView.setAdapter(wishListAdapter);
    }

    private void UpdateWishList(int i) {
        for (WishList wishList : list) {
            wishList.setStatus(i);
            wishListDao.updateData(wishList);
            wishListAdapter.notifyDataSetChanged();
        }
    }
}