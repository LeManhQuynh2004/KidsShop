package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Intefaces.OnChange_Price;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class CartItem_Adapter extends BaseAdapter {
    Context context;
    ArrayList<CartItem> list;
    ProductDao productDao;
    int quantity = 1;
    CartItemDao cartItemDao;
    public static final String TAG = "CartItem_Adapter";
    private OnChange_Price onChange;

    public void setItemClickListener(OnChange_Price listener) {
        this.onChange = listener;
    }

    public CartItem_Adapter(Context context, ArrayList<CartItem> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
        cartItemDao = new CartItemDao(context);
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

    class CartViewHolder {
        TextView tv_name, tv_quantity, tv_price, tv_signal, tv_sum;
        ImageView img_item_cart, img_delete;
        CheckBox chkStatus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CartViewHolder cartViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cartitem, viewGroup, false);
            cartViewHolder = new CartViewHolder();
            cartViewHolder.tv_name = view.findViewById(R.id.tv_name_item_cart);
            cartViewHolder.tv_price = view.findViewById(R.id.tv_price_item_cart);
            cartViewHolder.tv_quantity = view.findViewById(R.id.tv_quantity_item_cart);
            cartViewHolder.img_item_cart = view.findViewById(R.id.img_item_cart);
            cartViewHolder.tv_signal = view.findViewById(R.id.bt_signal_item_cart);
            cartViewHolder.tv_sum = view.findViewById(R.id.bt_sum_item_cart);
            cartViewHolder.img_delete = view.findViewById(R.id.img_delete_cartItem);
            cartViewHolder.chkStatus = view.findViewById(R.id.chk_status);
            view.setTag(cartViewHolder);
        } else {
            cartViewHolder = (CartViewHolder) view.getTag();
        }
        CartItem cartItem = list.get(i);
        Product product = productDao.SelectID(String.valueOf(cartItem.getProduct_id()));
        cartViewHolder.tv_name.setText(product.getProduct_name());
        quantity = Integer.parseInt(String.valueOf(cartItem.getQuantity()));
        Glide.with(context).load(product.getImage()).placeholder(R.drawable.productimg).into(cartViewHolder.img_item_cart);
        cartViewHolder.tv_quantity.setText(cartItem.getQuantity() + "");
        cartViewHolder.tv_price.setText("$" + cartItem.getTotal_price());
        if (cartItem.getStatus() == 0) {
            cartViewHolder.chkStatus.setChecked(false);
        } else {
            cartViewHolder.chkStatus.setChecked(true);
        }
        cartViewHolder.chkStatus.setOnClickListener(view1 -> {
            int total_price = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getStatus() == 1) {
                    total_price += list.get(j).getTotal_price();
                }
            }

            Log.d(TAG, "getView: " + total_price);

            if (cartViewHolder.chkStatus.isChecked()) {
                cartItem.setStatus(1);
                cartItemDao.updateData(cartItem);
                Log.d(TAG, "getView: " + cartItem.getTotal_price());
                Log.d(TAG, "getView: " + cartItem.getQuantity());
                onChange.UpdateItem(total_price + (product.getProduct_price() * cartItem.getQuantity()));
                Log.d(TAG, "getView: " + total_price);
            } else {
                if (total_price > 0) {
                    cartItem.setStatus(0);
                    cartItemDao.updateData(cartItem);
                    Log.d(TAG, "getView: " + "false");
                    onChange.UpdateItem(total_price - (product.getProduct_price() * cartItem.getQuantity()));
                } else {
                    onChange.UpdateItem(0);
                }
            }
        });
        cartViewHolder.tv_sum.setOnClickListener(view1 -> {
            quantity = Integer.parseInt(String.valueOf(cartItem.getQuantity()));
            quantity++;
            cartItem.setQuantity(quantity);
            cartItem.setTotal_price(product.getProduct_price() * quantity);
            if (cartItemDao.updateData(cartItem)) {
                int total_price = 0;
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(i).getStatus() == 1) {
                        if (cartItem.getStatus() == 1) {
                            total_price += list.get(j).getTotal_price();
                        }
                    }
                }
                onChange.UpdateItem(total_price);
                cartViewHolder.tv_quantity.setText(quantity + "");
                cartViewHolder.tv_price.setText("$" + (product.getProduct_price() * quantity));
            } else {
                Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
            }
        });
        cartViewHolder.tv_signal.setOnClickListener(view1 -> {
            quantity = Integer.parseInt(String.valueOf(cartItem.getQuantity()));
            if (quantity > 1) {
                quantity--;
                cartItem.setQuantity(quantity);
                cartItem.setTotal_price(product.getProduct_price() * quantity);
                if (cartItemDao.updateData(cartItem)) {
                    int total_price = 0;
                    for (int j = 0; j < list.size(); j++) {
                        if (cartItem.getStatus() == 1) {
                            total_price += list.get(j).getTotal_price();
                        }
                    }
                    onChange.UpdateItem(total_price);
                    cartViewHolder.tv_quantity.setText(quantity + "");
                    cartViewHolder.tv_price.setText("$" + (product.getProduct_price() * quantity));
                } else {
                    Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Không thực hiện được", Toast.LENGTH_SHORT).show();
            }
        });
        cartViewHolder.img_delete.setOnClickListener(view1 -> {
            DeleteItem(cartItem);
        });
        int total_price = 0;
        for (int j = 0; j < list.size(); j++) {
            if (cartItem.getStatus() == 1) {
                total_price += list.get(j).getTotal_price();
            }
        }
        onChange.UpdateItem(total_price);
        return view;
    }

    private void DeleteItem(CartItem cartItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (cartItemDao.deleteData(cartItem)) {
                    Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    list.remove(cartItem);
                    int total_price = 0;
                    for (int j = 0; j < list.size(); j++) {
                        total_price += list.get(j).getTotal_price();
                    }
                    onChange.UpdateItem(total_price);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
