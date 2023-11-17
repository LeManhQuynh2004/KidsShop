package fpoly.group6_pro1122.kidsshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Home_Fragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Category_Admin_Fragment()).commit();
    }
}