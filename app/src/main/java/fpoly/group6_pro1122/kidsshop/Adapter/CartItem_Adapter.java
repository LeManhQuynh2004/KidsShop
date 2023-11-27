package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Intefaces.OnChange_Price;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class CartItem_Adapter extends RecyclerView.Adapter<CartItem_Adapter.CartViewHolder> {

    private Context context;
    private ArrayList<CartItem> list;
    private ProductDao productDao;
    private CartItemDao cartItemDao;
    private OnChange_Price onChange;

    public CartItem_Adapter(Context context, ArrayList<CartItem> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
        cartItemDao = new CartItemDao(context);
    }

    public void setItemClickListener(OnChange_Price listener) {
        this.onChange = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cartitem, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = list.get(position);
        Product product = productDao.SelectID(String.valueOf(cartItem.getProduct_id()));
        holder.bindData(cartItem, product);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_quantity, tv_price, tv_signal, tv_sum;
        ImageView img_item_cart, img_delete;
        CheckBox chkStatus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_item_cart);
            tv_price = itemView.findViewById(R.id.tv_price_item_cart);
            tv_quantity = itemView.findViewById(R.id.tv_quantity_item_cart);
            img_item_cart = itemView.findViewById(R.id.img_item_cart);
            tv_signal = itemView.findViewById(R.id.bt_signal_item_cart);
            tv_sum = itemView.findViewById(R.id.bt_sum_item_cart);
            img_delete = itemView.findViewById(R.id.img_delete_cartItem);
            chkStatus = itemView.findViewById(R.id.chk_status);
        }

        public void bindData(CartItem cartItem, Product product) {
            tv_name.setText(product.getProduct_name());
            tv_quantity.setText(String.valueOf(cartItem.getQuantity()));

            Glide.with(context)
                    .load(product.getImage())
                    .placeholder(R.drawable.productimg)
                    .into(img_item_cart);

            tv_price.setText(String.format("$%s", cartItem.getTotal_price()));

            if (cartItem.getStatus() == 0) {
                chkStatus.setChecked(false);
            } else {
                chkStatus.setChecked(true);
            }

            chkStatus.setOnClickListener(view -> {
                int total_price = 0;
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getStatus() == 1) {
                        total_price += list.get(j).getTotal_price();
                    }
                }

                if (chkStatus.isChecked()) {
                    cartItem.setStatus(1);
                    cartItemDao.updateData(cartItem);
                    onChange.UpdateItem(total_price + (product.getProduct_price() * cartItem.getQuantity()));
                } else {
                    if (total_price > 0) {
                        cartItem.setStatus(0);
                        cartItemDao.updateData(cartItem);
                        onChange.UpdateItem(total_price - (product.getProduct_price() * cartItem.getQuantity()));
                    } else {
                        onChange.UpdateItem(0);
                    }
                }
            });

            tv_sum.setOnClickListener(view -> {
                int quantity = cartItem.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    cartItem.setQuantity(quantity);
                    cartItem.setTotal_price(product.getProduct_price() * quantity);
                    UpdateDate(cartItem);
                } else {
                    Toast.makeText(context, "Không thực hiện được", Toast.LENGTH_SHORT).show();
                }
            });

            tv_signal.setOnClickListener(view -> {
                int quantity = cartItem.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    cartItem.setQuantity(quantity);
                    cartItem.setTotal_price(product.getProduct_price() * quantity);
                    UpdateDate(cartItem);
                } else {
                    Toast.makeText(context, "Không thực hiện được", Toast.LENGTH_SHORT).show();
                }
            });

            img_delete.setOnClickListener(view -> {
                DeleteItem(cartItem);
            });

            int total_price = 0;
            for (int j = 0; j < list.size(); j++) {
                if (cartItem.getStatus() == 1) {
                    total_price += list.get(j).getTotal_price();
                }
            }
            onChange.UpdateItem(total_price);
        }

        private void UpdateDate(CartItem cartItem) {
            if (cartItemDao.updateData(cartItem)) {
                int total_price = 0;
                for (int j = 0; j < list.size(); j++) {
                    if (cartItem.getStatus() == 1) {
                        total_price += list.get(j).getTotal_price();
                    }
                }
                onChange.UpdateItem(total_price);
                tv_quantity.setText(String.valueOf(cartItem.getQuantity()));
                tv_price.setText(String.format("$%s", cartItem.getTotal_price()));
            } else {
                Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
            }
        }
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
