package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Login_Fragment extends Fragment {

    View view;
    TextView tv_signUp;
    EditText ed_email, ed_pass;
    CheckBox chk_remember_account;
    UserDao userDao;
    String role_value;
    Toolbar toolbar;
//    public boolean isChuoi(String str) {
//        return str.matches("[a-z A-Z 0-9]+");
//    }
    private boolean isEmail(String str){
        return str.matches(
                "[a-zA-Z0-9_.]+@[a-zA-Z]+\\.+[a-z]+"
        );
    }
    private boolean isLength(String str){
        return str.matches(
                "[a-z0-9_]{3,12}$");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        toolbar = view.findViewById(R.id.toolbar_login);
        ed_email = view.findViewById(R.id.ed_email_login);
        ed_pass = view.findViewById(R.id.ed_password_login);
        tv_signUp = view.findViewById(R.id.tv_signUp);
        userDao = new UserDao(getContext());
        CreateToolbar();
        chk_remember_account = view.findViewById(R.id.remember_account_login);
        view.findViewById(R.id.sendSigUp).setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SigUp_Fragment()).commit();
        });
//        ReadFile();
        view.findViewById(R.id.bt_login).setOnClickListener(view -> {
            String email = ed_email.getText().toString().trim();
            String password = ed_pass.getText().toString().trim();
            if (validateFrom(email, password)) {
                User user = userDao.checkUser(email);
                if (user == null) {
                    Toast.makeText(getContext(), "Không tồn tại user", Toast.LENGTH_SHORT).show();
                } else {
                    if (user.getPassword().equals(password)) {
                        if (user.getRole() == 0) {
                            role_value = "admin";
                        } else {
                            role_value = "customer";
                        }
                        Toast.makeText(getContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                        rememberUser(email, password, chk_remember_account.isChecked());
                        getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Nhập sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.tv_signUp).setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SigUp_Fragment()).commit();
        });
        return view;
    }

//    private void ReadFile() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
//        String email = sharedPreferences.getString("EMAIL", "");
//        String pass = sharedPreferences.getString("PASSWORD", "");
//        boolean check = sharedPreferences.getBoolean("REMEMBER", false);
//        if (email.equals("userNormal")) {
//
//        } else {
//            ed_email.setText(email);
//            ed_pass.setText(pass);
//            chk_remember_account.setChecked(check);
//        }
//    }

    private void rememberUser(String email, String password, boolean checked) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if (!checked) {
//            editor.clear();
//        } else {
            editor.putString("EMAIL", email);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REMEMBER", checked);
//        }
        editor.commit();
    }

    private boolean validateFrom(String strEmail, String strPassword) {
        boolean isCheck = true;
        if (strEmail.isEmpty() || strPassword.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if(!isEmail(strEmail)){
            Toast.makeText(getContext(), "Nhập sai định dạng email", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if(!isLength(strPassword)){
            Toast.makeText(getContext(), "Nhập sai định dạng mật khẩu", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Đăng nhập");
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