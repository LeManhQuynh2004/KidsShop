package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Adapter.CartItem_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_Select_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.EvaluationDao;
import fpoly.group6_pro1122.kidsshop.Dao.OrderDao;
import fpoly.group6_pro1122.kidsshop.Dao.OrderItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.PaymentDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Dao.VoucherDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Evaluation;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;
import fpoly.group6_pro1122.kidsshop.Model.Payment;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Payment_Fragment extends Fragment {
    View view;
    Toolbar toolbar;
    int shipment_id = 0, voucher_id = 0;
    float voucher_discount = 0;
    Shipment shipment;
    Shipment_Payment_Adapter shipmentSelectAdapter;
    ArrayList<Shipment> list = new ArrayList<>();
    ShipmentDao shipmentDao;
    ListView listView, listViewProduct;
    TextView tv_status_address, tv_quantity_cartItem_payment, tv_quantity_cartItem_payment2, tv_total_price, tv_voucher;
    CartItemDao cartItemDao;
    RecyclerView RecyclerView_Evaluation;
    ArrayList<CartItem> list_cartItem = new ArrayList<>();
    ArrayList<Shipment> list_shipment = new ArrayList<>();
    ArrayList<Evaluation> list_evaluation = new ArrayList<>();
    CartItem_Payment_Adapter cartItemPaymentAdapter;
    TextView tv_payment, tv_total_price_cart_payment, tv_discount_order, tv_total_price_order_payment, tv_total_price_order_payment_bottom;
    PaymentDao paymentDao;
    UserDao userDao;
    OrderDao orderDao;
    String email;
    ProductDao productDao;
    SharedPreferences sharedPreferences;
    int total_price = 0, discount = 0;
    OrderItemDao orderItemDao;
    int hour, minute;
    EvaluationDao evaluationDao;

    Shipment shipment1;

    Voucher voucher;
    VoucherDao voucherDao;
    int count = 0;

    public static final String TAG = "Payment_Fragment";

    private void MinMap() {
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
        toolbar = view.findViewById(R.id.Toolbar_Payment);
        listView = view.findViewById(R.id.listViewShipment);
        tv_payment = view.findViewById(R.id.tv_payment);
        tv_discount_order = view.findViewById(R.id.tv_discount_order);
        tv_total_price_cart_payment = view.findViewById(R.id.tv_total_price_cart_payment);
        tv_total_price_order_payment = view.findViewById(R.id.tv_total_price_order_payment);
        tv_total_price_order_payment_bottom = view.findViewById(R.id.tv_total_price_order_payment_bottom);
        tv_quantity_cartItem_payment2 = view.findViewById(R.id.tv_quantity_cartItem2);
        tv_total_price = view.findViewById(R.id.tv_total_price_cartItem_payment);
        tv_voucher = view.findViewById(R.id.tv_voucher);
        listViewProduct = view.findViewById(R.id.listViewProduct);
        cartItemDao = new CartItemDao(getContext());
        tv_status_address = view.findViewById(R.id.tv_status_address);
        paymentDao = new PaymentDao(getContext());
        userDao = new UserDao(getContext());
        shipmentDao = new ShipmentDao(getContext());
        orderDao = new OrderDao(getContext());
        productDao = new ProductDao(getContext());
        orderItemDao = new OrderItemDao(getContext());
        voucherDao = new VoucherDao(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        MinMap();
        if (email != null) {
            User user = userDao.SelectID(email);
            list_shipment = shipmentDao.SelectAllUser(String.valueOf(user.getId()));
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            voucher = (Voucher) bundle.getSerializable("voucher");
            if (voucher != null) {
                voucher_id = voucher.getId();
                voucher_discount = (float) (voucher.getDiscount_amount() * 0.01);
                Log.e(TAG, "onCreateView: " + voucher_discount);
                tv_voucher.setText("Giảm giá :" + voucher.getDiscount_amount() + " %");
            }
        }
        CreateToolbar();
        CreateListViewCartItem();
        CreateListViewShipment();
        UpdatePayment();
        ChooseAddress();

        if (list.size() == 0) {
            tv_status_address.setText("Chưa có địa chỉ");
        } else {
            tv_status_address.setText("Mặc định");
        }

        tv_voucher.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Voucher_Customer_Fragment()).commit();
        });
        tv_payment.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Type_Payment_Fragment()).commit();
        });
        tv_status_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Shipment_Fragment()).commit();
            }
        });

        view.findViewById(R.id.bt_order).setOnClickListener(view1 -> {
            User user = userDao.SelectID(email);
            if (list_shipment.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
            } else {
                shipment1 = shipmentDao.SelectID(String.valueOf(shipment_id));
                Toast.makeText(getContext(), "Shipment_id" + shipment_id, Toast.LENGTH_SHORT).show();
                if (user != null) {
                    Order order = new Order();
                    order.setUser_id(user.getId());
                    order.setPayment_id(1);
                    order.setStatus(0);
                    order.setTotal_price(total_price - discount);
                    order.setShipment_id(shipment1.getId());
                    Calendar calendar = Calendar.getInstance();
                    hour = calendar.get(Calendar.HOUR_OF_DAY);
                    minute = calendar.get(Calendar.MINUTE);
                    order.setTime(hour + ":" + minute);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String date = sdf.format(new Date());
                    Log.e(TAG, "onCreateView: " + date);
                    shipment1.setStatus(0);
                    shipmentDao.updateData(shipment1);
                    order.setDate(date);
                    Toast.makeText(getContext(), String.valueOf(total_price - discount), Toast.LENGTH_SHORT).show();

                    if (orderDao.insertData(order)) {
                        Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                        if (voucher_id != 0) {
                            Voucher voucherFinish = voucherDao.SelectID(String.valueOf(voucher_id));
                            voucherDao.deleteData(voucherFinish);
                        }
                        for (int i = 0; i < list_cartItem.size(); i++) {
                            CartItem cartItem = list_cartItem.get(i);
                            Log.e(TAG, "List_Size: " + list_cartItem.size());
                            Product product = productDao.SelectID(String.valueOf(cartItem.getProduct_id()));
                            Log.e(TAG, "List_Size: " + product.getProduct_id());
                            if (product != null) {
                                OrderItem orderItem = new OrderItem();
                                orderItem.setProduct_id(product.getProduct_id());
                                orderItem.setQuantity(cartItem.getQuantity());
                                discount = discount / list_cartItem.size();
                                if (voucher_discount != 0) {
                                    discount = (int) (product.getProduct_price() * voucher_discount);
                                }
                                orderItem.setPrice(product.getProduct_price() * cartItem.getQuantity() - discount);
                                orderItem.setOrder_id(order.getId());
                                orderItemDao.insertData(orderItem);
                                product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                                productDao.updateData(product);
                                cartItemDao.deleteData(cartItem);
                            }
                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Finish_Order_Fragment()).commit();
                    } else {
                        Toast.makeText(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        return view;
    }

    private void ChooseAddress() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            shipment = (Shipment) bundle.getSerializable("shipment");
            if (shipment != null) {
                if (email != null) {
                    User user = userDao.SelectID(email);
                    shipment_id = shipment.getId();
                    list = shipmentDao.Select_Shipment_User(shipment_id, user.getId());
                    shipmentSelectAdapter = new Shipment_Payment_Adapter(getContext(), list);
                    listView.setAdapter(shipmentSelectAdapter);
                }
            }
        }
    }
    private void CreateListViewShipment() {
        if (email != null) {
            User user = userDao.SelectID(email);
            list = shipmentDao.SelectUser(String.valueOf(user.getId()));
            Log.e(TAG, "CreateListViewShipment: " + user.getId());
            Log.e(TAG, "CreateListViewShipment: " + list.size());
            ArrayList<Shipment> singleItemList = new ArrayList<>();
            if (!list.isEmpty()) {
                singleItemList.add(list.get(0));
                shipment_id = list.get(0).getId();
            }
            shipmentSelectAdapter = new Shipment_Payment_Adapter(getContext(), singleItemList);
            listView.setAdapter(shipmentSelectAdapter);
        }
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thanh toán");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
        });
    }

    private void UpdatePayment() {
        if (email != null) {
            User user = userDao.SelectID(email);
            Payment payment = paymentDao.SelectID(String.valueOf(1));
            payment.setUser_id(user.getId());
            paymentDao.updateData(payment);
            tv_payment.setText(payment.getType());
        }
    }

    private void CreateListViewCartItem() {
        list_cartItem = cartItemDao.SelectPay(1);
        Log.e(TAG, "CreateListViewCartItem: "+list_cartItem.size());
        for (int i = 0; i < list_cartItem.size(); i++) {
            total_price += list_cartItem.get(i).getTotal_price();
            Product product = productDao.SelectID(String.valueOf(list_cartItem.get(i).getProduct_id()));
            if (voucher_discount != 0) {
                discount += (int) (product.getProduct_price() * voucher_discount);
            }
        }
        tv_total_price.setText("$" + total_price);
        tv_quantity_cartItem_payment2.setText("Tổng số tiền (" + list_cartItem.size() + " sản phẩm):");
        cartItemPaymentAdapter = new CartItem_Payment_Adapter(getContext(), list_cartItem);
        tv_discount_order.setText("-$" + discount);
        tv_total_price_cart_payment.setText("$" + total_price);
        tv_total_price_order_payment.setText("$" + (total_price - discount));
        tv_total_price_order_payment_bottom.setText("$" + (total_price - discount));
        listViewProduct.setAdapter(cartItemPaymentAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}