package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Dao.VoucherDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Give_Voucher_Adapter extends RecyclerView.Adapter<Give_Voucher_Adapter.VoucherViewHolder>{
    Context context;
    ArrayList<Voucher> list;
    UserDao userDao;
    VoucherDao voucherDao;

    public Give_Voucher_Adapter(Context context, ArrayList<Voucher> list) {
        this.context = context;
        this.list = list;
        voucherDao = new VoucherDao(context);
        userDao = new UserDao(context);
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_give_voucher,parent,false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = list.get(position);
        if(voucher != null){
            holder.tv_discount.setText("Giảm tới :"+voucher.getDiscount_amount()+" %");
            holder.tv_end.setText("Có hiệu lực đến : "+voucher.getExpiration_date());
        }
        holder.button.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LIST_USER",context.MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            if(email != null){
                User user = userDao.SelectID(email);
                voucher.setUser_id(user.getId());
                if(voucherDao.updateData(voucher)){
                    Toast.makeText(context, "Lấy thành công", Toast.LENGTH_SHORT).show();
                    list.remove(voucher);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Lấy thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VoucherViewHolder extends RecyclerView.ViewHolder{
        TextView tv_discount,tv_end;
        Button button;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_discount = itemView.findViewById(R.id.tv_discount_item_give_voucher);
            tv_end = itemView.findViewById(R.id.tv_end_item_give_voucher);
            button = itemView.findViewById(R.id.bt_give_item_voucher);
        }
    }
}
