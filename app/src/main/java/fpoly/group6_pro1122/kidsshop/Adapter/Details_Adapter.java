package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;
import fpoly.group6_pro1122.kidsshop.R;

public class Details_Adapter extends RecyclerView.Adapter<Details_Adapter.DetailsViewHolder>{

    Context context;
    ArrayList<DetailsOrder> list;
    OrderDao orderDao;

    public Details_Adapter(Context context, ArrayList<DetailsOrder> list) {
        this.context = context;
        this.list = list;
        orderDao = new OrderDao(context);
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        DetailsOrder detailsOrder = list.get(position);
        Glide.with(context)
                .load(detailsOrder.getImage())
                .placeholder(R.drawable.productimg)
                .into(holder.img_product);
        holder.tv_name.setText(detailsOrder.getProduct_name());
        holder.tv_price.setText("$" + detailsOrder.getProduct_price());
        holder.tv_total_price.setText("Tổng thanh toán :" + "$" + detailsOrder.getOrderItemPrice());
        holder.tv_quantity.setText("x" + detailsOrder.getQuantity());
        holder.tv_quantity2.setText(detailsOrder.getQuantity() + " sản phẩm");
        String AfterOneDay = date(1);
        String AfterTwoDay = date(2);
        if (detailsOrder.getDate().equalsIgnoreCase(AfterOneDay)) {
            Order order = orderDao.SelectID(String.valueOf(detailsOrder.getOrder_id()));
            order.setStatus(1);
            orderDao.updateData(order);
            notifyDataSetChanged();
        } else if (detailsOrder.getDate().equalsIgnoreCase(AfterTwoDay)) {
            Order order = orderDao.SelectID(String.valueOf(detailsOrder.getOrder_id()));
            order.setStatus(2);
            orderDao.updateData(order);
            notifyDataSetChanged();
        }
        if (detailsOrder.getStatus() == 0) {
            holder.tv_status.setText("Chờ xác nhận");
        } else if (detailsOrder.getStatus() == 1) {
            holder.tv_status.setText("Đã xác nhận");
        } else if (detailsOrder.getStatus() == 2) {
            holder.tv_status.setTextColor(Color.BLUE);
            holder.tv_status.setText("Thành công");
        } else {
            holder.tv_status.setTextColor(Color.BLACK);
            holder.tv_status.setText("Đã hủy");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_price, tv_quantity, tv_quantity2, tv_total_price, tv_status;
        ImageView img_product;
        public DetailsViewHolder(@NonNull View view) {
            super(view);
            img_product = view.findViewById(R.id.img_item_order);
            tv_name = view.findViewById(R.id.tv_name_item_order);
            tv_price = view.findViewById(R.id.tv_price_item_order);
            tv_quantity = view.findViewById(R.id.tv_quantity_item_order);
            tv_quantity2 = view.findViewById(R.id.tv_quantity2_item_order);
            tv_total_price = view.findViewById(R.id.tv_total_price_item_order);
            tv_status = view.findViewById(R.id.tv_status_item_order);
        }
    }
    private String date(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        Date newDate = calendar.getTime();
        String newDateString = sdf.format(newDate);
        return newDateString;
    }
}
