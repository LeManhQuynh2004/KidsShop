package fpoly.group6_pro1122.kidsshop.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Details_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.OrderItem_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_information_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.OrderItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.PaymentDao;
import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.Payment;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.R;


public class InvoiceDetails_Fragment extends Fragment {
    View view;
    TextView tv_status, tv_payment, tv_total_price, tv_date, tv_user_id, tv_order_id, tv_shipment_details;
    ShipmentDao shipmentDao;
    PaymentDao paymentDao;
    RecyclerView recyclerView;
    ArrayList<DetailsOrder> list = new ArrayList<>();
    OrderItemDao orderItemDao;
    Details_Adapter detailsAdapter;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_invoice_details, container, false);
        tv_status = view.findViewById(R.id.tv_status_item_invoice_details);
        tv_payment = view.findViewById(R.id.tv_payment_item_invoice_details);
        tv_order_id = view.findViewById(R.id.tv_orderId_item_invoice_details);
        tv_user_id = view.findViewById(R.id.tv_UserId_item_invoice_details);
        tv_date = view.findViewById(R.id.tv_Date_item_invoice_details);
        toolbar = view.findViewById(R.id.toolbar_invoice_details);
        CreateToolbar();
        tv_total_price = view.findViewById(R.id.tv_Total_price_item_invoice_details);
        tv_shipment_details = view.findViewById(R.id.tv_address_item_invoice_details);
        recyclerView = view.findViewById(R.id.RecyclerView_Invoice_Details);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shipmentDao = new ShipmentDao(getContext());
        paymentDao = new PaymentDao(getContext());
        orderItemDao = new OrderItemDao(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            DetailsOrder detailsOrder = (DetailsOrder) bundle.getSerializable("invoice_details");
            if(detailsOrder != null){
                Shipment shipment = shipmentDao.SelectID(String.valueOf(detailsOrder.getShipmentID()));
                Payment payment = paymentDao.SelectID(String.valueOf(detailsOrder.getPaymentID()));
                tv_date.setText("Ngày đặt hàng: "+detailsOrder.getDate() + " - " + detailsOrder.getTime());
                tv_order_id.setText("Đơn hàng số: " + detailsOrder.getOrder_id());
                tv_user_id.setText("Mã khách hàng: " + detailsOrder.getUserID());
                tv_total_price.setText("Tổng đơn hàng: " + detailsOrder.getTotal_price());
                tv_shipment_details.setText("Địa chỉ :"+shipment.getAddress()+" - "+shipment.getDistrict()+" - "+shipment.getCity());
                if (detailsOrder.getStatus() == 0) {
                    tv_status.setText("Trạng thái: Chờ xác nhận");
                } else if (detailsOrder.getStatus() == 1) {
                    tv_status.setText("Trạng thái: Đã xác nhận");
                } else if (detailsOrder.getStatus() == 2) {
                    tv_status.setTextColor(Color.BLUE);
                    tv_status.setText("Trạng thái: Thành công");
                } else {
                    tv_status.setTextColor(Color.BLACK);
                    tv_status.setText("Trạng thái: Đã hủy");
                }
                tv_payment.setText("Phương thức:"+payment.getType());
                list = orderItemDao.SelectOrderJoin(String.valueOf(detailsOrder.getOrder_id()));
                detailsAdapter = new Details_Adapter(getContext(),list);
                recyclerView.setAdapter(detailsAdapter);
            }
        }
        return view;
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Chi tiết đơn hàng");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Invoice_Fragment()).commit();
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.BottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.menu_account);
            menuItem.setChecked(true);
        });
    }
}