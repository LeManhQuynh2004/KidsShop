package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;


public class Details_Fragment extends Fragment {

    View view;
    Product product;
    TextView tv_name,tv_price,tv_quantity,tv_signal,tv_quantity_details,tv_sum;
    UserDao userDao;
    CartItemDao cartItemDao;
    int quantity = 0;
    ImageView img_product;
    ArrayList<CartItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_, container, false);

        tv_name = view.findViewById(R.id.tv_name_details_product);
        tv_price = view.findViewById(R.id.tv_price_details_product);
        tv_quantity = view.findViewById(R.id.tv_Quantity_CartItem);
        tv_signal = view.findViewById(R.id.bt_signal_details);
        tv_quantity_details = view.findViewById(R.id.tv_quantity_details);
        tv_sum = view.findViewById(R.id.bt_sum_quantity_details);
        tv_sum.setOnClickListener(view1 -> {
            quantity++;
            tv_quantity_details.setText(quantity + "");
        });
        tv_signal.setOnClickListener(view1 -> {
            if (quantity > 0) {
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

        view.findViewById(R.id.bt_addCartItem).setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            User user = userDao.SelectID(email);
            CartItem cartItem = new CartItem();
            cartItem.setUser_id(user.getId());
            cartItem.setProduct_id(product.getProduct_id());

            CartItemDao cartItemDao = new CartItemDao(getContext());

            CartItem existingCartItem = cartItemDao.getCartItemByProductId(cartItem.getProduct_id());
            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                if (cartItemDao.updateData(existingCartItem)) {
                    Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.update_not_success, Toast.LENGTH_SHORT).show();
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
        });
        return view;
    }
}