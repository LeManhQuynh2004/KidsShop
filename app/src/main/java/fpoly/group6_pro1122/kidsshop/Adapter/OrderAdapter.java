package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Dao.OrderItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Fragment.Details_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.InformationOrder_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class OrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<DetailsOrder> list;
    ProductDao productDao;
    OrderItemDao orderItemDao;
    OrderDao orderDao;
    public static final String TAG = "OrderAdapter";

    public OrderAdapter(Context context, ArrayList<DetailsOrder> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
        orderItemDao = new OrderItemDao(context);
        orderDao = new OrderDao(context);
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

    class OrderViewHolder {
        TextView tv_name, tv_price, tv_quantity, tv_quantity2, tv_total_price, tv_status;
        ImageView img_product;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderViewHolder orderViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order, viewGroup, false);
            orderViewHolder = new OrderViewHolder();
            orderViewHolder.img_product = view.findViewById(R.id.img_item_order);
            orderViewHolder.tv_name = view.findViewById(R.id.tv_name_item_order);
            orderViewHolder.tv_price = view.findViewById(R.id.tv_price_item_order);
            orderViewHolder.tv_quantity = view.findViewById(R.id.tv_quantity_item_order);
            orderViewHolder.tv_quantity2 = view.findViewById(R.id.tv_quantity2_item_order);
            orderViewHolder.tv_total_price = view.findViewById(R.id.tv_total_price_item_order);
            orderViewHolder.tv_status = view.findViewById(R.id.tv_status_item_order);
            view.setTag(orderViewHolder);
        } else {
            orderViewHolder = (OrderViewHolder) view.getTag();
        }
        DetailsOrder detailsOrder = list.get(i);
        Glide.with(context)
                .load(detailsOrder.getImage())
                .placeholder(R.drawable.productimg)
                .into(orderViewHolder.img_product);
        orderViewHolder.tv_name.setText(detailsOrder.getProduct_name());
        orderViewHolder.tv_price.setText("$" + detailsOrder.getProduct_price());
        orderViewHolder.tv_total_price.setText("Tổng thanh toán :" + "$" + detailsOrder.getOrderItemPrice());
        orderViewHolder.tv_quantity.setText("x" + detailsOrder.getQuantity());
        orderViewHolder.tv_quantity2.setText(detailsOrder.getQuantity() + " sản phẩm");
        String AfterOneDay = date(1);
        String AfterTwoDay = date(2);
        Log.e(TAG, "getView: "+AfterOneDay);
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
            orderViewHolder.tv_status.setText("Chờ xác nhận");
        } else if (detailsOrder.getStatus() == 1) {
            orderViewHolder.tv_status.setText("Đã xác nhận");
        } else if (detailsOrder.getStatus() == 2) {
            orderViewHolder.tv_status.setTextColor(Color.BLUE);
            orderViewHolder.tv_status.setText("Thành công");
        } else {
            orderViewHolder.tv_status.setTextColor(Color.BLACK);
            orderViewHolder.tv_status.setText("Đã hủy");
        }
        view.setOnClickListener(view1 -> {
            InformationOrder_Fragment informationOrderFragment = new InformationOrder_Fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("details_order", detailsOrder);
            informationOrderFragment.setArguments(bundle);
            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, informationOrderFragment).commit();
        });
        return view;
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
