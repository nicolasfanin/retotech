package com.nicolasfanin.retotech.presentation.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class SplashFragment extends BaseFragment {


    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        //TODO: Verify if user is logged in!
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                initNavigation();
            }
        }, SPLASH_DISPLAY_LENGTH);
        return rootView;
    }

    private void initNavigation() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
    }
}
