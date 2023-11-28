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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Category_Spinner;
import fpoly.group6_pro1122.kidsshop.Adapter.Product_Admin_Adapter;
import fpoly.group6_pro1122.kidsshop.Adapter.Tag_Spinner;
import fpoly.group6_pro1122.kidsshop.Dao.CategoryDao;
import fpoly.group6_pro1122.kidsshop.Dao.ProductDao;
import fpoly.group6_pro1122.kidsshop.Dao.TagDao;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Intefaces.ItemClickListener;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.Tag;
import fpoly.group6_pro1122.kidsshop.R;

public class Product_Admin_Fragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    Toolbar toolbar;
    Spinner spinner_category, spinner_tag;
    Category_Spinner categorySpinner;
    Uri imageUri;
    TagDao tagDao;
    ArrayList<Tag> list_tag = new ArrayList<>();
    Tag_Spinner tagSpinner;
    CategoryDao categoryDao;
    ArrayList<Category> list_category = new ArrayList();
    EditText ed_name, ed_price, ed_quantity, ed_describe;
    ImageView imgUpLoad;
    ProductDao productDao;
    ArrayList<Product> list_product = new ArrayList<>();
    ArrayList<Product> temp_list = new ArrayList<>();
    int category_id, selectedPosition, selectedPosition_tag, tag_id;
    Product_Admin_Adapter product_admin_adapter;
    EditText ed_Search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_admin, container, false);
        toolbar = view.findViewById(R.id.toolbar_product_admin);
        CreateToolbar();
        ed_Search = view.findViewById(R.id.ed_search_Product);
        recyclerView = view.findViewById(R.id.recyclerView_Product_admin);
        productDao = new ProductDao(getContext());
        list_product = productDao.SelectAll();
        temp_list = productDao.SelectAll();
        product_admin_adapter = new Product_Admin_Adapter(getContext(), list_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(product_admin_adapter);
        SearchProduct();
        view.findViewById(R.id.fab_product_admin).setOnClickListener(v -> {
            showDialog(0, null);
        });
        product_admin_adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                Product product = list_product.get(position);
                showDialog(1, product);
            }
        });
        return view;
    }

    private void showDialog(int type, Product product) {
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
        spinner_tag = dialogView.findViewById(R.id.spinner_tag_dialog);

        if (type != 0) {
            ed_name.setText(product.getProduct_name());
            ed_price.setText(product.getProduct_price() + "");
            ed_describe.setText(product.getDescribe());
            ed_quantity.setText(product.getQuantity() + "");
            for (int i = 0; i < list_category.size(); i++) {
                if (product.getCategory_id() == list_category.get(i).getCategory_id()) {
                    selectedPosition = i;
                }
            }
            spinner_category.setSelection(selectedPosition);
            for (int i = 0; i < list_tag.size(); i++) {
                if (product.getTag_id() == list_tag.get(i).getId()) {
                    selectedPosition_tag = i;
                }
            }
            spinner_tag.setSelection(selectedPosition_tag);
            Glide.with(getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.image)
                    .into(imgUpLoad);
        }

        imgUpLoad.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        create_spinner_category();
        create_spinner_tag();
        spinner_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tag_id = list_tag.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            if (type == 0) {
                if (validateForm(name, price, quantity, describe) && imageUri != null) {
                    String imagePath = imageUri.toString();
                    Product productNew = new Product();
                    productNew.setProduct_name(name);
                    productNew.setQuantity(Integer.parseInt(quantity));
                    productNew.setProduct_price(Integer.parseInt(price));
                    productNew.setDescribe(describe);
                    productNew.setImage(imagePath);
                    productNew.setTag_id(tag_id);
                    productNew.setCategory_id(category_id);
                    if (productDao.insertData(productNew)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        updateUI();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (validateForm(name, price, describe, quantity)) {
                    product.setProduct_name(name);
                    product.setProduct_price(Integer.parseInt(price));
                    product.setQuantity(Integer.parseInt(quantity));
                    product.setDescribe(describe);
                    product.setTag_id(tag_id);
                    product.setCategory_id(category_id);
                    if (imageUri != null) {
                        product.setImage(imageUri.toString());
                    } else {
                        product.setImage(product.getImage());
                    }

                    if (productDao.updateData(product)) {
                        Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                        updateUI();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), R.string.update_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        dialogView.findViewById(R.id.bt_cancle_dialog_product).setOnClickListener(dv -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void create_spinner_tag() {
        tagDao = new TagDao(getContext());
        list_tag = tagDao.SelectAll();
        tagSpinner = new Tag_Spinner(getContext(), list_tag);
        spinner_tag.setAdapter(tagSpinner);
    }

    private void create_spinner_category() {
        categoryDao = new CategoryDao(getContext());
        list_category = categoryDao.getAll();
        categorySpinner = new Category_Spinner(getContext(), list_category);
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

    private void updateUI() {
        list_product.clear();
        list_product.addAll(productDao.SelectAll());
        product_admin_adapter.notifyDataSetChanged();
    }

    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Quản lý sản phẩm");
    }
    private void SearchProduct(){
        ed_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                list_product.clear();

                for (Product product : temp_list) {
                    if (product.getProduct_name().contains(query)) {
                        list_product.add(product);
                    }
                }
                product_admin_adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}