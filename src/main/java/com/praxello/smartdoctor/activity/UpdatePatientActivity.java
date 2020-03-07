package com.praxello.smartdoctor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.CommonMethods;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.model.allpatient.AddPatientResponse;
import com.praxello.smartdoctor.model.allpatient.AllPatientData;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdatePatientActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_firstname)
    EditText etFirstName;
    @BindView(R.id.et_middlename)
    EditText etMiddleName;
    @BindView(R.id.et_lastname)
    EditText etLastName;
    @BindView(R.id.et_birthdate)
    EditText etBirthDate;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_mobilenumber)
    EditText etMobileNumber;
    @BindView(R.id.et_reference)
    EditText etReference;
    @BindView(R.id.btn_update_patient)
    AppCompatButton btnUpdatePatient;
    String stGenderType;
    private int mYear, mMonth, mDay,position;
    public SmartQuiz smartQuiz;
    private static String TAG="UpdatePatientActivity";
    AllPatientData allPatientData;
    String[] leaveType = {"Female", "Male", "Transgender"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();

        if(getIntent().getParcelableExtra("data")!=null){
            allPatientData=getIntent().getParcelableExtra("data");
            position=getIntent().getIntExtra("position",0);

            //setData to editext..
            setData();
        }
        //basic intialisation...
        initViews();

        //setDatatoSpinner...
        setDataToSpinner();
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar=findViewById(R.id.toolbar_updatepatients);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Add Patients");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        btnUpdatePatient.setOnClickListener(this);
        etBirthDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_birthdate:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etBirthDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.btn_update_patient:
                if(isValidated()){
                    updatePatient();
                }
                break;
        }
    }

    private void updatePatient() {
        Map<String,String> params=new HashMap<>();
        params.put("doctorId", CommonMethods.getPrefrence(UpdatePatientActivity.this, AllKeys.USER_ID));
        params.put("patientId", String.valueOf(allPatientData.getPatientId()));
        params.put("firstName",etFirstName.getText().toString());
        params.put("midlleName",etMiddleName.getText().toString());
        params.put("lastName",etLastName.getText().toString());
        params.put("mobile",etMobileNumber.getText().toString());
        params.put("birthDate",etBirthDate.getText().toString());
        params.put("age",etAge.getText().toString());
        params.put("gender",stGenderType);
        params.put("height",etHeight.getText().toString());
        params.put("weight",etWeight.getText().toString());
        params.put("city",etCity.getText().toString());
        params.put("Address",etAddress.getText().toString());
        params.put("Reference",etReference.getText().toString());

        Log.e(TAG, "submitLeave: "+params );
        final ProgressDialog progress = new ProgressDialog(UpdatePatientActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        AllPatientsActivity.smartQuiz.getApiRequestHelper().updatePatient(params,new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                AddPatientResponse addPatientResponse=(AddPatientResponse) object;

                Log.e(TAG, "onSuccess: "+addPatientResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+addPatientResponse.getMessage());

                progress.dismiss();

                if(addPatientResponse.getResponsecode()==200){
                    Toast.makeText(UpdatePatientActivity.this, addPatientResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if(addPatientResponse.getData()!=null){
                        AllPatientData allPatientData=new AllPatientData(addPatientResponse.getData().getPatientId(),
                                addPatientResponse.getData().getDoctorId(),
                                addPatientResponse.getData().getFirstName(),
                                addPatientResponse.getData().getMidlleName(),
                                addPatientResponse.getData().getLastName(),
                                addPatientResponse.getData().getMobile(),
                                addPatientResponse.getData().getBirthDate(),
                                (addPatientResponse.getData().getAge()),
                                addPatientResponse.getData().getGender(),
                                addPatientResponse.getData().getHeight(),
                                addPatientResponse.getData().getWeight(),
                                addPatientResponse.getData().getCity(),
                                addPatientResponse.getData().getAddress(),
                                addPatientResponse.getData().getReference(),
                                addPatientResponse.getData().getIsActive());
                        AllPatientsActivity.allPatientDataArrayList.set(position,allPatientData);
                        AllPatientsActivity.allPatientAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(UpdatePatientActivity.this, addPatientResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(UpdatePatientActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataToSpinner() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leaveType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGender.setAdapter(arrayAdapter);

        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stGenderType = spinGender.getSelectedItem().toString();
                //Toast.makeText(ApplyLeaveActivity.this, "" + stleaveType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(){
        etFirstName.setText(allPatientData.getFirstName());
        etMiddleName.setText(allPatientData.getMidlleName());
        etLastName.setText(allPatientData.getLastName());
        etMobileNumber.setText(allPatientData.getMobile());
        etBirthDate.setText(allPatientData.getBirthDate());
        etAge.setText(String.valueOf(allPatientData.getAge()));
        etHeight.setText(allPatientData.getHeight());
        etWeight.setText(allPatientData.getWeight());
        etAddress.setText(allPatientData.getAddress());
        etCity.setText(allPatientData.getCity());
        etReference.setText(allPatientData.getReference());

        for(int i=0;i<leaveType.length;i++){
            if(allPatientData.getGender().equals(leaveType[i])) {
                spinGender.setSelection(i);
                break;
            }
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

    private boolean isValidated(){

        if(etFirstName.getText().toString().isEmpty()){
            Toast.makeText(UpdatePatientActivity.this, "First Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etMiddleName.getText().toString().isEmpty()){
            Toast.makeText(UpdatePatientActivity.this, "Middle Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etLastName.getText().toString().isEmpty()){
            Toast.makeText(UpdatePatientActivity.this, "Last Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etAge.getText().toString().isEmpty()){
            Toast.makeText(UpdatePatientActivity.this, "Age required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etHeight.getText().toString().isEmpty()){
            Toast.makeText(UpdatePatientActivity.this, "Height required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etWeight.getText().toString().isEmpty()){
            Toast.makeText(UpdatePatientActivity.this, "Weight required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(spinGender.getSelectedItemPosition()==-1){
            Toast.makeText(UpdatePatientActivity.this, "Gender required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etMobileNumber.getText().toString().isEmpty()){
            //etMobileLayout.setError("Mobile number required!");
            Toast.makeText(UpdatePatientActivity.this, "Mobile number required!", Toast.LENGTH_SHORT).show();

            etMobileNumber.requestFocus();
            return false;
        }

        if(etMobileNumber.getText().toString().length()!=10){
            // etMobileLayout.setError("Invalid mobile number!");
            Toast.makeText(UpdatePatientActivity.this, "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            etMobileNumber.requestFocus();
            return false;
        }

        if(etMobileNumber.getText().toString().startsWith("1") || etMobileNumber.getText().toString().startsWith("2") ||
                etMobileNumber.getText().toString().startsWith("3") || etMobileNumber.getText().toString().startsWith("4")
                || etMobileNumber.getText().toString().startsWith("5")){

            // etMobileLayout.setError("Invalid mobile number!");
            Toast.makeText(UpdatePatientActivity.this, "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            etMobileNumber.requestFocus();
            return false;
        }
        return true;
    }

}
