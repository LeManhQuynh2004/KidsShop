package fpoly.group6_pro1122.kidsshop.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fpoly.group6_pro1122.kidsshop.Adapter.Voucher_Admin_Adapter;
import fpoly.group6_pro1122.kidsshop.Dao.VoucherDao;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Model.Voucher;
import fpoly.group6_pro1122.kidsshop.R;

public class Voucher_Admin_Fragment extends Fragment {

    View view;
    Toolbar toolbar;
    ListView listView;
    VoucherDao voucherDao;
    ArrayList<Voucher> list = new ArrayList<>();
    ArrayList<Voucher> temp_list = new ArrayList<>();
    EditText ed_discount, ed_startDate, ed_endDate;
    ImageView img_date;
    Voucher_Admin_Adapter voucherAdminAdapter;
    EditText edSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_voucher, container, false);
        voucherDao = new VoucherDao(getContext());
        toolbar = view.findViewById(R.id.toolbar_admin_voucher);
        CreateToolbar();
        edSearch = view.findViewById(R.id.ed_search_Voucher);
        listView = view.findViewById(R.id.listView_admin_voucher);
        list = voucherDao.SelectAll();
        temp_list = voucherDao.SelectAll();
        voucherAdminAdapter = new Voucher_Admin_Adapter(getContext(), list);
        listView.setAdapter(voucherAdminAdapter);
        SearchVoucher();
        view.findViewById(R.id.fab_add_voucher).setOnClickListener(view1 -> {
            ShowDialogAddOrEditVoucher(0, null);
        });
        return view;
    }

    private void ShowDialogAddOrEditVoucher(int type, Voucher voucher) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_voucher, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        ed_discount = dialogView.findViewById(R.id.ed_discount_dialog_voucher);
        ed_endDate = dialogView.findViewById(R.id.ed_endDate_dialog_voucher);
        img_date = dialogView.findViewById(R.id.img_click_show_date);
        img_date.setOnClickListener(view1 -> {
            showDatePickerDialog(ed_endDate);
        });

        dialogView.findViewById(R.id.bt_add_dialog_voucher).setOnClickListener(view1 -> {
            String code = "Tẩt cả hình thức";
            String discount = ed_discount.getText().toString().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String date = sdf.format(new Date());
            String end = ed_endDate.getText().toString().trim();
            if (ValidateForm(discount, date, end)) {
                if (type == 0) {
                    Voucher voucherNew = new Voucher();
                    voucherNew.setCode(code);
                    voucherNew.setDiscount_amount(Integer.parseInt(discount));
                    voucherNew.setStart_date(date);
                    voucherNew.setExpiration_date(end);
                    voucherNew.setUser_id(1);
                    if (voucherDao.insertData(voucherNew)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        list.add(voucherNew);
                        voucherAdminAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    voucher.setDiscount_amount(Integer.parseInt(discount));
                    voucher.setExpiration_date(end);
                    if (voucherDao.updateData(voucher)) {
                        Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                        updateUI();
                    } else {
                        Toast.makeText(getContext(), R.string.update_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean ValidateForm(String discount, String start, String end) {
        boolean isCheck = true;
        if (discount.isEmpty() || start.isEmpty() || end.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }

    private void showDatePickerDialog(EditText editText) {
        //Calender lấy ngày tháng hiện tại của máy tính
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(selectedDateCalendar.getTime());
                    editText.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }

    private void updateUI() {
        list.clear();
        list.addAll(voucherDao.SelectAll());
        voucherAdminAdapter.notifyDataSetChanged();
    }

    private void CreateToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Quản lý Voucher");
    }

    private void SearchVoucher() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                list.clear();


                if (query.isEmpty()) {
                    list.addAll(temp_list);
                } else {
                    try {
                        for (Voucher voucher : temp_list) {
                            int discount = voucher.getDiscount_amount();
                            if (discount <= Integer.parseInt(query)) {
                                list.add(voucher);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                voucherAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}