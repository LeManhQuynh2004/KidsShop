package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class ChangePass_Fragment extends Fragment {

    TextInputEditText edt_passOld,edt_passNew,edt_rePassNew;
    UserDao dao;
    Button btn_check;
    TextInputLayout layout, layout_passNew,layout_conFirm;
    TextView tv_content,tv_title;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dao = new UserDao(getContext());

        edt_passOld = view.findViewById(R.id.edtOldPassword);
        edt_passNew = view.findViewById(R.id.edtNewPassword);
        edt_rePassNew = view.findViewById(R.id.edtConfirmPassword);
        btn_check = view.findViewById(R.id.btnChangePassword);
        layout = view.findViewById(R.id.layout_oldPass);
        layout_passNew = view.findViewById(R.id.layout_passNew);
        layout_conFirm = view.findViewById(R.id.layout_confirm);
        tv_content = view.findViewById(R.id.tv_textContent);
        tv_title = view.findViewById(R.id.tv_title);
        toolbar = view.findViewById(R.id.toolbar);
        CreateToolbar();

        //View.Gone: ẩn và mất không gian
        //View.INS...: ẩn nhưng vẫn chiếm không gian

        edt_passNew.setVisibility(View.GONE);
        edt_rePassNew.setVisibility(View.GONE);
        layout_conFirm.setVisibility(View.GONE);
        layout_passNew.setVisibility(View.GONE);
        btn_check.setOnClickListener(v->{
            String pass = edt_passOld.getText().toString();
            if (dao.checkPassword(pass)){
                edt_passNew.setVisibility(View.VISIBLE);
                edt_rePassNew.setVisibility(View.VISIBLE);
                layout_conFirm.setVisibility(View.VISIBLE);
                layout_passNew.setVisibility(View.VISIBLE);
                edt_passOld.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                btn_check.setText("Đặt lại mật khẩu");
                tv_title.setText("Tạo mật khẩu mới");
                tv_content.setText("Vui lòng nhập mật khẩu mới và xác nhận mật khẩu.");
                btn_check.setOnClickListener(view1->{
                    String passNew = edt_passNew.getText().toString().trim();
                    String rePass = edt_rePassNew.getText().toString().trim();
                    updatePass(pass,passNew,rePass);
                });
            }else {
                Toast.makeText(getContext(),"Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                edt_passOld.requestFocus();
            }
        });
    }
    private void updatePass(String pasOld,String passNew,String rePass){
        if (pasOld.equalsIgnoreCase(passNew)){
            Toast.makeText(getContext(), "Mật khẩu mới không được trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passNew.equalsIgnoreCase(rePass)){
            Toast.makeText(getContext(), "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
            return;
        }
        dao = new UserDao(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        if (dao.updatePass(email,passNew)){
            Log.e("MK",passNew);
            Toast.makeText(getContext(), "Mật khẩu đã được cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Cập nhật mật khẩu thất bại. Hãy thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thay Đổi Mật Khẩu");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalInf_Fragment()).commit();
        });
    }
}