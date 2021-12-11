package com.example.healthapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    View view;
    Button btnLogin;
    TextView tvRegister;

    ActivityCommunicationInterface callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        callback = (ActivityCommunicationInterface) getActivity();

        // get the reference of Button
        btnLogin = (Button) view.findViewById(R.id.btn_login);

        tvRegister = (TextView) view.findViewById(R.id.tv_register);

        // perform setOnClickListener on first Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate data
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
}
