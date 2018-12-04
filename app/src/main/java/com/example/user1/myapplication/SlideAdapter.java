package com.example.user1.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class SlideAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    public void initFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
