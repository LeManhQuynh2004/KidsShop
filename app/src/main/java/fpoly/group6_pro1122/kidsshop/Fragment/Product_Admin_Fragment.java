package fpoly.group6_pro1122.kidsshop.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Category_Spinner;
import fpoly.group6_pro1122.kidsshop.Dao.CategoryDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.R;

public class Product_Admin_Fragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    Toolbar toolbar;
    Spinner spinner_category;

    Category_Spinner categorySpinner;
    Uri imageUri;
    CategoryDao categoryDao;
    ArrayList<Category> list_category = new ArrayList();
    EditText ed_name,ed_price,ed_quantity,ed_describe;
    ImageView imgUpLoad;
    ProductDao productDao;
    ArrayList<Product> list_product = new ArrayList<>();
    int category_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_admin, container, false);
        toolbar =  view.findViewById(R.id.toolbar_product_admin);
        recyclerView = view.findViewById(R.id.recyclerView_Product_admin);
        productDao = new ProductDao(getContext());
        list_product = productDao.SelectAll();
        view.findViewById(R.id.fab_product_admin).setOnClickListener(v -> {

        });
        return view;
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        ed_name = dialogView.findViewById(R.id.ed_name_dialog_product);
        ed_price = dialogView.findViewById(R.id.ed_price_dialog_product);
        ed_quantity = dialogView.findViewById(R.id.ed_quantity_dialog_product);
        ed_describe = dialogView.findViewById(R.id.ed_describe_dialog_product);
        imgUpLoad = dialogView.findViewById(R.id.img_dialog_product);
        spinner_category = dialogView.findViewById(R.id.spinner_category_id_dialog);

        imgUpLoad.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        create_spinner_category();
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category_id = list_category.get(i).getCategory_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialogView.findViewById(R.id.bt_save_dialog_product).setOnClickListener(dv -> {
            String name = ed_name.getText().toString().trim();
            String price = ed_price.getText().toString().trim();
            String quantity = ed_quantity.getText().toString().trim();
            String describe = ed_describe.getText().toString().trim();
            if(validateForm(name,price,quantity,describe)  && imageUri != null){
                String imagePath = imageUri.toString();
                Product productNew = new Product();
                productNew.setProduct_name(name);
                productNew.setQuantity(Integer.parseInt(quantity));
                productNew.setProduct_price(Integer.parseInt(price));
                productNew.setDescribe(describe);
                productNew.setImage(imagePath);
                productNew.setCategory_id(category_id);
                if (productDao.insertData(productNew)) {
                    Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                    list_product.add(productNew);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.bt_cancle_dialog_product).setOnClickListener(dv ->{
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
    private void create_spinner_category(){
        categoryDao = new CategoryDao(getContext());
        list_category = categoryDao.getAll();
        categorySpinner = new Category_Spinner(getContext(),list_category);
        spinner_category.setAdapter(categorySpinner);
    }
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (imageUri != null) {
                        imgUpLoad.setImageURI(imageUri);
                    }
                }
            }
    );
    private boolean validateForm(String name, String price, String describe, String quantity) {
        boolean isCheck = true;
        if (name.isEmpty() || price.isEmpty() || describe.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}