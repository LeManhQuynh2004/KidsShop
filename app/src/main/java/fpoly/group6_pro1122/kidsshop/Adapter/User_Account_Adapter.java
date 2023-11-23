package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.AccountItem;
import fpoly.group6_pro1122.kidsshop.R;

public class User_Account_Adapter extends BaseAccount_Adapter{
    public User_Account_Adapter(Context context, ArrayList<AccountItem> list) {
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
            Toast.makeText(context, "Chức năng: "+accountItem.getTenChucNang(), Toast.LENGTH_SHORT).show();
        });
    }

}
