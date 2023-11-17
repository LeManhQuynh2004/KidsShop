package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class Product_Customer_Adapter extends RecyclerView.Adapter<Product_Customer_Adapter.Product_Customer_ViewHolder> {
    Context context;
    ArrayList<Product> list;

    public Product_Customer_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Product_Customer_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new Product_Customer_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_Customer_ViewHolder holder, int position) {
        Product product = list.get(position);
        if (product != null) {
            Glide.with(context).load(product.getImage()).placeholder(R.drawable.banner1).into(holder.imgProduct);
            holder.name.setText(product.getProduct_name());
            holder.price.setText(product.getProduct_price() + "");
            holder.quantity.setText(product.getQuantity() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Product_Customer_ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView imgProduct;

        public Product_Customer_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_item_product);
            price = itemView.findViewById(R.id.tv_price_item_product);
            quantity = itemView.findViewById(R.id.tv_quantity_item_product);
            imgProduct = itemView.findViewById(R.id.img_item_product);
        }
    }
}
