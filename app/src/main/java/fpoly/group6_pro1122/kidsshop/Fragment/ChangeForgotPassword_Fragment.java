package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.R;

public class ChangeForgotPassword_Fragment extends Fragment {

    TextInputEditText edt_pass, edt_confirmPass;
    UserDao dao;
    Button btn_update;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_forgot_password_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_pass = view.findViewById(R.id.edtNewPassword2);
        edt_confirmPass = view.findViewById(R.id.edtConfirmPassword2);
        btn_update = view.findViewById(R.id.btnChangePassword2);
        toolbar = view.findViewById(R.id.toolbar);
        CreateToolbar();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String email = bundle.getString("email");

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pass = edt_pass.getText().toString().trim();
                    String passConfirm = edt_confirmPass.getText().toString().trim();
                    if (pass.equals("") || passConfirm.equals("")){
                        Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!pass.equals(passConfirm)) {
                        Toast.makeText(getContext(), "Mật khẩu không trùng khớp.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dao = new UserDao(getContext());
                    if (dao.updatePass(email, pass)) {
                        Toast.makeText(getContext(), "Mật khẩu đã được cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Cập nhật mật khẩu thất bại. Hãy thử lại.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Thay đổi mật khẩu");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForgotPassword_Fragment()).commit();
        });
    }
}