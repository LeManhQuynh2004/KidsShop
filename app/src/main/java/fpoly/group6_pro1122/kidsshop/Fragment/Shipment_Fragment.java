package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_Select_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Shipment_Fragment extends Fragment {
    View view;
    Toolbar toolbar;
    ListView listViewShipment;
    ShipmentDao shipmentDao;
    ArrayList<Shipment> list = new ArrayList<>();
    Shipment_Select_Adapter shipmentSelectAdapter;

    public static final String TAG = "Shipment_Fragment";
    SharedPreferences sharedPreferences;
    String email;
    UserDao userDao;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shipment, container, false);
        toolbar = view.findViewById(R.id.toolbar_shipment);
        userDao = new UserDao(getContext());
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
        shipmentDao = new ShipmentDao(getContext());
        listViewShipment = view.findViewById(R.id.listViewShipment);
        if(email != null){
            User user = userDao.SelectID(email);
            list = shipmentDao.SelectAllUser(String.valueOf(user.getId()));
            Log.e(TAG, "onCreateView: "+user.getId());
        }
        Log.e(TAG, "onCreateView: " + list.size());
        shipmentSelectAdapter = new Shipment_Select_Adapter(getContext(), list);
        listViewShipment.setAdapter(shipmentSelectAdapter);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Chọn địa chỉ nhận hàng");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Payment_Fragment()).commit();
        });
        view.findViewById(R.id.bt_add_shipment).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Add_Shipment_Fragment()).commit();
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Payment_Fragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}