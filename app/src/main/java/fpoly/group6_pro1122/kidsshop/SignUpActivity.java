package fpoly.group6_pro1122.kidsshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;

public class SignUpActivity extends AppCompatActivity {

    EditText edEmail, edPassword, edConfirmPassword;
    CheckBox chkAgreeTerms;
    Button btnGetStarted;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        edEmail = findViewById(R.id.ed_email_signup);
        edPassword = findViewById(R.id.ed_password_signup);
        edConfirmPassword = findViewById(R.id.confirm_pass);
        chkAgreeTerms = findViewById(R.id.chk_singUp);
        btnGetStarted = findViewById(R.id.bt_getStart);
        userDao = new UserDao(this);

//        findViewById(R.id.sendLogin).setOnClickListener(view -> {
//            Intent intent = new Intent(SignUpActivity.this, Login_Activity.class);
//            startActivity(intent);
//            finish();
//        });
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
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

//            if (userDao.insertData(newUser)) {
//                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SignUpActivity.this, Login_Activity.class);
//                startActivity(intent);
//                finish();
//            } else {
//                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    private boolean validateSignUpForm(String email, String password, String confirmPassword, boolean agreeTerms) {
        boolean isCheck = true;
        if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(this, "Vui lòng không bỏ trống dữ liệu", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu và Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        if (!agreeTerms) {
            Toast.makeText(this, "Vui lòng đồng ý với các điều khoản và điều kiện", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}
