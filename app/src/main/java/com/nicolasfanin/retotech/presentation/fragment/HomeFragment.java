package com.nicolasfanin.retotech.presentation.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;
import com.nicolasfanin.retotech.databinding.FragmentHomeBinding;
import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.presentation.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import static com.nicolasfanin.retotech.core.utils.Constants.EMPTY_VALUE;

public class HomeFragment extends BaseFragment {

    @Inject
    HomeViewModel viewModel;

    private FragmentHomeBinding binding;

    private final Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        appComponent.inject(this);

        initViews();

        viewModel.clientCreated.observe(getViewLifecycleOwner(), s -> onCreatedUserResponse(s));
        return binding.getRoot();
    }

    public void initViews() {
        binding.nameEditText.addTextChangedListener(createTextWatcher());
        binding.surnameEditText.addTextChangedListener(createTextWatcher());
        binding.ageEditText.addTextChangedListener(createTextWatcher());
        binding.birthDateEditText.addTextChangedListener(createTextWatcher());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBirthDateEditText();
            }
        };

        binding.birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.createClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle(getString(R.string.create_client_title))
                        .setMessage(getString(R.string.create_client_message))
                        .setPositiveButton(R.string.create_client_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.createClient(new ClientModel(binding.nameEditText.getText().toString(),
                                        binding.surnameEditText.getText().toString(),
                                        Integer.parseInt(binding.ageEditText.getText().toString()),
                                        binding.birthDateEditText.getText().toString()));
                            }
                        })
                        .setNegativeButton(R.string.create_client_negative_button, null)
                        .show();
            }
        });

        binding.homeSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.close_session_title))
                        .setMessage(getString(R.string.close_session_message))
                        .setPositiveButton(R.string.close_session_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.signOut();
                                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_splashFragment);
                            }
                        })
                        .setNegativeButton(R.string.close_session_negative_button, null)
                        .show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequiredFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void checkRequiredFields() {
        if (binding.nameEditText.getText().toString().isEmpty()
                || binding.surnameEditText.getText().toString().isEmpty()
                || binding.ageEditText.getText().toString().isEmpty()
                || binding.birthDateEditText.getText().toString().isEmpty()) {
            binding.createClientButton.setEnabled(false);
            binding.createClientButton.setBackgroundColor(getResources().getColor(R.color.disabled_button));
        } else {
            binding.createClientButton.setEnabled(true);
            binding.createClientButton.setBackgroundColor(getResources().getColor(R.color.enabled_button));
        }
    }


    private void updateBirthDateEditText() {
        String formatDate = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());

        binding.birthDateEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }


    private void onCreatedUserResponse(String s) {
        binding.nameEditText.setText(EMPTY_VALUE);
        binding.surnameEditText.setText(EMPTY_VALUE);
        binding.ageEditText.setText(EMPTY_VALUE);
        binding.birthDateEditText.setText(EMPTY_VALUE);

        Toast.makeText(getContext(), getString(R.string.created_client_success, s), Toast.LENGTH_LONG).show();
    }


}
