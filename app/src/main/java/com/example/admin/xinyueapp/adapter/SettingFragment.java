package com.example.admin.xinyueapp.adapter;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceFragment;

import com.example.admin.xinyueapp.R;


public class SettingFragment extends PreferenceFragment {



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

}
