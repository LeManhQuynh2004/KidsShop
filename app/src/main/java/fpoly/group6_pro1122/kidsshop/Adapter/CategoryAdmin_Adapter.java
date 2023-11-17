package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        Category category = list.get(position);
        holder.tv_id.setText(category.getCategory_id()+"");
        holder.tv_name.setText(category.getName());
        holder.tv_describe.setText(category.getDescribe());
        Glide.with(context)
                .load(category.getImage())
                .into(holder.imageView);
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryAdmin extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name, tv_id,tv_describe;
        Button btn_del,btn_update;
        public CategoryAdmin(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item_category_admin);
            tv_name = itemView.findViewById(R.id.tv_name_item_category_admin);
            tv_id = itemView.findViewById(R.id.tv_id_item_categoroy_admin);
            tv_describe = itemView.findViewById(R.id.tv_describe_item_category_admin);
            btn_del = itemView.findViewById(R.id.btn_delete_item_describe_admin);
            btn_update = itemView.findViewById(R.id.btn_update_item_describe_admin);
        }
    }
}
