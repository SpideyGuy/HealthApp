package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class LoginRegisterActivity extends AppCompatActivity implements ActivityCommunicationInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        loadFragment(new LoginFragment());
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.login_register, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onLoginBtnClicked() {
        loadFragment(new LoginFragment());
    }

    @Override
    public void onRegisterBtnClicked() {
        loadFragment(new RegisterFragment());
    }
}