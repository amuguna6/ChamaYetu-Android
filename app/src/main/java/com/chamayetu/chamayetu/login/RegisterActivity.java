package com.chamayetu.chamayetu.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.login
 * Created by lusinabrian on 30/09/16.
 * Description: Register Activity class for a new user
 */
public class RegisterActivity extends AppCompatActivity{
    public static final String REGISTERACT_TAG = RegisterActivity.class.getSimpleName();

    /*UI views*/
    @BindView(R.id.signup_button_id) Button signUpButton;
    @BindView(R.id.signup_email_id) EditText signUpEmail;
    @BindView(R.id.signup_password_id) EditText signUpPassword;
    @BindView(R.id.signup_emailtxtInput_id) TextInputLayout signUpEmailTxtInptLayout;
    @BindView(R.id.signup_pass_txtInput_id) TextInputLayout signUpPassTxtInptLayout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(REGISTERACT_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(REGISTERACT_TAG, "onAuthStateChanged:signed_out");
            }
        };
        signUpEmail.addTextChangedListener(new MyTextWatcher(signUpEmail));
        signUpPassword.addTextChangedListener(new MyTextWatcher((signUpPassword)));
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**Todo: Make TextWatcher an interface*/
    private class MyTextWatcher implements TextWatcher {
        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.signup_email_id:
                    validateEmail();
                    break;

                case R.id.signup_password_id:
                    validatePassword();
                    break;
            }
        }
    }

    /**VALIDATE user password*/
    private boolean validatePassword() {
        String password = signUpPassword.getText().toString().trim();
        if(password.isEmpty()){
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password));
            requestFocus(signUpPassword);
            return false;
        }else if(password.length() < 6){
            signUpPassTxtInptLayout.setError(getString(R.string.err_msg_password_short));
            requestFocus(signUpPassword);
        }else{
            signUpPassTxtInptLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**validate user email*/
    private boolean validateEmail() {
        String email = signUpEmail.getText().toString().trim();

        if(email.isEmpty() || isValidEmail(email)){
            signUp_emailtxtInptLayout.setError(getString(R.string.err_msg_email));
            requestFocus(signUp_email);
            return false;
        }else{
            signUp_passwordTxtInptLayout.setErrorEnabled(false);
        }
        return true;
    }

}