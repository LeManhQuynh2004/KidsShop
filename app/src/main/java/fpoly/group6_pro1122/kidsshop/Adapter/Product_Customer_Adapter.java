package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.CartItemDao;
import fpoly.group6_pro1122.kidsshop.Fragment.Details_Fragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class Product_Customer_Adapter extends RecyclerView.Adapter<Product_Customer_Adapter.Product_Customer_ViewHolder> {
    Context context;
    ArrayList<Product> list;
    CartItemDao cartItemDao;
    ArrayList<CartItem> list_cartItem;

    public Product_Customer_Adapter(Context context, ArrayList<Product> list,ArrayList<CartItem> list_cartItem) {
        this.context = context;
        this.list = list;
        cartItemDao = new CartItemDao(context);
        this.list_cartItem = list_cartItem;
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
            Glide.with(context).load(product.getImage()).placeholder(R.drawable.productimg).into(holder.imgProduct);
            holder.name.setText(product.getProduct_name());
            holder.price.setText("$"+product.getProduct_price());
        }
        holder.fab.setOnClickListener(view -> {
            CartItem cartItem = new CartItem();
            cartItem.setProduct_id(product.getProduct_id());
            cartItem.setQuantity(1);
            if(cartItemDao.insertData(cartItem)){
                Toast.makeText(context,R.string.add_success, Toast.LENGTH_SHORT).show();
                list_cartItem.add(cartItem);
                notifyDataSetChanged();
            }else{
                Toast.makeText(context,R.string.add_not_success, Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(view -> {
            Details_Fragment show_detail_fragment = new Details_Fragment();
            Product send_product = list.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("id",send_product.getProduct_id());
            bundle.putSerializable("product", send_product);
            show_detail_fragment.setArguments(bundle);
            ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, show_detail_fragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Product_Customer_ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView imgProduct;
        FloatingActionButton fab;

        public Product_Customer_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_item_product);
            price = itemView.findViewById(R.id.tv_price_item_product);
            imgProduct = itemView.findViewById(R.id.img_item_product);
            fab = itemView.findViewById(R.id.fab_add_cartItem);
        }
    }
}
