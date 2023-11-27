package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.R;

public class Star_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> list;
    TextView tv_number_start;
    TextView tv_satisfied_start;

    public Star_Adapter(Context context, ArrayList<Integer> list) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_start,viewGroup,false);
            tv_number_start = view.findViewById(R.id.tv_star_item_Star);
            tv_satisfied_start = view.findViewById(R.id.tv_satisfied_item_Star);
        }
        Integer integer = list.get(i);
        tv_number_start.setText(integer+"");
        if(integer == 1){
            tv_satisfied_start.setText("(Rất thất vọng)");
        }else if(integer == 2){
            tv_satisfied_start.setText("(Không hài lòng)");
        }else if(integer == 3){
            tv_satisfied_start.setText("(Tạm hài lòng)");
        }else if(integer == 4){
            tv_satisfied_start.setText("(Hài lòng)");
        }else{
            tv_satisfied_start.setText("(Tuyệt vời)");
        }
        return view;
    }
}
