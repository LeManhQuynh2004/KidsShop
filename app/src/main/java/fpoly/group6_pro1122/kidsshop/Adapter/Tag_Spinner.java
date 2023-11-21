package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Model.Tag;
import fpoly.group6_pro1122.kidsshop.R;

public class Tag_Spinner extends BaseAdapter {
    Context context;
    ArrayList<Tag> list;
    public Tag_Spinner(Context context, ArrayList<Tag> list) {
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
    private static class TagViewHolder {
        TextView tv_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TagViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tag_spinner, viewGroup, false);
            viewHolder = new TagViewHolder();
            viewHolder.tv_name = view.findViewById(R.id.tv_name_spinner_tag);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TagViewHolder) view.getTag();
        }
        Tag tag = list.get(i);
        viewHolder.tv_name.setText(tag.getTag_name());
        return view;
    }
}
