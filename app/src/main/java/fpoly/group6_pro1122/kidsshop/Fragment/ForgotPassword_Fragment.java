package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;
import java.util.Random;

import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class ForgotPassword_Fragment extends Fragment {
    private TextView textViewRandomNumber;
    private TextInputEditText edt_numberInput,edt_emaulInput;
    private int randomNumber;
    Toolbar toolbar;
    UserDao dao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewRandomNumber = view.findViewById(R.id.textViewRandomNumber);
        edt_numberInput = view.findViewById(R.id.editTextUserInput);
        edt_emaulInput = view.findViewById(R.id.editTextUserEmail);
        Button submitButton = view.findViewById(R.id.buttonSubmit);
        toolbar = view.findViewById(R.id.toolbar);
        CreateToolbar();

        generateRandomNumber();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserInput();
            }
        });
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(90000) + 10000;
        textViewRandomNumber.setText(String.valueOf(randomNumber));
    }

    private void checkUserInput() {
        String numberInput = edt_numberInput.getText().toString();
        String emailInput = edt_emaulInput.getText().toString();


        if (numberInput.equals("") || emailInput.equals("")) {
            Toast.makeText(getContext(), "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int userNumber = Integer.parseInt(numberInput);

            if (userNumber != randomNumber) {
                Toast.makeText(getContext(), "Mã captcha không hợp lệ", Toast.LENGTH_SHORT).show();
                generateRandomNumber();
                edt_numberInput.setText("");
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Mã captcha không hợp lệ", Toast.LENGTH_SHORT).show();
            edt_numberInput.setText("");
            generateRandomNumber();
            return;
        }
        dao = new UserDao(getContext());
        User user = dao.checkUser(emailInput);
        if (user != null) {
            Toast.makeText(getContext(), "Tai Khoan Hop le", Toast.LENGTH_SHORT).show();
            ChangeForgotPassword_Fragment changeForgotPasswordFragment = new ChangeForgotPassword_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("email",emailInput);
            changeForgotPasswordFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,changeForgotPasswordFragment).commit();
        } else {
            Toast.makeText(getContext(), "Tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
            generateRandomNumber();
        }
    }
    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Quên Mật Khẩu");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login_Fragment()).commit();
        });
    }
}