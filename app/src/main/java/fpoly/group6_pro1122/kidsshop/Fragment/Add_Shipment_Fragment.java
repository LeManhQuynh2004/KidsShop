package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;


public class Add_Shipment_Fragment extends Fragment {

    View view;
    EditText ed_city, ed_district, ed_address, ed_name, ed_Phone_number;
    RadioGroup group_address_type;
    int result;
    UserDao userDao;
    ShipmentDao shipmentDao;
    ArrayList<Shipment> list = new ArrayList();
    RadioButton rd_type_1, rd_type_2;
    Toolbar toolbar;

    private boolean isEmail(String str) {
        return str.matches(
                "[a-zA-Z0-9_.]+@[a-zA-Z]+\\.+[a-z]+"
        );
    }

    private boolean isPhone(String str) {
        return str.matches("(09|08|03)\\d{8}");
    }

    private boolean isFullName(String str) {
        return str.matches("[a-z A-Z]+");
    }

    private boolean isAddress(String str) {
        return str.matches("[a-z A-Z 0-9]+");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_shipment, container, false);
        toolbar = view.findViewById(R.id.toolbar_add_shipment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Địa chỉ mới");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Shipment_Fragment()).commit();
        });
        ed_name = view.findViewById(R.id.ed_name_add_shipment);
        ed_Phone_number = view.findViewById(R.id.ed_phone_add_shipment);
        ed_city = view.findViewById(R.id.ed_city_add_shipment);
        shipmentDao = new ShipmentDao(getContext());
        list = shipmentDao.SelectAll();
        ed_district = view.findViewById(R.id.ed_district_add_shipment);
        ed_address = view.findViewById(R.id.ed_address_add_shipment);
        group_address_type = view.findViewById(R.id.rg_address_type);
        rd_type_1 = view.findViewById(R.id.rd_address_type_1);
        rd_type_2 = view.findViewById(R.id.rd_address_type_2);
        group_address_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rd_address_type_1) {
                    result = 0;
                } else if (i == R.id.rd_address_type_2) {
                    result = 1;
                } else {
                    Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.bt_finish_add_shipment).setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            if (email.equals("")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
            } else {
                userDao = new UserDao(getContext());
                User user = userDao.SelectID(email);
                if (user != null) {
                    String name = ed_name.getText().toString().trim();
                    String phone = ed_Phone_number.getText().toString().trim();
                    String address = ed_address.getText().toString().trim();
                    String city = ed_city.getText().toString().trim();
                    String district = ed_district.getText().toString().trim();
                    Calendar calendar = Calendar.getInstance();
                    Date currentDate = calendar.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String date = sdf.format(currentDate);
                    if (ValidateForm(city, district, name, phone, address)) {
                        Shipment shipment = new Shipment();
                        shipment.setCity(city);
                        shipment.setAddress(address);
                        shipment.setDate(date);
                        shipment.setStatus(0);
                        shipment.setAddress_type(result);
                        shipment.setDistrict(district);
                        shipment.setName(name);
                        shipment.setPhone(phone);
                        shipment.setUser_id(user.getId());
                        if (shipmentDao.insertData(shipment)) {
                            Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Shipment_Fragment()).commit();
                            list.add(shipment);
                        } else {
                            Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Shipment_Fragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    EditText ed_city, ed_district, ed_address, ed_name, ed_Phone_number;
    private boolean ValidateForm(String city, String district, String name,
                                 String phone, String address) {
        boolean isCheck = true;
        if (city.isEmpty() || district.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        } else {
            if (!isFullName(name) || !isFullName(city) || !isFullName(district)) {
                Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (!isAddress(address)) {
                Toast.makeText(getContext(), "Nhập sai định dạng địa chỉ", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (!isPhone(phone)) {
                Toast.makeText(getContext(), "Nhập sai định dạng số điện thoại", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
        }
        return isCheck;
    }
}