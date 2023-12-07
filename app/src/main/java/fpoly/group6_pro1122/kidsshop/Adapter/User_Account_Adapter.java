package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Fragment.CartFragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.ChangePass_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.EditInfor_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Login_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Order_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Product_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.WishList_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.AccountItem;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class User_Account_Adapter extends BaseAccount_Adapter{
    public User_Account_Adapter(Context context, ArrayList<AccountItem> list) {
        super(context, list);
    }
    public void addNewItem(AccountItem newItem) {
        list.add(newItem);
        notifyDataSetChanged();
    }
    UserDao dao;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        dao = new UserDao(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("LIST_USER",Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL","");
        User user =dao.SelectID(email);

        AccountItem accountItem = list.get(position);
        holder.itemView.setOnClickListener(v->{
            if (user != null){
                Toast.makeText(context, "Chức năng: "+accountItem.getTenChucNang(), Toast.LENGTH_SHORT).show();
                if (accountItem.getTenChucNang().equalsIgnoreCase("Thiết Lập Tài Khoản")){
                    ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EditInfor_Fragment()).commit();
                } else if (accountItem.getTenChucNang().equalsIgnoreCase("Đã Thích")) {
                    ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WishList_Fragment()).commit();
                } else if (accountItem.getTenChucNang().equalsIgnoreCase("Thay Đổi Mật khẩu")) {
                    ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChangePass_Fragment()).commit();
                }else if (accountItem.getTenChucNang().equalsIgnoreCase("Lịch sử mua hàng")){
                    ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Order_Fragment()).commit();
                }else if (accountItem.getTenChucNang().equalsIgnoreCase("Rỏ hàng")) {
                    ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragment()).commit();
                }
            }else {
                Toast.makeText(context, "Bạn cần đăng nhập để dùng chức năng của hệ thống", Toast.LENGTH_SHORT).show();
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Login_Fragment()).commit();
            }
        });
    }

}
