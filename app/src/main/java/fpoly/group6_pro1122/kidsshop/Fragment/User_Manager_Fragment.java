package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.UserManagement_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class User_Manager_Fragment extends Fragment {
    Toolbar toolbar;
    RecyclerView recyclerView;
    UserManagement_Adapter adapter;
    UserDao userDao;
    ArrayList<User> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user__manager_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar_ql_user);
        recyclerView = view.findViewById(R.id.recyclerView_ql_user);
        userDao = new UserDao(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL","");
        User user = userDao.SelectID(email);
        ArrayList<User> users = new ArrayList<>();
        list = userDao.SelectAll();
        for (User user1:list) {
            if (user1.getRole() != 0){
                users.add(user1);
            }
        }
        adapter = new UserManagement_Adapter(getContext(), users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }
}