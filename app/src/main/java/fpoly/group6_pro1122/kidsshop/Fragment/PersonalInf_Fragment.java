package fpoly.group6_pro1122.kidsshop.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Admin_Account_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.User_Account_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.AccountItem;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class PersonalInf_Fragment extends Fragment {
    Button btn_logout;
    RecyclerView recyclerView;
    Admin_Account_Adapter adminAccountAdapter;
    User_Account_Adapter userAccountAdapter;
    ArrayList<AccountItem> list = new ArrayList<>();
    UserDao dao;
    TextView tv_hoTen, tv_email;
    Button btnSignIn, btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_inf_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dao = new UserDao(getContext());
        //anh xa
        tv_hoTen = view.findViewById(R.id.tv_hoTen);
        tv_email = view.findViewById(R.id.tv_email);
        btn_logout = view.findViewById(R.id.btn_logOut);
        btnSignIn = view.findViewById(R.id.btn_sign_in);
        btnSignUp = view.findViewById(R.id.btn_sign_up);
        recyclerView = view.findViewById(R.id.recyclerView_account_admin);


        adminAccountAdapter = new Admin_Account_Adapter(getContext(), list);
        userAccountAdapter = new User_Account_Adapter(getContext(), list);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        User user = dao.SelectID(email);

        if (user != null) {
            if (user.getRole() == 0) {//Admin
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_ql_user, "Quản Lý Người Dùng"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_sanpham, "Quản Lý Sản Phẩm"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_category, "Quản Lý Thể Loại"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_voucher, "Quản Lý Mã Giảm Giá"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_invoice, "Quản Lý Hóa Đơn"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_doanhthu, "Thống Kê Doanh Thu"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_san_pham_ban_chay, "Thống Kê Sản Phẩm Bán Chạy"));

                recyclerView.setAdapter(adminAccountAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {//Customer
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_thiet_lap_account, "Thiết Lập Tài Khoản"));
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_lic_su_mua_hang, "Lịch Sử Mua Hàng"));
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.heartred, "Đã Thích"));
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_change_pass, "Thay Đổi Mật Khẩu"));
                recyclerView.setAdapter(userAccountAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            btnSignIn.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.GONE);

            // Hiển thị thông tin tên và email
            if (user.getFullname() == null) {
                tv_hoTen.setText("No Name");
            } else {
                tv_hoTen.setText(user.getFullname());
            }
            tv_email.setText(user.getEmail());

        } else {
            userAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_thiet_lap_account, "Thiết Lập Tài Khoản"));
            userAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_lic_su_mua_hang, "Lịch Sử Mua Hàng"));
            userAccountAdapter.addNewItem(new AccountItem(R.drawable.heartred, "Đã Thích"));
            userAccountAdapter.addNewItem(new AccountItem(R.drawable.icon_quenpass, "Quên Mật Khẩu"));
            recyclerView.setAdapter(userAccountAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tv_hoTen.setVisibility(View.GONE);
            tv_email.setVisibility(View.GONE);
            btn_logout.setVisibility(View.GONE);
            btnSignIn.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
            });
            btnSignUp.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SigUp_Fragment()).commit();
            });
        }
        //Thêm gạch
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        itemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(itemDecoration);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không ?");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
                        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.BottomNavigationView);
                        bottomNavigationView.setSelectedItemId(R.id.menu_home);
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
            }
        });
    }
}