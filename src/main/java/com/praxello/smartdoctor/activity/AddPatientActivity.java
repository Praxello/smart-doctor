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
import android.widget.Toast;

import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.CommonMethods;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.model.CommonResponse;
import com.praxello.smartdoctor.model.allpatient.AddPatientResponse;
import com.praxello.smartdoctor.model.allpatient.AllPatientData;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPatientActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_firstname)
    EditText etFirstName;
    @BindView(R.id.et_middlename)
    EditText etMiddleName;
    @BindView(R.id.et_lastname)
    EditText etLastName;
    @BindView(R.id.et_mobilenumber)
    EditText etMobileNumber;
    @BindView(R.id.et_birthdate)
    EditText etBirthDate;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.spin_gender)
    AppCompatSpinner spinGender;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_reference)
    EditText etReference;
    @BindView(R.id.btn_add_patient)
    AppCompatButton btnAddPatient;
    String stGenderType;
    private int mYear, mMonth, mDay;
    public  SmartQuiz smartQuiz;
    private static String TAG="AddPatientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();

        //basic intialisation...
        initViews();

        //setDatatoSpinner...
        setDataToSpinner();
    }

    private void initViews(){
        //Toolbar intialisation...
        Toolbar toolbar=findViewById(R.id.toolbar_addpatients);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Add Patients");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        btnAddPatient.setOnClickListener(this);
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
                                String age=getAge(year,(monthOfYear + 1),dayOfMonth);
                                etAge.setText(age);

                            }
                        }, mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.btn_add_patient:
                if(isValidated()){
                    addPatient();
                }
                break;
        }
    }

    private void addPatient() {
        Map<String,String> params=new HashMap<>();
        params.put("doctorId", CommonMethods.getPrefrence(AddPatientActivity.this, AllKeys.USER_ID));
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
        final ProgressDialog progress = new ProgressDialog(AddPatientActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        AllPatientsActivity.smartQuiz.getApiRequestHelper().addPatient(params,new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                AddPatientResponse addPatientResponse=(AddPatientResponse) object;

                Log.e(TAG, "onSuccess: "+addPatientResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+addPatientResponse.getMessage());

                progress.dismiss();

                if(addPatientResponse.getResponsecode()==200){
                    Toast.makeText(AddPatientActivity.this, addPatientResponse.getMessage(), Toast.LENGTH_SHORT).show();

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
                    AllPatientsActivity.allPatientDataArrayList.add(allPatientData);
                    AllPatientsActivity.allPatientAdapter.notifyDataSetChanged();

                    //Clear all fields of edittextts...
                    clearAllFields();
                }else{
                    Toast.makeText(AddPatientActivity.this, addPatientResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(AddPatientActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAllFields(){
        etFirstName.setText("");
        etMiddleName.setText("");
        etLastName.setText("");
        etBirthDate.setText("");
        etAge.setText("");
        etHeight.setText("");
        etWeight.setText("");
        etAddress.setText("");
        etCity.setText("");
        etReference.setText("");
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    private void setDataToSpinner() {
        String[] leaveType = {"Female", "Male", "Transgender"};
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
            Toast.makeText(AddPatientActivity.this, "First Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etMiddleName.getText().toString().isEmpty()){
            Toast.makeText(AddPatientActivity.this, "Middle Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etLastName.getText().toString().isEmpty()){
            Toast.makeText(AddPatientActivity.this, "Last Name required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etAge.getText().toString().isEmpty()){
            Toast.makeText(AddPatientActivity.this, "Age required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etHeight.getText().toString().isEmpty()){
            Toast.makeText(AddPatientActivity.this, "Height required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etWeight.getText().toString().isEmpty()){
            Toast.makeText(AddPatientActivity.this, "Weight required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(spinGender.getSelectedItemPosition()==-1){
            Toast.makeText(AddPatientActivity.this, "Gender required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etMobileNumber.getText().toString().isEmpty()){
            //etMobileLayout.setError("Mobile number required!");
            Toast.makeText(AddPatientActivity.this, "Mobile number required!", Toast.LENGTH_SHORT).show();

            etMobileNumber.requestFocus();
            return false;
        }

        if(etMobileNumber.getText().toString().length()!=10){
            // etMobileLayout.setError("Invalid mobile number!");
            Toast.makeText(AddPatientActivity.this, "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            etMobileNumber.requestFocus();
            return false;
        }

        if(etMobileNumber.getText().toString().startsWith("1") || etMobileNumber.getText().toString().startsWith("2") ||
                etMobileNumber.getText().toString().startsWith("3") || etMobileNumber.getText().toString().startsWith("4")
                || etMobileNumber.getText().toString().startsWith("5")){

            // etMobileLayout.setError("Invalid mobile number!");
            Toast.makeText(AddPatientActivity.this, "Invalid mobile number!", Toast.LENGTH_SHORT).show();
            etMobileNumber.requestFocus();
            return false;
        }


        return true;
    }

}
