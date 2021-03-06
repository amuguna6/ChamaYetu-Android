package com.chamayetu.chamayetu.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.auth.register.registerchama
 * Created by lusinabrian on 15/10/16.
 * Description: Activity to register a new chama
 */

public class RegisterChamaActivity extends AppCompatActivity implements RegisterView.RegisterChama, View.OnClickListener{

    /*UI references*/
    @BindView(R.id.signup_chamaname_id) EditText chamaNameView;
    @BindView(R.id.signup_chamaMembers_id) EditText chamaMemberNumbersView;
    @BindView(R.id.signup_chama_bankAccount_id) EditText chamaAccountNoView;
    @BindView(R.id.signup_chama_bankName_id) EditText chamaBankNameView;
    @BindView(R.id.signup_chama_button_id) Button chamaSignUpBtnView;

    private RegisterPresenter registerPresenter;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_chama_activity);
        ButterKnife.bind(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        registerPresenter = new RegisterPresenterImpl(this, RegisterChamaActivity.this, mAuth, mDatabaseRef);

        chamaSignUpBtnView.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        registerPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_chama_button_id:
                String chamaName = chamaNameView.getText().toString().trim();
                String chamaMembers = chamaMemberNumbersView.getText().toString();
                String chamaBankName = chamaBankNameView.getText().toString().trim();
                String chamaAccountNo = chamaAccountNoView.getText().toString().trim();

                registerPresenter.validateChamaCredentials(chamaName,Integer.parseInt(chamaMembers), chamaBankName,Integer.parseInt(chamaAccountNo));
                break;
        }

    }

    @Override
    public void setChamaNameError() {
        chamaNameView.setError(getString(R.string.chamaname_error));
    }

    @Override
    public void setChamaMemberNumbers() {
     chamaMemberNumbersView.setError(getString(R.string.chamamembers_error));
    }

    @Override
    public void setBankNameError() {
        chamaBankNameView.setError(getString(R.string.chamabank_error));
    }

    @Override
    public void setBankAccountError() {
       chamaAccountNoView.setError(getString(R.string.chamaaccount_error));
    }

    @Override
    public void showProgress() {
        materialDialog = new MaterialDialog.Builder(RegisterChamaActivity.this)
                .title(R.string.progress_dialog_title)
                .theme(Theme.DARK)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();
    }

    @Override
    public void hideProgress() {
        if(materialDialog.isShowing()){
            materialDialog.dismiss();
            finish();
        }
    }

    @Override
    public void displayToastError(String message, int messageType) {
        TastyToast.makeText(this, message, TastyToast.LENGTH_SHORT, messageType);
    }

    @Override
    public void navigateToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
