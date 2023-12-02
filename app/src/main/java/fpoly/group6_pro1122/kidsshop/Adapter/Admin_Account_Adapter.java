package fpoly.group6_pro1122.kidsshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Category_User_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.EditInfor_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Invoice_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Revenue_Statistics_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.StatisticalFragment;
import fpoly.group6_pro1122.kidsshop.Fragment.User_Manager_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Voucher_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.AccountItem;
import fpoly.group6_pro1122.kidsshop.R;

public class Admin_Account_Adapter extends BaseAccount_Adapter {
    Fragment fragment = null;

    public Admin_Account_Adapter(Context context, ArrayList<AccountItem> list) {
        super(context, list);
    }

    public void addNewItem(AccountItem newItem) {
        list.add(newItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        AccountItem accountItem = list.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (accountItem.getTenChucNang().equalsIgnoreCase("Quản Lý Sản Phẩm")) {
                fragment = new Product_Admin_Fragment();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Quản Lý Thể Loại")) {
                fragment = new Category_Admin_Fragment();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Thiết Lập Tài Khoản")) {
                fragment = new Product_Admin_Fragment();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Quản lý Mã Giảm Giá")) {
                fragment = new Voucher_Admin_Fragment();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Thống kê doanh thu")) {
                fragment = new Revenue_Statistics_Fragment();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Thống kê sản phẩm bán chạy")) {
                fragment = new StatisticalFragment();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("quản lý người dùng")){
                fragment = new User_Manager_Fragment();
            }else{
                fragment = new Invoice_Fragment();
            }
            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        });
    }
}
