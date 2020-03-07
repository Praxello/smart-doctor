package com.praxello.smartdoctor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.CommonMethods;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.model.login.LoginResponse;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnlogin)
    AppCompatButton btnLogIn;
    @BindView(R.id.tv_forgotpassword)
    TextView tvForgotPassword;
    SmartQuiz smartQuiz;
    private static final String TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();

        //basic intialisation..
        initViews();

    }

    private void initViews(){
        btnLogIn.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
       /* TextView tvTitle = findViewById(R.id.tv_title);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/greatvibes-regular.otf");
        tvTitle.setTypeface(face);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnlogin:
                if(CommonMethods.isNetworkAvailable(LoginActivity.this)){
                    if(isValidated()) {
                        userAuthentication();
                    }
                }else{
                    Toast.makeText(this, AllKeys.NO_INTERNET_AVAILABLE, Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.tv_forgotpassword:
             /*   Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);*/
                break;
        }
    }

    private void userAuthentication(){
       /* Map<String, String> params = new HashMap<>();
        params.put("usrname", etMobileNumber.getText().toString());
        params.put("uuid", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        params.put("passwrd", etPassword.getText().toString());*/
        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("Logging you...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().login(etMobileNumber.getText().toString(),
                etPassword.getText().toString(),new ApiRequestHelper.OnRequestComplete() {
                    @Override
                    public void onSuccess(Object object) {
                        LoginResponse loginResponse=(LoginResponse) object;

                        Log.e(TAG, "onSuccess: "+loginResponse.getResponsecode());
                        Log.e(TAG, "onSuccess: "+loginResponse.getMessage());
                        Log.e(TAG, "onSuccess: "+loginResponse.getData());

                        progress.dismiss();
                        if(loginResponse.getResponsecode()==200){
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.USER_ID, loginResponse.getData().getUserId());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.FIRST_NAME, loginResponse.getData().getFirstName());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.LAST_NAME, loginResponse.getData().getLastName());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.MOBILE, loginResponse.getData().getMobile());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.EMAIL, loginResponse.getData().getEmail());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.CITY, loginResponse.getData().getCity());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.DATEOFBIRTH, loginResponse.getData().getBirthDate());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.ADDRESS, loginResponse.getData().getAddress());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.PASSWORD, loginResponse.getData().getPassword());
                            CommonMethods.setPreference(LoginActivity.this, AllKeys.LOGINTYPE, String.valueOf(loginResponse.getData().getLoginType()));

                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this, DashBoardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                        }else{
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String apiResponse) {
                        progress.dismiss();
                        Toast.makeText(LoginActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private Boolean isValidated() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (etMobileNumber.getText().toString().isEmpty()) {
            etMobileNumber.setError("Email required!");
            etMobileNumber.setFocusable(true);
            etMobileNumber.requestFocus();
            return false;
        }

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password required!");
            etPassword.setFocusable(true);
            etPassword.requestFocus();
            return false;
        }

        if(!etMobileNumber.getText().toString().matches(emailPattern)){
            etMobileNumber.setError("Invalid email!");
            etMobileNumber.setFocusable(true);
            etMobileNumber.requestFocus();
            return false;
        }
        return true;
    }

}
