package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Fragment.CategoryDetailsFragment;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class CategoryUser_Adapter extends RecyclerView.Adapter<CategoryUser_Adapter.CategoryUser> {
    ArrayList<Category> list;
    Context context;
    public static final String TAG = "CategoryUser";
    public CategoryUser_Adapter(ArrayList<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);
        return new CategoryUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryUser holder, int position) {
        Category category = list.get(position);
        holder.tv_name.setText(category.getName());
        Glide.with(context)
                .load(category.getImage())
                .placeholder(R.drawable.productimg)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(v->{
            CategoryDetailsFragment detailsFragment = new CategoryDetailsFragment();
            Category send_category = list.get(position);
            Log.d(TAG, "onBindViewHolder: "+send_category.getCategory_id());
            Bundle bundle = new Bundle();
            bundle.putInt("id",send_category.getCategory_id());
            detailsFragment.setArguments(bundle);
            ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailsFragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryUser extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name;
        public CategoryUser(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_category_user);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
