package com.praxello.smartdoctor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.CommonMethods;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.adapter.AllPatientAdapter;
import com.praxello.smartdoctor.model.allpatient.AllPatientData;
import com.praxello.smartdoctor.model.allpatient.PatientResponse;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllPatientsActivity extends AppCompatActivity implements View.OnClickListener {

    public static SmartQuiz smartQuiz;
    @BindView(R.id.rv_all_patients)
    RecyclerView rvAllPatients;
    @BindView(R.id.fab_add_patient)
    FloatingActionButton fabAddPatient;
    private static String TAG="AllPatientsActivity";
    public static ArrayList<AllPatientData> allPatientDataArrayList;
    public static AllPatientAdapter allPatientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_patients);
        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();

        //basic intialisation...
        initViews();

        //load all patient data...
        loadData();

    }

    private void initViews() {

        Toolbar toolbar=findViewById(R.id.toolbar_allpatients);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("All Patients");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        fabAddPatient.setOnClickListener(this);
        rvAllPatients.setLayoutManager(new LinearLayoutManager(this));

    }


    private void loadData(){
        final ProgressDialog progress = new ProgressDialog(AllPatientsActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getallpatient(CommonMethods.getPrefrence(AllPatientsActivity.this,AllKeys.USER_ID)
                ,new ApiRequestHelper.OnRequestComplete() {
                    @Override
                    public void onSuccess(Object object) {

                        PatientResponse patientResponse=(PatientResponse) object;

                        Log.e(TAG, "onSuccess: "+patientResponse.getResponsecode());
                        Log.e(TAG, "onSuccess: "+patientResponse.getMessage());
                        Log.e(TAG, "onSuccess: "+patientResponse.getData());

                        progress.dismiss();
                        if(patientResponse.getResponsecode()==200){

                            Toast.makeText(AllPatientsActivity.this, patientResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            if(patientResponse.getData()!=null) {
                                allPatientDataArrayList=patientResponse.getData();
                                allPatientAdapter = new AllPatientAdapter(AllPatientsActivity.this,allPatientDataArrayList);
                                rvAllPatients.setAdapter(allPatientAdapter);
                            }
                        }else{
                            Toast.makeText(AllPatientsActivity.this, patientResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String apiResponse) {
                        progress.dismiss();
                        Toast.makeText(AllPatientsActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_patient:
                Intent intent=new Intent(AllPatientsActivity.this,AddPatientActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }
}
