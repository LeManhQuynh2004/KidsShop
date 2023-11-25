package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Model.Payment;
import fpoly.group6_pro1122.kidsshop.R;

public class Payment_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Payment> list;

    public Payment_Adapter(Context context, ArrayList<Payment> list) {
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
    class PaymentViewHolder {
        TextView tv_type_item_payment;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PaymentViewHolder paymentViewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_payment,viewGroup,false);
            paymentViewHolder = new PaymentViewHolder();
            paymentViewHolder.tv_type_item_payment = view.findViewById(R.id.tv_type_item_payment);
            view.setTag(paymentViewHolder);
        }else{
            paymentViewHolder = (PaymentViewHolder) view.getTag();
        }
        Payment payment = list.get(i);
        if(payment != null){
            paymentViewHolder.tv_type_item_payment.setText(payment.getType());
        }
        return view;
    }
}
