package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Intefaces.ItemClickListener;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class Product_Admin_Adapter extends RecyclerView.Adapter<Product_Admin_Adapter.Product_Admin_ViewHolder>{
    Context context;
    ArrayList<Product>list;
    ProductDao productDao;

    public static final String TAG = "Product_Admin_Adapter";
    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public Product_Admin_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
    }

    @NonNull
    @Override
    public Product_Admin_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_admin
                , parent, false);
        return new Product_Admin_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_Admin_ViewHolder holder, int position) {
        Product product = list.get(position);
        if (product != null) {
            Glide.with(context).load(product.getImage()).placeholder(R.drawable.banner1).into(holder.imgProduct);
            holder.name.setText(product.getProduct_name());
            holder.id.setText("Mã sản phẩm :"+product.getProduct_id());
            holder.price.setText("Giá bán :"+product.getProduct_price() + "");
            holder.quantity.setText("Số lượng :"+product.getQuantity() + "");
            holder.category_id.setText("Mã danh mục :"+product.getCategory_id());
        }
        holder.img_update.setOnClickListener(view -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateItem(position);
                }
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e);
            }
        });
        holder.img_delete.setOnClickListener(view -> {
            DeleteItem(position);
        });
    }

    private void DeleteItem(int position) {
        Product product = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(productDao.deleteData(product)){
                    Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context,R.string.delete_not_success,Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy",null);
        builder.show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Product_Admin_ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity,id,category_id;
        ImageView imgProduct,img_update,img_delete;

        public Product_Admin_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_item_product_admin);
            price = itemView.findViewById(R.id.tv_price_item_product_admin);
            quantity = itemView.findViewById(R.id.tv_quantity_item_product_admin);
            id = itemView.findViewById(R.id.tv_id_item_product_admin);
            category_id = itemView.findViewById(R.id.tv_category_id_item_product_admin);
            imgProduct = itemView.findViewById(R.id.img_item_product_admin);
            img_delete =itemView.findViewById(R.id.bt_delete_item_product_admin);
            img_update = itemView.findViewById(R.id.bt_update_item_product_admin);
        }
    }
}
