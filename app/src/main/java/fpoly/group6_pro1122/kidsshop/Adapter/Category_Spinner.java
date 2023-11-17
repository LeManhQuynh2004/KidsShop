package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.R;

public class Category_Spinner extends BaseAdapter {

    Context context;
    ArrayList<Category> list;

    public Category_Spinner(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private static class CategoryViewHolder {
        TextView tv_id;
        TextView tv_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CategoryViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_category_spinner, viewGroup, false);
            viewHolder = new CategoryViewHolder();
            viewHolder.tv_id = view.findViewById(R.id.tv_id_spinner_category);
            viewHolder.tv_name = view.findViewById(R.id.tv_name_spinner_category);
            view.setTag(viewHolder);
        } else {
            viewHolder = (CategoryViewHolder) view.getTag();
        }
        Category category = list.get(i);
        viewHolder.tv_id.setText(String.valueOf(category.getCategory_id()));
        viewHolder.tv_name.setText(category.getName());
        return view;
    }
}
