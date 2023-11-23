package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Shipment_Select_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.R;

public class Payment_Fragment extends Fragment {
    View view;
    Toolbar toolbar;
    int shipment_id;
    Shipment shipment;
    Shipment_Payment_Adapter shipmentSelectAdapter;
    ArrayList<Shipment> list = new ArrayList<>();
    ShipmentDao shipmentDao;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        toolbar = view.findViewById(R.id.Toolbar_Payment);
        listView = view.findViewById(R.id.listViewShipment);
        shipmentDao = new ShipmentDao(getContext());
        list = shipmentDao.Select_Shipment(1);
        shipmentSelectAdapter = new Shipment_Payment_Adapter(getContext(),list);
        listView.setAdapter(shipmentSelectAdapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            shipment = (Shipment) bundle.getSerializable("shipment");
            if (shipment != null) {
                shipment_id = shipment.getId();
                list = shipmentDao.Select_Shipment(shipment_id);
                shipmentSelectAdapter = new Shipment_Payment_Adapter(getContext(),list);
                listView.setAdapter(shipmentSelectAdapter);
            }
        }
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thanh toán");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view.findViewById(R.id.bt_payment2).setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Shipment_Fragment()).commit();
        });
        return view;
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