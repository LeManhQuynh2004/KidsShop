package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Product_Customer_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.Cart;
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
    RecyclerView recyclerView;
    ProductDao productDao;
    ArrayList<Product> list_product = new ArrayList<>();
    int Columns = 2;
    CartItemDao cartItemDao;
    ArrayList<CartItem> list_CartItem = new ArrayList<>();
    TextView txt_quantity;
    UserDao userDao;
    ArrayList<Cart> list_cart = new ArrayList<>();
    User user;
    Product_Customer_Adapter productCustomerAdapter;
    int[] imageResIds = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,R.drawable.banner4};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_Product_customer);
        productDao = new ProductDao(getContext());
        ShowQuantityCartItem();
        view.findViewById(R.id.img_bag_home).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragment()).commit();
        });
        list_product = productDao.SelectAll();
        productCustomerAdapter = new Product_Customer_Adapter(getContext(),list_product,list_CartItem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),Columns);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productCustomerAdapter);
        imageShow = view.findViewById(R.id.imageShow);
        imageShow.setImageResource(R.drawable.banner1);
        handler = new Handler(Looper.getMainLooper());
        startRead();
        return view;
    }

    private void ShowQuantityCartItem() {
        txt_quantity = view.findViewById(R.id.tv_Quantity_CartItem_Product);
        cartItemDao = new CartItemDao(getContext());
        list_CartItem = cartItemDao.SelectAll();
        txt_quantity.setText(list_CartItem.size()+"");
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
    public void play (){
        index++;
        if(index >= imageResIds.length){
            index = 0;
        }
        imageShow.setImageResource(imageResIds[index]);
    }
}