package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CartItem_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Details_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.OrderItem_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_information_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Dao.OrderItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.PaymentDao;
import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;
import fpoly.group6_pro1122.kidsshop.Model.Payment;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.R;

public class InformationOrder_Fragment extends Fragment {
    View view;
    TextView tv_status, tv_payment, tv_total_price, tv_orderItem_id, tv_date, tv_total_price2, tv_content, tv_shipment_details;
    ListView lv_shipment;
    ArrayList<Shipment> list_shipment = new ArrayList<>();
    ArrayList<DetailsOrder> list = new ArrayList<>();
    Shipment_information_Adapter shipmentPaymentAdapter;
    PaymentDao paymentDao;
    ShipmentDao shipmentDao;
    OrderItemDao orderItemDao;
    Button bt_Cancle;
    Details_Adapter detailsAdapter;
    Toolbar toolbar;
    OrderDao orderDao;
    Shipment shipment;
    RecyclerView recyclerView;
    Order order;

    private void MinMap() {
        tv_status = view.findViewById(R.id.tv_status_details);
        tv_content = view.findViewById(R.id.tv_content_details);
        tv_date = view.findViewById(R.id.tv_date_details);
        tv_payment = view.findViewById(R.id.tv_type_payment_details);
        tv_total_price = view.findViewById(R.id.tv_total_price_details);
        tv_orderItem_id = view.findViewById(R.id.tv_orderItem_id_details);
        lv_shipment = view.findViewById(R.id.listViewShipment_details);
        recyclerView = view.findViewById(R.id.RecyclerView_OrderItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tv_shipment_details = view.findViewById(R.id.tv_shipment_details);
        paymentDao = new PaymentDao(getContext());
        shipmentDao = new ShipmentDao(getContext());
        orderItemDao = new OrderItemDao(getContext());
        orderDao = new OrderDao(getContext());
        toolbar = view.findViewById(R.id.toolbar_details);
        bt_Cancle = view.findViewById(R.id.bt_cancel_order);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_information_order, container, false);
        MinMap();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Chi tiết đơn hàng");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            DetailsOrder detailsOrder = (DetailsOrder) bundle.getSerializable("details_order");
            order = orderDao.SelectID(String.valueOf(detailsOrder.getOrder_id()));
            if (detailsOrder.getStatus() == 0) {
                tv_status.setText("Chờ xác nhận");
                tv_content.setText("Đang chờ hệ thống xác nhận đơn hàng . Trong thời gian này , bạn có thể liên hệ với Người bán để xác nhận thêm thông tind đơn hàng nhé.");
            } else if (detailsOrder.getStatus() == 1) {
                tv_status.setText("Đã xác nhận");
                tv_content.setText("Hệ thống đã xác nhận đơn hàng thành công. Trong thời gian này , bạn có thể liên hệ với Người bán để xác nhận thêm thông tind đơn hàng nhé.");
            } else if (detailsOrder.getStatus() == 2) {
                tv_status.setText("Thành công");
                tv_content.setText("Hệ thống xác nhận đơn hàng thành công. Trong thời gian này , bạn có thể liên hệ với Người bán để xác nhận thêm thông tind đơn hàng nhé.");
            } else {
                bt_Cancle.setText("Đơn hàng đã hủy");
                bt_Cancle.setEnabled(false);
                tv_status.setText("Đã hủy");
                tv_content.setText("Hệ thống xác nhận đơn hàng đã hủy thành công. Trong thời gian này , bạn có thể liên hệ với Người bán để xác nhận thêm thông tind đơn hàng nhé.");
            }
            tv_date.setText(detailsOrder.getDate() + " " + detailsOrder.getTime());
            tv_orderItem_id.setText("je" + detailsOrder.getOrder_id() + "NjL");
            tv_total_price.setText("$" + detailsOrder.getTotal_price());
            list_shipment = shipmentDao.Select_Shipment(detailsOrder.getShipmentID());
            shipmentPaymentAdapter = new Shipment_information_Adapter(getContext(), list_shipment);
            lv_shipment.setAdapter(shipmentPaymentAdapter);
            list = orderItemDao.SelectOrderJoin(String.valueOf(detailsOrder.getOrder_id()));
            detailsAdapter = new Details_Adapter(getContext(), list);
            recyclerView.setAdapter(detailsAdapter);
            Payment payment = paymentDao.SelectID(String.valueOf(detailsOrder.getPaymentID()));
            tv_payment.setText(payment.getType());
            shipment = shipmentDao.SelectID(String.valueOf(detailsOrder.getShipmentID()));

            if (order.getStatus() == 0) {
                tv_shipment_details.setText("Đang chuẩn bị hàng");
            } else if (order.getStatus() == 1) {
                tv_shipment_details.setText("Đang vận chuyển");
            } else if (order.getStatus() == 2) {
                tv_shipment_details.setText("Giao hàng thành công");
            } else {
                tv_shipment_details.setText("Hoãn giao hàng");
            }
            Log.e("TAG", "onCreateView: "+shipment.getStatus());
            if(order.getStatus() == 2){
                bt_Cancle.setText("Thành công");
                bt_Cancle.setEnabled(false);
            }
            bt_Cancle.setOnClickListener(view1 -> {

                order.setStatus(4);
                shipment.setStatus(4);
                if (orderDao.updateData(order)) {
                    shipmentDao.updateData(shipment);
                    Toast.makeText(getContext(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Order_Fragment()).commit();
                } else {
                    Toast.makeText(getContext(), "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}