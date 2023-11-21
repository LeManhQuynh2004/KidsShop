package fpoly.group6_pro1122.kidsshop.Fragment;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Admin_Account_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.User_Account_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.AccountItem;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;
import fpoly.group6_pro1122.kidsshop.SignUpActivity;

public class PersonalInf_Fragment extends Fragment {
    Button btn_logout;
    RecyclerView recyclerView;
    Admin_Account_Adapter adminAccountAdapter;
    User_Account_Adapter userAccountAdapter;
    ArrayList<AccountItem> list = new ArrayList<>();
    UserDao dao;
    TextView tv_hoTen,tv_email;
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

        tv_hoTen = view.findViewById(R.id.tv_hoTen);
        tv_email = view.findViewById(R.id.tv_email);
        btn_logout = view.findViewById(R.id.btn_logOut);
        recyclerView = view.findViewById(R.id.recyclerView_account_admin);
        adminAccountAdapter = new Admin_Account_Adapter(getContext(),list);
        userAccountAdapter = new User_Account_Adapter(getContext(),list);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        User user = dao.SelectID(email);
        if (user.getFullname() == null){
            tv_hoTen.setText("No Name");
        }else {
            tv_hoTen.setText(user.getFullname());
        }
        tv_email.setText(user.getEmail());
        if(user != null){
            if(user.getRole() == 0){//Admin
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Quản Lý Người Dùng"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Quản Lý Sản Phẩm"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Quản Lý Thể Loại"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Quản Lý Mã Giảm Giá"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Quản Lý Hóa Đơn"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Thống Kê Doanh Thu"));
                adminAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Thống Kê Sản Phẩm Bán Chạy"));

                recyclerView.setAdapter(adminAccountAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }else{//Customer
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Thiết Lập Tài Khoản"));
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Lịch Sử Mua Hàng"));
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Đã Thích"));
                userAccountAdapter.addNewItem(new AccountItem(R.drawable.productimg, "Quên Mật Khẩu"));
                recyclerView.setAdapter(userAccountAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }


        //Thêm gạch
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        itemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(itemDecoration);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });
    }
}