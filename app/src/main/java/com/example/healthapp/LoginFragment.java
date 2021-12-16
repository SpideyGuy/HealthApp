package com.example.healthapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFragment extends Fragment {

    View view;
    Button btnLogin;
    TextView tvRegister;

    EditText etEmail, etPassword;

    ActivityCommunicationInterface callback;

    DBHelper dbHelper;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    final String PREFERENCES_NAME = "user_data";

    final String IS_LOGGED_IN = "logged_in";
    final String USER_ID = "user_id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        callback = (ActivityCommunicationInterface) getActivity();

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        editor = sharedPreferences.edit();

        dbHelper = new DBHelper(getActivity());

        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_pwd);

        // get the reference of Button
        btnLogin = (Button) view.findViewById(R.id.btn_login);

        tvRegister = (TextView) view.findViewById(R.id.tv_register);

        // perform setOnClickListener on first Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate data

                String email = etEmail.getText().toString();
                String password = generateHashedPassword(etPassword.getText().toString());

                Cursor cursor = dbHelper.getUser(email, password);

                if( cursor.getCount() > 0){
                    editor.putBoolean(IS_LOGGED_IN, true);

//                    cursor.moveToFirst();
//                    int user_id = cursor.getInt(cursor.getColumnIndex("id",0));
//
//                    editor.putInt(USER_ID, user_id);
                    editor.commit();

                    startActivity(new Intent(getActivity(), DashboardActivity.class));

                }else{
                    Toast.makeText(getActivity(), "Sorry your credentials does not match.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onRegisterBtnClicked();
            }
        });

        return view;
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
