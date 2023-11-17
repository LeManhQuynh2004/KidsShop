package fpoly.group6_pro1122.kidsshop.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.CategoryAdmin_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.CategoryDao;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.R;


public class Category_Admin_Fragment extends Fragment {
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton btn_add;
    ImageView imgUp;
    CategoryDao categoryDao;
    EditText edt_name,edt_describe;
    Uri imageUri;
    Button btn_them, btn_huy;
    ArrayList<Category> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category__admin_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar_category_admin);
        recyclerView = view.findViewById(R.id.recyclerView_Category_admin);
        CategoryAdmin_Adapter adapter = new CategoryAdmin_Adapter(list,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(view1 -> {
            showDiaglog();
        });
    }

    private void showDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_category,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        categoryDao = new CategoryDao(getContext());
        edt_name = view.findViewById(R.id.edt_name_dialog_category);
        edt_describe = view.findViewById(R.id.edt_describe_dialog_category);
        imgUp = view.findViewById(R.id.img_dialog_category);
        imgUp.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
        btn_them = view.findViewById(R.id.btn_save_dialog_category);
        btn_huy = view.findViewById(R.id.btn_cancel_dialog_category);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_name.getText().toString().isEmpty() || edt_describe.getText().toString().isEmpty() && imageUri != null){
                    Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = edt_name.getText().toString().trim();
                String moTa = edt_describe.getText().toString().trim();
                String imgUri = imageUri.toString();
                Category category = new Category(getId(),name,imgUri,moTa);
                boolean check = categoryDao.insertTL(category);
                if (check){
                    list.add(category);
                    Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (imageUri != null) {
                        imgUp.setImageURI(imageUri);
                    }
                }
            }
    );
}