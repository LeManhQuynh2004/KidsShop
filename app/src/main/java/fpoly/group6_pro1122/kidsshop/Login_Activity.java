package fpoly.group6_pro1122.kidsshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;

public class Login_Activity extends AppCompatActivity {
    EditText ed_email,ed_pass;
    CheckBox chk_remember_account;
    Spinner spinner_role;
    UserDao userDao;
    String role_value;
    int role_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner_role = findViewById(R.id.spn_role_login);
        ed_email = findViewById(R.id.ed_email_login);
        ed_pass = findViewById(R.id.ed_password_login);
        userDao = new UserDao(this);
        chk_remember_account = findViewById(R.id.remember_account_login);
        ArrayList<String> list = new ArrayList<>();
        list.add("Quản lý");
        list.add("Khách hàng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);
        ReadFile();
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                role_position = position;
                role_value = list.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findViewById(R.id.bt_login).setOnClickListener(view -> {
           String email = ed_email.getText().toString().trim();
           String password = ed_pass.getText().toString().trim();
           if(validateFrom(email,password)){
                if(userDao.checkLogin(email,password,String.valueOf(role_position))){
                    User user = userDao.SelectID(email);
                    if(user.getRole() == 0){
                        role_value = "admin";
                    }else{
                        role_value = "customer";
                    }
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    rememberUser(email,password, chk_remember_account.isChecked(), role_position);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }
           }
        });
    }

    private void ReadFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        ed_email.setText(sharedPreferences.getString("EMAIL", ""));
        ed_pass.setText(sharedPreferences.getString("PASSWORD", ""));
        chk_remember_account.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        spinner_role.setSelection(sharedPreferences.getInt("ROLE", 0));
    }

    private void rememberUser(String email, String password, boolean checked, int rolePosition) {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("EMAIL", email);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REMEMBER", checked);
            editor.putInt("ROLE", role_position);
        }
        editor.commit();
    }

    private boolean validateFrom(String strEmail, String strPassword) {
        boolean isCheck = true;
        if(strEmail.isEmpty() || strPassword.isEmpty()){
            Toast.makeText(this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}