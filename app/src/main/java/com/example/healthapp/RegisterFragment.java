package com.example.healthapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterFragment extends Fragment {

    View view;
    Button btnRegister;
    TextView tvLogin;

    EditText etFullname, etDob, etAddress, etContact, etEmail, etPassword, etConfirmPassword;

    RadioButton rbMale, rbFemale;

    ActivityCommunicationInterface callback;

    final Calendar myCalendar = Calendar.getInstance();

    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        callback = (ActivityCommunicationInterface) getActivity();

        dbHelper = new DBHelper(getActivity());

        // get the reference of Button
        btnRegister = (Button) view.findViewById(R.id.btn_register);

        tvLogin = (TextView) view.findViewById(R.id.tv_login);

        etFullname = (EditText) view.findViewById(R.id.et_fullname);
        etDob = (EditText) view.findViewById(R.id.et_dob);
        etAddress = (EditText) view.findViewById(R.id.et_address);
        etContact = (EditText) view.findViewById(R.id.et_contact);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_pwd);
        etConfirmPassword = (EditText) view.findViewById(R.id.et_confirm_pwd);

        rbMale = (RadioButton) view.findViewById(R.id.rb_male);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    etConfirmPassword.setVisibility(View.VISIBLE);
                }
            }
        });

        // perform setOnClickListener on first Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store data
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    String hashedPassword = generateHashedPassword(etPassword.getText().toString());
                    Log.d("password", hashedPassword);
                    if(dbHelper.insertUser(
                            etFullname.getText().toString(),
                            etDob.getText().toString(),
                            rbMale.isSelected() ? rbMale.getText().toString() : rbFemale.getText().toString(),
                            etContact.getText().toString(),
                            etEmail.getText().toString(),
                            hashedPassword
                    )){
                        Toast.makeText(getActivity(), "Thank you for registering as user", Toast.LENGTH_SHORT).show();
                        callback.onLoginBtnClicked();
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onLoginBtnClicked();
            }
        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDob.setText(sdf.format(myCalendar.getTime()));
    }

    public String generateHashedPassword(String password) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
