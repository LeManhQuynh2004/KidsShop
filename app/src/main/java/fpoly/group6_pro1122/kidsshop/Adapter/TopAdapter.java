package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Model.Top;
import fpoly.group6_pro1122.kidsshop.R;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {
    Context context;

    ArrayList<Top> list;

    public TopAdapter(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Top top = list.get(position);
        if (top != null) {
            holder.tv_name.setText(top.getProduct_name());
            holder.tv_quantity.setText("Số lượng bán :" + top.getQuantity());
            holder.tv_price.setText("Tổng tiền bán :" + top.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_quantity, tv_price;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_item_top);
            tv_quantity = itemView.findViewById(R.id.tv_quantity_item_top);
            tv_price = itemView.findViewById(R.id.tv_price_item_top);
        }
    }
}
