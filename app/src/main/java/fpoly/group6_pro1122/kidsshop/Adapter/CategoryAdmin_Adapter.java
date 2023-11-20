package fpoly.group6_pro1122.kidsshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.CategoryDao;
import fpoly.group6_pro1122.kidsshop.Fragment.Category_Admin_Fragment;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Intefaces.ItemClickListener;
import fpoly.group6_pro1122.kidsshop.R;

public class CategoryAdmin_Adapter extends RecyclerView.Adapter<CategoryAdmin_Adapter.CategoryAdmin> {
    ArrayList<Category> list;
    Context context;
    CategoryDao categoryDao;
    Button btn_update, btn_cancel;
    EditText edt_name, edt_describe;
    ImageView imageView;
    Uri imageUri;

    public void setImageUri(Uri uri){
        this.imageUri = uri;
        notifyDataSetChanged();
    }
    Category_Admin_Fragment admin_fragment;


    public CategoryAdmin_Adapter(ArrayList<Category> list, Context context) {
        this.list = list;
        this.context = context;
        categoryDao = new CategoryDao(context);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CategoryAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_admin, parent, false);
        return new CategoryAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdmin holder, int position) {
        Category category = list.get(position);
        holder.tv_id.setText("Mã thể loại: " + category.getCategory_id() + "");
        holder.tv_name.setText(category.getName());
        holder.tv_describe.setText("Mô tả thể loại: " + category.getDescribe());
        Glide.with(context)
                .load(category.getImage())
                .placeholder(R.drawable.image)
                .into(holder.imageView);
        holder.btn_del.setOnClickListener(view -> {
            DeleteCategory(position);
        });
        holder.btn_update.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
        });
    }

    private void DeleteCategory(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setIcon(R.drawable.baseline_warning_24).setTitle("Cảnh báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = list.get(position).getCategory_id();
                boolean check = categoryDao.deleteTL(id);
                if (check) {
                    list.remove(position);
                    Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", null).show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryAdmin extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name, tv_id, tv_describe;
        ImageView btn_del, btn_update;

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
