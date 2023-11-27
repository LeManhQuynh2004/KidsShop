package fpoly.group6_pro1122.kidsshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.EditInfor_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.AccountItem;
import fpoly.group6_pro1122.kidsshop.R;

public class Admin_Account_Adapter extends BaseAccount_Adapter{
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
        holder.itemView.setOnClickListener(v->{
            if (accountItem.getTenChucNang().equalsIgnoreCase("Quản Lý Sản Phẩm")){
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Category_Admin_Fragment()).commit();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Quản Lý Thể Loại")) {
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Product_Admin_Fragment()).commit();
            } else if (accountItem.getTenChucNang().equalsIgnoreCase("Thiết Lập Tài Khoản")) {
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EditInfor_Fragment()).commit();
            }
        });
    }
}
