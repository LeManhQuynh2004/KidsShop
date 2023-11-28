package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.Evaluation;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Evaluation_Adapter extends RecyclerView.Adapter<Evaluation_Adapter.EvaluationViewHolder> {
    Context context;
    ArrayList<Evaluation> list;
    UserDao userDao;

    public static final String TAG = "Evaluation_Adapter";
    User user;

    public Evaluation_Adapter(Context context, ArrayList<Evaluation> list) {
        this.context = context;
        this.list = list;
        userDao = new UserDao(context);
    }

    @NonNull
    @Override
    public EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_valuation, parent, false);
        return new EvaluationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationViewHolder holder, int position) {
        Evaluation evaluation = list.get(position);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("LIST_USER", Context.MODE_PRIVATE);
//        String email = sharedPreferences.getString("EMAIL", "");
        if (evaluation != null) {
            user = userDao.SelectIDTwo(String.valueOf(evaluation.getId()));
            if (user == null || user.getFullname() == null || user.getFullname().isEmpty()) {
                holder.tv_name.setText("Khách hàng của KidsShop");
            } else {
                String roleName = (user.getRole() == 0) ? "(Quản trị viên)" : "(Khách hàng)";
                holder.tv_name.setText(user.getFullname() + " " + roleName);
            }

            holder.tv_comment.setText(evaluation.getComment());
            holder.tv_start.setText("Đánh giá: " + evaluation.getStart());
            int startValue = evaluation.getStart();
            if (startValue == 1) {
                holder.tv_satisfied_item_Evaluation.setText("(Rất thất vọng)");
            } else if (startValue == 2) {
                holder.tv_satisfied_item_Evaluation.setText("(Không hài lòng)");
            } else if (startValue == 3) {
                holder.tv_satisfied_item_Evaluation.setText("(Tạm hài lòng)");
            } else if (startValue == 4) {
                holder.tv_satisfied_item_Evaluation.setText("(Hài lòng)");
            } else {
                holder.tv_satisfied_item_Evaluation.setText("(Tuyệt vời)");
            }

            holder.tv_date.setText(evaluation.getDate() + " " + evaluation.getTime());
        }
//        holder.itemView.setOnLongClickListener(view -> {
//            try {
//                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
//                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(item -> {
//                    if (item.getItemId() == R.id.menu_delete) {
//                        showDeleteDialog(position);
//                    } else {
//                        if (itemClickListener != null) {
//                            itemClickListener.UpdateItem(position);
//                        }
//                    }
//                    return true;
//                });
//                popupMenu.show();
//            } catch (Exception e) {
//                Log.e(TAG, "Error handling long click", e);
//            }
//            return true;
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class EvaluationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_comment, tv_start, tv_date, tv_satisfied_item_Evaluation;

        public EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_user_name_item_evaluation);
            tv_comment = itemView.findViewById(R.id.tv_comment_item_evaluation);
            tv_start = itemView.findViewById(R.id.tv_star_item_evaluation);
            tv_date = itemView.findViewById(R.id.tv_date_item_evaluation);
            tv_satisfied_item_Evaluation = itemView.findViewById(R.id.tv_satisfied_item_Evaluation);
        }
    }
}
