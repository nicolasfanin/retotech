package com.nicolasfanin.retotech.core.platform;

import android.os.Bundle;

import com.nicolasfanin.retotech.AndroidApplication;
import com.nicolasfanin.retotech.core.di.ApplicationComponent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public ApplicationComponent appComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent = ((AndroidApplication) getActivity().getApplicationContext()).appComponent;
    }
}
