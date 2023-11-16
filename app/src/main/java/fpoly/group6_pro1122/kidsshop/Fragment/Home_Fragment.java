package fpoly.group6_pro1122.kidsshop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;

import fpoly.group6_pro1122.kidsshop.R;

public class Home_Fragment extends Fragment {
    View view;
    ImageView imageShow;
    int index;

    private Handler handler;

    public static final String TAG = "Home_Fragment";
    int[] imageResIds = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner1,R.drawable.banner2};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        imageShow = view.findViewById(R.id.imageShow);
        imageShow.setImageResource(R.drawable.banner1);
        handler = new Handler(Looper.getMainLooper());
        startRead();
        return view;
    }

    private void startRead() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play();
                startRead();
            }
        }, 3000);
    }
    public void play (){
        index++;
        if(index >= imageResIds.length){
            index = 0;
        }
        Log.d(TAG, "play: "+index);
        imageShow.setImageResource(imageResIds[index]);
    }
}