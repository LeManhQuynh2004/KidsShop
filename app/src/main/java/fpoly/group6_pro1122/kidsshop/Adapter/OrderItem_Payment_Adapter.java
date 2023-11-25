package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class OrderItem_Payment_Adapter extends BaseAdapter {
    Context context;
    ArrayList<OrderItem> list;
    ProductDao productDao;

    public OrderItem_Payment_Adapter(Context context, ArrayList<OrderItem> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
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
    class CartView_Payment_Holder {
        TextView tv_name, tv_quantity, tv_price;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CartView_Payment_Holder cartViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cartitem_payment, viewGroup, false);
            cartViewHolder = new CartView_Payment_Holder();
            cartViewHolder.tv_name = view.findViewById(R.id.tv_name_item_cartItem_Payment);
            cartViewHolder.tv_price = view.findViewById(R.id.tv_price_item_cartItem_Payment);
            cartViewHolder.tv_quantity = view.findViewById(R.id.tv_quantity_item_cartItem_Payment);
            view.setTag(cartViewHolder);
        } else {
            cartViewHolder = (CartView_Payment_Holder) view.getTag();
        }
        OrderItem orderItem = list.get(i);
        if(orderItem != null){
            Product product = productDao.SelectID(String.valueOf(orderItem.getProduct_id()));
            cartViewHolder.tv_name.setText(product.getProduct_name());
            cartViewHolder.tv_price.setText("$"+product.getProduct_price()+"");
            cartViewHolder.tv_quantity.setText("x"+orderItem.getQuantity());
        }
        return view;
    }
}
