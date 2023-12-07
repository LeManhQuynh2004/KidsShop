package fpoly.group6_pro1122.kidsshop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class UserManagement_Adapter extends RecyclerView.Adapter<UserManagement_Adapter.ViewHolder> {
    Context context;
    ArrayList<User> list;

    EditText edt_ten, edt_email;
    ImageView img_anh;
    Button btn_save, btn_cancel;
    UserDao userDao;

    public UserManagement_Adapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    private boolean isEmail(String str) {
        return str.matches(
                "[a-zA-Z0-9_.]+@[a-zA-Z]+\\.+[a-z]+"
        );
    }

    private boolean isFullName(String str) {
        return str.matches("[a-z A-Z]+");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.tv_id.setText("Mã người dùng: " + user.getId());
        holder.tv_name.setText("Họ tên: " + user.getFullname());
        holder.tv_email.setText("Email: " + user.getEmail());
        Glide.with(context).load(user.getImage()).placeholder(R.drawable.anhdaidien).into(holder.img_daiDien);
        holder.btn_xoa.setOnClickListener(v -> {
            DeleteUser(position);
        });
//        holder.btn_sua.setOnClickListener(v -> {
//            UpdateUser(position);
//        });
    }

//    private void UpdateUser(int position) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_sua_user, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(view);
//        AlertDialog alertDialog = builder.create();
//        edt_ten = view.findViewById(R.id.edt_name_user);
//        edt_email = view.findViewById(R.id.edt_email_user);
//        img_anh = view.findViewById(R.id.img_user);
//        btn_save = view.findViewById(R.id.btn_save_user);
//        btn_cancel = view.findViewById(R.id.btn_cancel_user);
//        User user = list.get(position);
//        edt_ten.setText(user.getFullname());
//        edt_email.setText(user.getEmail());
//        Glide.with(context).load(user.getImage()).placeholder(R.drawable.anhdaidien).into(img_anh);
//        btn_save.setOnClickListener(v -> {
//            if (edt_ten.getText().toString().isEmpty() || edt_email.getText().toString().isEmpty()) {
//                Toast.makeText(context, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (!isFullName(edt_ten.getText().toString().trim())){
//                Toast.makeText(context, "Sai định dạng họ tên", Toast.LENGTH_SHORT).show();
//                edt_ten.requestFocus();
//                return;
//            }
//            if (!isEmail(edt_email.getText().toString().trim())){
//                Toast.makeText(context, "Sai định dạng email", Toast.LENGTH_SHORT).show();
//                edt_email.requestFocus();
//                return;
//            }
//            String name = edt_ten.getText().toString().trim();
//            String email = edt_email.getText().toString().trim();
//            User userNew = new User(user.getId(), user.getPassword(), name, email, user.getImage(), user.getPhone(), user.getAddress(), user.getRole());
//            userDao = new UserDao(context);
//            if (userDao.updateData(userNew)) {
//               list.set(position,userNew);
//                notifyDataSetChanged();
//                Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show();
//                alertDialog.dismiss();
//            } else {
//                Toast.makeText(context, R.string.update_not_success, Toast.LENGTH_SHORT).show();
//            }
//        });
//        btn_cancel.setOnClickListener(v -> {
//            alertDialog.dismiss();
//        });
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.show();
//    }


    private void DeleteUser(int position) {
        User user = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setIcon(R.drawable.baseline_warning_24).setTitle("Cảnh báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userDao = new UserDao(context);
                boolean check = userDao.deleteUser(user);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_id, tv_email;
        ImageView img_daiDien, btn_xoa, btn_sua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_user);
            tv_id = itemView.findViewById(R.id.tv_id_user);
            tv_email = itemView.findViewById(R.id.tv_email_user);
            img_daiDien = itemView.findViewById(R.id.img_anh_user);
//            btn_sua = itemView.findViewById(R.id.btn_update_user);
            btn_xoa = itemView.findViewById(R.id.btn_delete_user);
        }
    }
}
