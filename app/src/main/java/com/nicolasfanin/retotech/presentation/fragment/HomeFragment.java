package com.nicolasfanin.retotech.presentation.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;
import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.presentation.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment extends BaseFragment {

    @Inject
    HomeViewModel viewModel;

    private View rootView;

    private final Calendar calendar = Calendar.getInstance();
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText ageEditText;
    private EditText birthDateEditText;
    private Button createClientButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        appComponent.inject(this);

        initViews();

        viewModel.clientCreated.observe(getViewLifecycleOwner(), s -> onCreatedUserResponse(s));
        return rootView;
    }

    public void initViews() {
        nameEditText = rootView.findViewById(R.id.name_edit_text);
        surnameEditText = rootView.findViewById(R.id.surname_edit_text);
        ageEditText = rootView.findViewById(R.id.age_edit_text);
        birthDateEditText = rootView.findViewById(R.id.birth_date_edit_text);
        createClientButton = rootView.findViewById(R.id.create_client_button);

        nameEditText.addTextChangedListener(createTextWatcher());
        surnameEditText.addTextChangedListener(createTextWatcher());
        ageEditText.addTextChangedListener(createTextWatcher());
        birthDateEditText.addTextChangedListener(createTextWatcher());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBirthDateEditText();
            }
        };

        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.createClient(new ClientModel(nameEditText.getText().toString(),
                        surnameEditText.getText().toString(),
                        Integer.parseInt(ageEditText.getText().toString()),
                        birthDateEditText.getText().toString()));
            }
        });
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
        if (nameEditText.getText().toString().isEmpty()
                || surnameEditText.getText().toString().isEmpty()
                || ageEditText.getText().toString().isEmpty()
                || birthDateEditText.getText().toString().isEmpty()) {
            createClientButton.setEnabled(false);
            createClientButton.setBackgroundColor(getResources().getColor(R.color.disabled_button));
        } else {
            createClientButton.setEnabled(true);
            createClientButton.setBackgroundColor(getResources().getColor(R.color.enabled_button));
        }
    }


    private void updateBirthDateEditText() {
        String formatDate = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.getDefault());

        birthDateEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }


    private void onCreatedUserResponse(String s) {
        Toast.makeText(getContext(), "Created user in database with: " + s, Toast.LENGTH_LONG).show();
    }
}
