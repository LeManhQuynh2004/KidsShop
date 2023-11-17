package fpoly.group6_pro1122.kidsshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Fragment.Category_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Home_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Model.User;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String email;
    UserDao userDao;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = new UserDao(this);
        Intent intent = getIntent();
        if(intent != null){
            email =  intent.getStringExtra("email");
        }
        user = userDao.SelectID(email);

        if(user.getRole() == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Product_Admin_Fragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_Fragment()).commit();
        }
        bottomNavigationView = findViewById(R.id.BottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = item.getItemId();
                Fragment fragment = null;
                if(position == R.id.menu_home){
                    if(user.getRole() == 0){
                        fragment = new Product_Admin_Fragment();
                    }else{
                        fragment = new Home_Fragment();
                    }
                }else if(position == R.id.menu_category){
                    if(user.getRole() == 0){
                        fragment = new Product_Admin_Fragment();
                    }else{
                        fragment = new Category_Fragment();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Chưa làm", Toast.LENGTH_SHORT).show();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }
        });
    }
}