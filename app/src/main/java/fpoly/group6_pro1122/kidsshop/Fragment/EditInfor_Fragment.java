package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class EditInfor_Fragment extends Fragment {
    UserDao dao;
    EditText edt_ten, edt_gmail, edt_phone, edt_adress;
    Button btn_chinhsua;
    TextView tv_ten, tv_email;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_infor_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new UserDao(getContext());

        edt_ten = view.findViewById(R.id.edt_edit_fullname);
        edt_adress = view.findViewById(R.id.edt_edit_address);
        edt_gmail = view.findViewById(R.id.edt_edit_email);
        edt_phone = view.findViewById(R.id.edt_edit_phone);
        btn_chinhsua = view.findViewById(R.id.btn_edit_chinhSua);
        tv_ten = view.findViewById(R.id.tv_edit_hoTen);
        tv_email = view.findViewById(R.id.tv_edit_email);
        toolbar = view.findViewById(R.id.toolbar);
        CreateToolbar();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalInf_Fragment()).commit();
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        if (email != null) {
            User user = dao.SelectID(email);
//            ((MainActivity)getContext()).disableBottomNavigationView();
            tv_ten.setText(user.getFullname());
            tv_email.setText(user.getEmail());
            if (user.getFullname() == null) {
                edt_ten.setHint("Thiết lập ngay");
                edt_ten.setHintTextColor(Color.parseColor("#FF0000"));
                edt_ten.setGravity(Gravity.END);
                edt_ten.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        // Mở khóa trường nhập liệu khi nhận được focus
                        edt_ten.setEnabled(true);
                        edt_ten.setHint(null);
                        edt_ten.setGravity(Gravity.START);
                        edt_ten.setHintTextColor(Color.TRANSPARENT);
                    }
                });

            } else {
                edt_ten.setText(user.getFullname());
            }
            edt_gmail.setText(user.getEmail());
            edt_gmail.setKeyListener(null);
            if (user.getAddress() == null) {
                edt_adress.setHint("Thiết lập ngay");
                edt_adress.setHintTextColor(Color.parseColor("#FF0000"));
                edt_adress.setGravity(Gravity.END);
                edt_adress.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        // Mở khóa trường nhập liệu khi nhận được focus
                        edt_adress.setEnabled(true);
                        edt_adress.setHint(null);
                        edt_adress.setGravity(Gravity.START);
                        edt_adress.setHintTextColor(Color.TRANSPARENT);
                    }
                });
            } else {
                edt_adress.setText(user.getAddress());
            }
            if (user.getPhone() == null) {
                edt_phone.setHint("Thiết lập ngay");
                edt_phone.setHintTextColor(Color.parseColor("#FF0000"));
                edt_phone.setGravity(Gravity.END);
                edt_phone.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        // Mở khóa trường nhập liệu khi nhận được focus
                        edt_phone.setEnabled(true);
                        edt_phone.setHint(null);
                        edt_phone.setGravity(Gravity.START);
                        edt_phone.setHintTextColor(Color.TRANSPARENT);
                    }
                });
            } else {
                edt_phone.setText(user.getPhone());
            }
            btn_chinhsua.setOnClickListener(v -> {
                if (!validate(edt_ten, "Vui lòng không để trống tên")) {
                    return;
                }

                if (!validate(edt_adress, "Vui lòng không để trống địa chỉ")) {
                    return;
                }
                if (!validate(edt_phone, "Vui lòng không để trống số điện thoại")) {
                    return;
                }
                String nameNew = edt_ten.getText().toString().trim();
                String phoneNew = edt_phone.getText().toString().trim();
                String addressNew = edt_adress.getText().toString().trim();
                User userNew = new User(user.getId(), user.getPassword(), nameNew, user.getEmail(), user.getImage(), phoneNew, addressNew, user.getRole());
                if (dao.updateData(userNew)) {
                    Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    Log.e("User new", userNew.getFullname());
                } else {
                    Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }


//
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Sửa thông tin cá nhân");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalInf_Fragment()).commit();
        });
    }

    private boolean validate(EditText editText, String errorMessage) {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            editText.requestFocus();
            return false;
        }
        return true;
    }

}