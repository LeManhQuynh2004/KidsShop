package fpoly.group6_pro1122.kidsshop.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.WishListDao;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.WishList;
import fpoly.group6_pro1122.kidsshop.R;

public class WishListAdapter extends BaseAdapter {
    Context context;
    ArrayList<WishList> list;
    ProductDao productDao;
    WishListDao wishListDao;

    public WishListAdapter(Context context, ArrayList<WishList> list) {
        this.context = context;
        this.list = list;
        wishListDao = new WishListDao(context);
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
    class WishListHolder {
        TextView tv_name,tv_price;
        ImageView img_item_cart,img_delete;
        CheckBox chk_status;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WishListHolder wishListHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_wishlist, viewGroup, false);
            wishListHolder = new WishListHolder();
            wishListHolder.tv_name = view.findViewById(R.id.tv_name_item_wishlist);
            wishListHolder.tv_price = view.findViewById(R.id.tv_price_item_wishlist);
            wishListHolder.img_item_cart = view.findViewById(R.id.img_item_wishlist);
            wishListHolder.img_delete = view.findViewById(R.id.img_delete_wishlist);
            wishListHolder.chk_status = view.findViewById(R.id.chk_status_wishList);
            view.setTag(wishListHolder);
        } else {
            wishListHolder = (WishListHolder) view.getTag();
        }
        WishList wishList = list.get(i);
        if(wishList != null){
            Product product = productDao.SelectID(String.valueOf(wishList.getProduct_id()));
            wishListHolder.tv_name.setText(product.getProduct_name());
            wishListHolder.tv_price.setText("$"+product.getProduct_price()+"");
            Glide.with(context).load(product.getImage()).placeholder(R.drawable.productimg).into(wishListHolder.img_item_cart);
            if(wishList.getStatus() == 0){
                wishListHolder.chk_status.setChecked(false);
            }else{
                wishListHolder.chk_status.setChecked(true);
            }
        }
        wishListHolder.chk_status.setOnClickListener(view1 -> {
            if(wishListHolder.chk_status.isChecked()){
                wishList.setStatus(1);
                wishListDao.updateData(wishList);
                notifyDataSetChanged();
            }else{
                wishList.setStatus(0);
                wishListDao.updateData(wishList);
                notifyDataSetChanged();
            }
        });
        wishListHolder.img_delete.setOnClickListener(view1 -> {
            DeleteItem(wishList);
        });
        return view;
    }
    private void DeleteItem(WishList wishList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (wishListDao.deleteData(wishList)) {
                    Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    list.remove(wishList);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
