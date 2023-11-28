package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Fragment.InformationOrder_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.InvoiceDetails_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Invoice_Adapter extends RecyclerView.Adapter<Invoice_Adapter.InvoiceViewHolder> {
    Context context;
    ArrayList<DetailsOrder> list;
    ShipmentDao shipmentDao;
    UserDao userDao;
    OrderDao orderDao;

    public static final String TAG = "Invoice_Adapter";

    public Invoice_Adapter(Context context, ArrayList<DetailsOrder> list) {
        this.context = context;
        this.list = list;
        shipmentDao = new ShipmentDao(context);
        userDao = new UserDao(context);
        orderDao = new OrderDao(context);
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_admin, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        DetailsOrder detailsOrder = list.get(position);
        if (detailsOrder != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LIST_USER", context.MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            if (!email.isEmpty()) {
                Order order = orderDao.SelectID(String.valueOf(detailsOrder.getOrder_id()));
                User user = userDao.SelectID(email);
                Log.e(TAG, "onBindViewHolder: " + user.getId());
                Shipment shipment = shipmentDao.SelectID(String.valueOf(detailsOrder.getShipmentID()));
                holder.tv_date.setText("Ngày đặt hàng: " + detailsOrder.getDate() + " - " + detailsOrder.getTime());
                holder.tv_order_id.setText("Đơn hàng số: " + detailsOrder.getOrder_id());
                Log.e(TAG, "role: " + user.getRole());
                Log.e(TAG, "role: " + user.getEmail());
                if (user.getRole() == 0) {
                    holder.tv_user_id.setText("Mã khách hàng: " + detailsOrder.getUserID());
                    holder.itemView.setOnClickListener(view -> {
                        InvoiceDetails_Fragment invoiceDetailsFragment = new InvoiceDetails_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("invoice_details", detailsOrder);
                        invoiceDetailsFragment.setArguments(bundle);
                        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, invoiceDetailsFragment).commit();
                    });
                } else {
                    holder.itemView.setOnClickListener(view1 -> {
                        InformationOrder_Fragment informationOrderFragment = new InformationOrder_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("details_order", detailsOrder);
                        informationOrderFragment.setArguments(bundle);
                        ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, informationOrderFragment).commit();
                    });
                    holder.tv_user_id.setVisibility(View.GONE);
//                holder.tv_user_id.setText("Số lượng mua : " + detailsOrder.getQuantity() + " sản phẩm");
                }
                holder.tv_total_price.setText("Tổng đơn hàng: " + "$" + detailsOrder.getTotal_price());
                holder.tv_total_price.setTextColor(Color.RED);
                holder.tv_address.setText("Địa chỉ :" + shipment.getAddress() + " - " + shipment.getDistrict() + " - " + shipment.getCity());
                if (shipment.getStatus() == 0) {
                    holder.tv_payment_item_order_admin.setText("Vận chuyển: Đang chuẩn bị hàng");
                } else if (shipment.getStatus() == 1) {
                    holder.tv_payment_item_order_admin.setText("Vận chuyển: Đang vận chuyển");
                } else if (shipment.getStatus() == 2) {
                    holder.tv_payment_item_order_admin.setText("Vận chuyển: Giao hàng thành công");
                } else {
                    holder.tv_payment_item_order_admin.setText("Vận chuyển: Hoãn giao hàng");
                }
                String AfterOneDay = date(1);
                String AfterTwoDay = date(2);
                Log.e(TAG, "getView: " + AfterOneDay);
                if (detailsOrder.getDate().equalsIgnoreCase(AfterOneDay)) {
                    UpdateStatus(1, shipment, order);
                } else if (detailsOrder.getDate().equalsIgnoreCase(AfterTwoDay)) {
                    UpdateStatus(2, shipment, order);
                }

                if (detailsOrder.getStatus() == 0) {
                    holder.tv_status.setText("Trạng thái: Chờ xác nhận");
                } else if (detailsOrder.getStatus() == 1) {
                    holder.tv_status.setText("Trạng thái: Đã xác nhận");
                } else if (detailsOrder.getStatus() == 2) {
                    holder.tv_status.setTextColor(Color.BLUE);
                    holder.tv_status.setText("Trạng thái: Thành công");
                } else {
                    holder.tv_status.setTextColor(Color.BLACK);
                    holder.tv_status.setText("Trạng thái: Đã hủy");
                }
            }
        }

    }

    private void UpdateStatus(int i, Shipment shipment, Order order) {
        shipment.setStatus(i);
        order.setStatus(i);
        shipmentDao.updateData(shipment);
        orderDao.updateData(order);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InvoiceViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_id, tv_user_id, tv_total_price, tv_status, tv_date, tv_address, tv_payment_item_order_admin;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_Date_item_order_admin);
            tv_order_id = itemView.findViewById(R.id.tv_orderId_item_order_admin);
            tv_user_id = itemView.findViewById(R.id.tv_UserId_item_order_admin);
            tv_total_price = itemView.findViewById(R.id.tv_Total_price_item_order_admin);
            tv_status = itemView.findViewById(R.id.tv_status_item_order_admin);
            tv_address = itemView.findViewById(R.id.tv_address_item_order_admin);
            tv_payment_item_order_admin = itemView.findViewById(R.id.tv_payment_item_order_admin);
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
