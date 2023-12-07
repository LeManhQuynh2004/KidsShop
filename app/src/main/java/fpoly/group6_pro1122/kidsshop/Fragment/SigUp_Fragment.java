package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class SigUp_Fragment extends Fragment {
    View view;
    Toolbar toolbar;
    EditText edEmail, edPassword, edConfirmPassword;
    CheckBox chkAgreeTerms;
    Button btnGetStarted;
    UserDao userDao;
    TextView sendLogin;

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
        sendLogin = view.findViewById(R.id.sendLogin);
        toolbar = view.findViewById(R.id.toolbar_signup);
        CreateToolbar();
        userDao = new UserDao(getContext());
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        sendLogin.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
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
            User user = userDao.checkUser(email);
            if(user != null){
                Toast.makeText(getContext(), "Tài khoản đã tồn tại !!", Toast.LENGTH_SHORT).show();
            }else{
                if (userDao.insertData(newUser)) {
                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
                } else {
                    Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
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
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Đăng ký");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PersonalInf_Fragment()).commit();
//            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.BottomNavigationView);
//            Menu menu = bottomNavigationView.getMenu();
//            MenuItem menuItem = menu.findItem(R.id.menu_home);
//            menuItem.setChecked(true);
        });
    }
}
