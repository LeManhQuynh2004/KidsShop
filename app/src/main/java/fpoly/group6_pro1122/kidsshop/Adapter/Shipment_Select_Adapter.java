package fpoly.group6_pro1122.kidsshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.ShipmentDao;
import fpoly.group6_pro1122.kidsshop.Fragment.Details_Fragment;
import fpoly.group6_pro1122.kidsshop.Fragment.Payment_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;
import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Shipment_Select_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Shipment> list;
    int result = 0;

    private boolean isPhone(String str) {
        return str.matches("(09|08|03)\\d{8}");
    }

    private boolean isFullName(String str) {
        return str.matches("[a-z A-Z]+");
    }

    private boolean isAddress(String str) {
        return str.matches("[a-z A-Z 0-9]+");
    }

    EditText ed_name, ed_phone, ed_district, ed_city, ed_address;
    RadioButton rb_1, rb_2;
    ShipmentDao shipmentDao;

    public Shipment_Select_Adapter(Context context, ArrayList<Shipment> list) {
        this.context = context;
        this.list = list;
        shipmentDao = new ShipmentDao(context);
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

    class ShipmentViewHolder {
        TextView tv_information, tv_address, tv_Update;
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
        if (shipment != null) {
            shipmentViewHolder.tv_information.setText(shipment.getName() + " | " + shipment.getPhone());
            shipmentViewHolder.tv_address.setText(shipment.getAddress() + "\n" + shipment.getDistrict() + " - " + shipment.getCity());
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
        view.setOnLongClickListener(view1 -> {
            DeleteItem(i);
            return false;
        });
        shipmentViewHolder.tv_Update.setOnClickListener(view1 -> {
            Shipment shipmentUpdate = list.get(i);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_shipment, null);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            ed_name = dialogView.findViewById(R.id.ed_name_add_dialog_shipment);
            ed_phone = dialogView.findViewById(R.id.ed_phone_add_dialog_shipment);
            ed_address = dialogView.findViewById(R.id.ed_address_add_dialog_shipment);
            ed_district = dialogView.findViewById(R.id.ed_district_add_dialog_shipment);
            ed_city = dialogView.findViewById(R.id.ed_city_add_dialog_shipment);
            rb_1 = dialogView.findViewById(R.id.rd_address_type_1_dialog);
            rb_2 = dialogView.findViewById(R.id.rd_address_type_2_dialog);
            ed_name.setText(shipmentUpdate.getName());
            ed_phone.setText(shipmentUpdate.getPhone());
            ed_city.setText(shipmentUpdate.getCity());
            ed_district.setText(shipmentUpdate.getDistrict());
            ed_address.setText(shipmentUpdate.getAddress());
            if(shipmentUpdate.getAddress_type() == 0){
                rb_1.setChecked(true);
            }else{
                rb_2.setChecked(true);
            }
            dialogView.findViewById(R.id.bt_update_shipment).setOnClickListener(view2 -> {
                String name = ed_name.getText().toString().trim();
                String phone = ed_phone.getText().toString().trim();
                String address = ed_address.getText().toString().trim();
                String district = ed_district.getText().toString().trim();
                String city = ed_city.getText().toString().trim();
                if (ValidateForm(city, district, name, phone, address, rb_1, rb_2)) {
                    Shipment shipmentFind = shipmentDao.SelectPhone(phone);
                    if(shipmentFind == null){
                        shipmentUpdate.setName(name);
                        shipmentUpdate.setPhone(phone);
                        shipmentUpdate.setStatus(0);
                        shipmentUpdate.setCity(city);
                        shipmentUpdate.setAddress(address);
                        shipmentUpdate.setDistrict(district);
                        shipmentUpdate.setAddress_type(result);
                        if (shipmentDao.updateData(shipmentUpdate)) {
                            Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show();
                            list.set(i, shipmentUpdate);
                            alertDialog.dismiss();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, R.string.update_not_success, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            dialogView.findViewById(R.id.bt_cancle_shipment).setOnClickListener(view2 -> {
                alertDialog.dismiss();
            });
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        });
        return view;
    }

    private boolean ValidateForm(String city, String district, String name,
                                 String phone, String address, RadioButton rd_type_1, RadioButton rd_type_2) {
        boolean isCheck = true;
        if (city.isEmpty() || district.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || !rd_type_1.isChecked() && !rd_type_2.isChecked()) {
            Toast.makeText(context, "Vui lòng không bỏ trống các trường thông tin", Toast.LENGTH_SHORT).show();
            isCheck = false;
        } else {
            if (!isFullName(name) || !isFullName(city) || !isFullName(district)) {
                Toast.makeText(context, "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (!isAddress(address) || address.length() < 10) {
                Toast.makeText(context, "Nhập sai định dạng địa chỉ", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (!isPhone(phone)) {
                Toast.makeText(context, "Nhập sai định dạng số điện thoại", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (rd_type_1.isChecked()) {
                result = 0;
            } else if (rd_type_2.isChecked()) {
                result = 1;
            }
        }
        return isCheck;
    }
    private void DeleteItem(int position) {
        Shipment shipment = list.get(position);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (shipmentDao.deleteData(shipment)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
