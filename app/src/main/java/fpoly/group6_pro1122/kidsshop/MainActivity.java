package fpoly.group6_pro1122.kidsshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Fragment.CartFragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Category_User_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Home_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.InformationOrder_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Login_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.PersonalInf_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Customer_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.StatisticalFragment;
import fpoly.group6_pro1122.kidsshop.Fragment.User_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Voucher_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.WishList_Fragment;
import fpoly.group6_pro1122.kidsshop.Model.User;

public class MainActivity extends AppCompatActivity {
    UserDao userDao;
    BottomNavigationView bottomNavigationView;
    String email;
    public static final String TAG = "ManActivity";
    ArrayList<User> list = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = new UserDao(this);
        list = userDao.SelectAll();
        Log.e(TAG, "onCreate: " + list.size());
        bottomNavigationView = findViewById(R.id.BottomNavigationView);
        sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        Log.e(TAG, "onCreate: " + email);
        if (email.equals("")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        } else {
            User user  = userDao.SelectID(email);
            if (user.getRole() == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Product_Admin_Fragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = item.getItemId();
                Fragment fragment = null;
                sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
                String email = sharedPreferences.getString("EMAIL", "");
                if (email.equals("")){
                    if (position == R.id.menu_home) {
                        fragment = new Home_Fragment();
                    } else if (position == R.id.menu_category) {
                        fragment = new Category_User_Fragment();
                    } else {
                        fragment = new PersonalInf_Fragment();
                    }
                }else{
                    User user_bottom  = userDao.SelectID(email);
                    if (user_bottom.getRole() == 0) {
                        if (position == R.id.menu_home) {
                            fragment = new Product_Admin_Fragment();
                        } else if (position == R.id.menu_category) {
                            fragment = new Category_Admin_Fragment();
                        } else if (position == R.id.menu_bag) {
                            fragment = new Voucher_Admin_Fragment();
                        } else if (position == R.id.menu_wishlist) {
                            fragment = new StatisticalFragment();
                        } else {
                            fragment = new PersonalInf_Fragment();
                        }
                    } else {
                        if (position == R.id.menu_home) {
                            fragment = new Home_Fragment();
                        } else if (position == R.id.menu_category) {
                            fragment = new Category_User_Fragment();
                        } else if (position == R.id.menu_bag) {
                            fragment = new CartFragment();
                        } else if (position == R.id.menu_wishlist) {
                            fragment = new WishList_Fragment();
                        } else {
                            fragment = new PersonalInf_Fragment();
                        }
                    }
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }

                return true;
            }
        });
    }

    public void disableBottomNavigationView() {//xóa bottom khi fragment không cần dùng đến
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        bottomNavigationView.setVisibility(View.GONE);
    }
}