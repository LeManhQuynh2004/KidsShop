package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.R;

public class CategoryUser_Adapter extends RecyclerView.Adapter<CategoryUser_Adapter.CategoryUser> {
    ArrayList<Category> list = new ArrayList<>();
    Context context;

    public CategoryUser_Adapter(ArrayList<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryUser holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryUser extends RecyclerView.ViewHolder {
        public CategoryUser(@NonNull View itemView) {
            super(itemView);
        }
    }
}
