package fpoly.group6_pro1122.kidsshop.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Adapter.Payment_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.PaymentDao;
import fpoly.group6_pro1122.kidsshop.Dao.UserDao;
import fpoly.group6_pro1122.kidsshop.MainActivity;
import fpoly.group6_pro1122.kidsshop.Model.Payment;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.User;
import fpoly.group6_pro1122.kidsshop.R;

public class Type_Payment_Fragment extends Fragment {

    View view;
    Toolbar toolbar;
    ListView listView;
    PaymentDao paymentDao;
    ArrayList<Payment> list = new ArrayList<>();
    Payment_Adapter paymentAdapter;
    String type_payment,email;
    int id_payment;
    UserDao userDao;
    SharedPreferences sharedPreferences;

    private void MinMap(){
        toolbar = view.findViewById(R.id.toolbar_type_payment);
        listView = view.findViewById(R.id.listView_type_Payment);
        userDao = new UserDao(getContext());
        paymentDao = new PaymentDao(getContext());
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type__payment, container, false);
        MinMap();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Phương thức thanh toán");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Payment_Fragment()).commit();
        });
        CreateListViewPayment();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(email != null){
                    type_payment = list.get(i).getType();
                    id_payment = list.get(i).getId();
                    User user = userDao.SelectID(email);
                    Payment payment = paymentDao.SelectID(String.valueOf(id_payment));
                    payment.setUser_id(user.getId());
                    paymentDao.updateData(payment);
                }
            }
        });
        view.findViewById(R.id.bt_finish_type_payment).setOnClickListener(view1 -> {
            Payment_Fragment paymentFragment = new Payment_Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id_payment",id_payment);
            paymentFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, paymentFragment).commit();
        });
        return view;
    }

    private void CreateListViewPayment() {
        list = paymentDao.SelectAll();
        paymentAdapter = new Payment_Adapter(getContext(),list);
        listView.setAdapter(paymentAdapter);
    }
}