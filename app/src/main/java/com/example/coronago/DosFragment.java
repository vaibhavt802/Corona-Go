package com.example.coronago;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class DosFragment extends Fragment {

    ViewPager viewPager;
    WormDotsIndicator dot1;
    ViewAdapter viewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dos,container,false);
    }

    @Override
    public void onStart() {
        init();
        super.onStart();
    }

    private void init() {
        viewPager = getView().findViewById(R.id.view_pager);
        dot1 = getView().findViewById(R.id.dot1);


        viewAdapter = new ViewAdapter(getActivity());
        viewPager.setAdapter(viewAdapter);
        dot1.setViewPager(viewPager);
    }
}
