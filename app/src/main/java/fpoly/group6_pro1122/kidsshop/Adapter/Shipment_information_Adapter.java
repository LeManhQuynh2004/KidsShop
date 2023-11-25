package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Fragment.Shipment_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.R;

public class Shipment_information_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Shipment> list;

    public Shipment_information_Adapter(Context context, ArrayList<Shipment> list) {
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
        TextView tv_information,tv_address;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShipmentViewHolder shipmentViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_shipment, viewGroup, false);
            shipmentViewHolder = new ShipmentViewHolder();
            shipmentViewHolder.tv_information = view.findViewById(R.id.tv_information_shipment);
            shipmentViewHolder.tv_address = view.findViewById(R.id.tv_address_shipment);
            view.setTag(shipmentViewHolder);
        } else {
            shipmentViewHolder = (ShipmentViewHolder) view.getTag();
        }
        Shipment shipment = list.get(i);
        if(shipment != null){
            shipmentViewHolder.tv_information.setText(shipment.getName().toUpperCase() +" | "+shipment.getPhone());
            shipmentViewHolder.tv_information.setTypeface(null, Typeface.BOLD);
            shipmentViewHolder.tv_address.setText(shipment.getAddress()+"\n"+shipment.getDistrict()+" - "+shipment.getCity());
        }
        return view;
    }
}
