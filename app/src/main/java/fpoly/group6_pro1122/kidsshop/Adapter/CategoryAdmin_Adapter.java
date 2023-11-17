package fpoly.group6_pro1122.kidsshop.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdmin_Adapter extends RecyclerView.Adapter<CategoryAdmin_Adapter.CategoryAdmin> {
    @NonNull
    @Override
    public CategoryAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
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
