package com.example.xfocus.HomePages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new Dashboard_Fragment();
            case 1:
                return  new Kategori_Fragment();
            default:
                return new Dashboard_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
