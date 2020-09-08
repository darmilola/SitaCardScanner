package com.sita.android.sitaproject.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sita.android.sitaproject.Fragments.AdminLogin;
import com.sita.android.sitaproject.Fragments.Citizenlogin;

public class LoginAdapter extends FragmentPagerAdapter {
    int mNumofTabs;

    public LoginAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 1:
                return Citizenlogin.newInstance();

            case 0:
                return  AdminLogin.newInstance();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
