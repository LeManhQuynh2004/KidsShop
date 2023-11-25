package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class SigUp_Fragment extends Fragment {
    View view;
    EditText edEmail, edPassword, edConfirmPassword;
    CheckBox chkAgreeTerms;
    Button btnGetStarted;
    UserDao userDao;

    private boolean isEmail(String str) {
        return str.matches(
                "[a-zA-Z0-9_.]+@[a-zA-Z]+\\.+[a-z]+"
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sing_up, container, false);
        edEmail = view.findViewById(R.id.ed_email_signup);
        edPassword = view.findViewById(R.id.ed_password_signup);
        edConfirmPassword = view.findViewById(R.id.confirm_pass);
        chkAgreeTerms = view.findViewById(R.id.chk_singUp);
        btnGetStarted = view.findViewById(R.id.bt_getStart);
        userDao = new UserDao(getContext());
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        return view;
    }

    private void signUp() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();
        boolean agreeTerms = chkAgreeTerms.isChecked();

        if (validateSignUpForm(email, password, confirmPassword, agreeTerms)) {
            User newUser = new User();
            newUser.setRole(1);
            newUser.setEmail(email);
            newUser.setPassword(password);

            if (userDao.insertData(newUser)) {
                Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
            } else {
                Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateSignUpForm(String email, String password, String confirmPassword, boolean agreeTerms) {
        boolean isCheck = true;
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống dữ liệu", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if(!isEmail(email)){
            Toast.makeText(getContext(), "Nhập sai định dạng email", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Mật khẩu và Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (!agreeTerms) {
            Toast.makeText(getContext(), "Vui lòng đồng ý với các điều khoản và điều kiện", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}
