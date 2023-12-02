package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Fragment.Details_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Payment_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.R;

public class Shipment_Select_Adapter extends BaseAdapter{

    Context context;
    ArrayList<Shipment> list;

    public Shipment_Select_Adapter(Context context, ArrayList<Shipment> list) {
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

    class ShipmentViewHolder{
        TextView tv_information,tv_address,tv_Update;
        RadioButton chk_select;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShipmentViewHolder shipmentViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_shipment2, viewGroup, false);
            shipmentViewHolder = new ShipmentViewHolder();
            shipmentViewHolder.tv_information = view.findViewById(R.id.tv_information_shipment2);
            shipmentViewHolder.tv_address = view.findViewById(R.id.tv_address_shipment2);
            shipmentViewHolder.chk_select = view.findViewById(R.id.chk_select_shipment);
            shipmentViewHolder.tv_Update = view.findViewById(R.id.tv_update_shipment);
            view.setTag(shipmentViewHolder);
        } else {
            shipmentViewHolder = (ShipmentViewHolder) view.getTag();
        }
        Shipment shipment = list.get(i);
        if(shipment != null){
            shipmentViewHolder.tv_information.setText(shipment.getName() +" | "+shipment.getPhone());
            shipmentViewHolder.tv_address.setText(shipment.getAddress()+"\n"+shipment.getDistrict()+" - "+shipment.getCity());
        }
        shipmentViewHolder.chk_select.setOnClickListener(view1 -> {
            if (shipmentViewHolder.chk_select.isChecked()) {
                Payment_Fragment paymentFragment = new Payment_Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("shipment", shipment);
                paymentFragment.setArguments(bundle);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, paymentFragment)
                        .commit();
            }
        });
        shipmentViewHolder.tv_Update.setOnClickListener(view1 -> {

        });
        return view;
    }
}
