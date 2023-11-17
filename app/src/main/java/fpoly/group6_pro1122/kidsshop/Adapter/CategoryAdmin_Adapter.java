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

public class CategoryAdmin_Adapter extends RecyclerView.Adapter<CategoryAdmin_Adapter.CategoryAdmin> {
    ArrayList<Category> list = new ArrayList<>();
    Context context;

    public CategoryAdmin_Adapter(ArrayList<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_admin,parent,false);
        return new CategoryAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdmin holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CategoryAdmin extends RecyclerView.ViewHolder {
        public CategoryAdmin(@NonNull View itemView) {
            super(itemView);
        }
    }
}
