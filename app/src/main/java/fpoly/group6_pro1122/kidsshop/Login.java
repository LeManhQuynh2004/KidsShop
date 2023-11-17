package fpoly.group6_pro1122.kidsshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;

public class Login extends AppCompatActivity {
    EditText edt_user, edt_pass;
    TextView tv_logUp, tv_forgot, tv_hienthi;
    Button btn_login;
    Spinner spn_login;
    CheckBox chk_login;
    String role;
    UserDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_user = findViewById(R.id.edt_User_Login);
        edt_pass = findViewById(R.id.edt_pass_login);
        btn_login = findViewById(R.id.btn_login);
        tv_logUp = findViewById(R.id.tv_dki_login);
        tv_forgot = findViewById(R.id.tv_forgot_login);
        tv_hienthi = findViewById(R.id.show_login);
        spn_login = findViewById(R.id.spn_login);
        chk_login = findViewById(R.id.remember_login);
    }
}