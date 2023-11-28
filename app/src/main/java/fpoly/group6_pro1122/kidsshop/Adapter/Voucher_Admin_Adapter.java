package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Voucher_Admin_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Voucher> list;

    public Voucher_Admin_Adapter(Context context, ArrayList<Voucher> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class Voucher_Holder {
        TextView tv_code, tv_discount, tv_end;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Voucher_Holder voucherHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_voucher, viewGroup, false);
            voucherHolder = new Voucher_Holder();
            voucherHolder.tv_code = view.findViewById(R.id.tv_code_item_voucher);
            voucherHolder.tv_discount = view.findViewById(R.id.tv_discount_item_voucher);
            voucherHolder.tv_end = view.findViewById(R.id.tv_end_item_voucher);
            view.setTag(voucherHolder);
        }else{
            voucherHolder = (Voucher_Holder) view.getTag();
        }
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        Voucher voucher = list.get(i);
        if(voucher != null){
            voucherHolder.tv_code.setText("Giảm tối đa: "+voucher.getDiscount_amount()+"%");
            voucherHolder.tv_discount.setText("Có hiệu lực từ : "+voucher.getStart_date());
            voucherHolder.tv_end.setText("Có hiệu lực đến : "+voucher.getExpiration_date());
            if (currentDate.equalsIgnoreCase(voucher.getExpiration_date())){
                voucherHolder.tv_code.setText("Giảm tối đa: "+voucher.getDiscount_amount()+"%"+"(Hết hạn)");
            }
        }
        return view;
    }
}
