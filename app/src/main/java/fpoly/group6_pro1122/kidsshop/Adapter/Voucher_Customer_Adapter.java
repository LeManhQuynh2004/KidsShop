package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Fragment.Details_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Payment_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Voucher_Customer_Adapter extends RecyclerView.Adapter<Voucher_Customer_Adapter.VoucherViewHolder>{
    Context context;
    ArrayList<Voucher> list;

    public Voucher_Customer_Adapter(Context context, ArrayList<Voucher> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_voucher_customer,parent,false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = list.get(position);
        if(voucher != null){
            holder.tv_discount.setText("Giảm tới :"+voucher.getDiscount_amount()+" %");
            holder.tv_end.setText("Có hiệu lực đến : "+voucher.getExpiration_date());
        }
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        if(currentDate.equalsIgnoreCase(voucher.getExpiration_date())){
            holder.radioButton.setEnabled(false);
            holder.tv_end.setText("Mã giảm giá đã hết hạn");
            holder.tv_end.setTextColor(Color.RED);
        }
        holder.radioButton.setOnClickListener(view -> {
            if (holder.radioButton.isChecked()) {
                Toast.makeText(context, "Chọn", Toast.LENGTH_SHORT).show();
                Payment_Fragment paymentFragment = new Payment_Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("voucher", voucher);
                paymentFragment.setArguments(bundle);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, paymentFragment).commit();
            } else {
                Toast.makeText(context, "Hủy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VoucherViewHolder extends RecyclerView.ViewHolder{
        TextView tv_code,tv_discount,tv_end;
        RadioButton radioButton;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_discount = itemView.findViewById(R.id.tv_discount_item_customer_voucher);
            tv_end = itemView.findViewById(R.id.tv_end_item_customer_voucher);
            radioButton = itemView.findViewById(R.id.rb_select);
        }
    }
}
