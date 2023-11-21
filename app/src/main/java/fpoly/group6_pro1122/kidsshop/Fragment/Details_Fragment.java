package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CartItem_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Dao.WishListDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.Model.WishList;
import fpoly.group6_pro1122.kidsshop.R;


public class Details_Fragment extends Fragment {

    View view;
    Product product;
    TextView tv_name, tv_price, tv_quantity, tv_signal, tv_quantity_details, tv_sum;
    UserDao userDao;
    WishListDao wishListDao;
    CartItemDao cartItemDao;
    int quantity = 0;
    ImageView img_product;
    CartItem_Adapter cartItemAdapter;
    ArrayList<CartItem> list = new ArrayList<>();
    ImageView img_wishlist;
    WishList findwishList;
    public static final String TAG = "Details_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_, container, false);
        cartItemDao = new CartItemDao(getContext());
        list = cartItemDao.SelectAll();
        wishListDao = new WishListDao(getContext());
        cartItemAdapter = new CartItem_Adapter(getContext(), list);
        tv_quantity = view.findViewById(R.id.tv_Quantity_CartItem);
        tv_quantity.setText(list.size() + "");
        tv_name = view.findViewById(R.id.tv_name_details_product);
        tv_price = view.findViewById(R.id.tv_price_details_product);
        tv_signal = view.findViewById(R.id.bt_signal_details);
        tv_quantity_details = view.findViewById(R.id.tv_quantity_details);
        tv_sum = view.findViewById(R.id.bt_sum_quantity_details);

        view.findViewById(R.id.bt_back_home).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        });
        view.findViewById(R.id.img_send_bag).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
        });
        tv_sum.setOnClickListener(view1 -> {
            quantity++;
            tv_quantity_details.setText(quantity + "");
        });
        tv_signal.setOnClickListener(view1 -> {
            if (quantity > 1) {
                quantity--;
                tv_quantity_details.setText(quantity + "");
            } else {
                Toast.makeText(getContext(), "Không thực hiện được", Toast.LENGTH_SHORT).show();
            }
        });
        userDao = new UserDao(getContext());
        cartItemDao = new CartItemDao(getContext());
        list = cartItemDao.SelectAll();
        img_product = view.findViewById(R.id.img_details);

        Bundle bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {
                tv_name.setText(product.getProduct_name());
                tv_price.setText("$" + product.getProduct_price());
                Glide.with(requireContext()).load(product.getImage()).placeholder(R.drawable.productimg).into(img_product);
            }
        }
        img_wishlist = view.findViewById(R.id.img_wishlist);
        findwishList = wishListDao.getWishListByProductId(product.getProduct_id());
        if (findwishList != null) {
            img_wishlist.setImageResource(R.drawable.heartred);
        } else {
            img_wishlist.setImageResource(R.drawable.heart);
        }
        img_wishlist.setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            if (email.equals("")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Login_Fragment()).commit();
            }else {
                User user = userDao.SelectID(email);
                WishList wishList = new WishList();
                wishList.setProduct_id(product.getProduct_id());
                wishList.setUser_id(user.getId());
                wishList.setQuantity(1);
                WishList findwishList2 = wishListDao.getWishListByProductId(product.getProduct_id());
                if (findwishList2 == null) {
                    if (wishListDao.insertData(wishList)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        img_wishlist.setImageResource(R.drawable.heartred);
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (wishListDao.deleteData(findwishList2)) {
                        Toast.makeText(getContext(), R.string.delete_success, Toast.LENGTH_SHORT).show();
                        img_wishlist.setImageResource(R.drawable.heart);
                    } else {
                        Toast.makeText(getContext(), R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.bt_addCartItem).setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            if (email.equals("")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Login_Fragment()).commit();
            }else {
                User user = userDao.SelectID(email);
                CartItem cartItem = new CartItem();
                cartItem.setUser_id(user.getId());
                cartItem.setProduct_id(product.getProduct_id());
                cartItem.setStatus(0);
                cartItem.setQuantity(Integer.parseInt(tv_quantity_details.getText().toString()));
                cartItem.setTotal_price(Integer.parseInt(tv_quantity_details.getText().toString()) * product.getProduct_price());
                CartItem findCartItem = cartItemDao.getCartItemByProductId(product.getProduct_id());
                if (findCartItem != null) {
                    findCartItem.setQuantity(findCartItem.getQuantity() + Integer.parseInt(tv_quantity_details.getText().toString()));
                    findCartItem.setTotal_price(findCartItem.getQuantity() * product.getProduct_price());
                    if (cartItemDao.updateData(findCartItem)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (cartItemDao.insertData(cartItem)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        list.add(cartItem);
                        tv_quantity.setText(String.valueOf(list.size()));
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}