package com.nicolasfanin.retotech.presentation.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;
import com.nicolasfanin.retotech.presentation.viewmodel.SplashViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class SplashFragment extends BaseFragment {

    @Inject
    SplashViewModel viewModel;

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        appComponent.inject(this);

        viewModel.user.observe(getViewLifecycleOwner(), value -> onUserResponse(value));
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.checkIfUserIsSignedIn();
    }

    private void onUserResponse(String userId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userId.isEmpty()) {
                    initLoginNavigation();
                } else {
                    initHomeNavigation();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void initLoginNavigation() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_authenticationFragment2);
    }

    private void initHomeNavigation() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment);
    }
}
