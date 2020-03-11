package com.praxello.smartdoctor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.CommonMethods;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.adapter.AddPrescriptionsAdapter;
import com.praxello.smartdoctor.adapter.OldPrescriptionAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.AllMedicineAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.DiagnosisAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.DosageAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.GetAdviceAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.GetAllComplaintsAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.InstructionAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.TypeAdapter;
import com.praxello.smartdoctor.model.AddMedicines;
import com.praxello.smartdoctor.model.PostData;
import com.praxello.smartdoctor.model.UploadPrescriptionResponse;
import com.praxello.smartdoctor.model.alldosage.DosageData;
import com.praxello.smartdoctor.model.alldosage.DosageResponse;
import com.praxello.smartdoctor.model.allinstruction.InstructionData;
import com.praxello.smartdoctor.model.allinstruction.InstructionResponse;
import com.praxello.smartdoctor.model.allmedicine.MedicineData;
import com.praxello.smartdoctor.model.allmedicine.MedicineResponse;
import com.praxello.smartdoctor.model.allpatient.AllPatientData;
import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsResponse;
import com.praxello.smartdoctor.model.getalldiagnosis.AllDiagnosisResponse;
import com.praxello.smartdoctor.model.getallprescription.GetAllPrescriptionResponse;
import com.praxello.smartdoctor.model.medicinetype.MedicineType;
import com.praxello.smartdoctor.model.medicinetype.MedicineTypeResponse;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxActivity extends AppCompatActivity implements View.OnClickListener {

    public SmartQuiz smartQuiz;
    @SuppressLint("StaticFieldLeak")
    public static AutoCompleteTextView actType;
    public static AutoCompleteTextView actMedicine;
    public static AutoCompleteTextView actMorning;
    public static AutoCompleteTextView actEvening;
    public static AutoCompleteTextView actNight;
    public static AutoCompleteTextView actNotesInstruction;
    @BindView(R.id.act_duration)
    public AutoCompleteTextView actDuration;
    @BindView(R.id.btn_clear)
    public AppCompatButton btnClear;
    @BindView(R.id.btn_add)
    public AppCompatButton btnAdd;
    @BindView(R.id.rv_new_prescription)
    public RecyclerView rvNewPrescription;
    @BindView(R.id.rv_old_prescription)
    public RecyclerView rvOldPrescription;
    @BindView(R.id.btn_add_prescription)
    public AppCompatButton btnAddPrescription;
   // @BindView(R.id.act_complaints)
    public static AutoCompleteTextView etComplaints;
   // @BindView(R.id.act_diagnosis)
    public static AutoCompleteTextView etDiagnosis;
   // @BindView(R.id.et_advice)
    public static AutoCompleteTextView etAdvice;
    //@BindView(R.id.et_nextvisitdate)
    public static EditText etNextVisitDate;

    private static String TAG="RxActivity";
    public static ArrayList<AddMedicines> addMedicinesArrayList =new ArrayList<>();
    AddPrescriptionsAdapter addPrescriptionsAdapter;
    static String type="add";
    private int mYear, mMonth, mDay;
    AllPatientData allPatientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();

        if(getIntent().getParcelableExtra("data")!=null){
            allPatientData=getIntent().getParcelableExtra("data");
        }

        //basic intialisation...
        initViews();

        //set Data to all Auto text view adapters....
        setDataToAdapter();

        //set previous prescription..
        setPreviousPrescription();

        actMedicine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(actMedicine.getText().toString().isEmpty()){
                    actType.setText("");
                    actMorning.setText("");
                    actEvening.setText("");
                    actNight.setText("");
                    actNotesInstruction.setText("");
                    actDuration.setText("");
                }
            }
        });
    }

    private void initViews(){
        //Toolbar intialisation....
        Toolbar toolbar=findViewById(R.id.toolbar_rx);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Rx");
        toolbar.setTitleTextColor(Color.BLACK);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        //AutoCompleteTextview binding..
        actType=findViewById(R.id.act_type);
        actMorning=findViewById(R.id.act_morning);
        actEvening=findViewById(R.id.act_evening);
        actNight=findViewById(R.id.act_night);
        actNotesInstruction=findViewById(R.id.act_notes_inst);
        actMedicine=findViewById(R.id.act_medicine);
        etComplaints=findViewById(R.id.act_complaints);
        etDiagnosis=findViewById(R.id.act_diagnosis);
        etAdvice=findViewById(R.id.act_advice);
        etNextVisitDate=findViewById(R.id.et_nextvisitdate);

        //Button click listeners...
        btnAdd.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnAddPrescription.setOnClickListener(this);
        etNextVisitDate.setOnClickListener(this);

        //Recycler view layout setting....
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvNewPrescription.setLayoutManager(linearLayoutManager);
        rvOldPrescription.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setDataToAdapter(){
        //loadDataMedicineType();
        if(DashBoardActivity.medicineTypeArrayList.size()>0){
            TypeAdapter typeAdapter=new TypeAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.medicineTypeArrayList);
            actType.setAdapter(typeAdapter);
            actType.setThreshold(1);
        }

        //load All Medicine...
        if(DashBoardActivity.medicineDataArrayList.size()>0){
            AllMedicineAdapter allMedicineAdapter=new AllMedicineAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.medicineDataArrayList);
            actMedicine.setAdapter(allMedicineAdapter);
            actMedicine.setThreshold(1);
        }

        //load All Dosage...
        if(DashBoardActivity.dosageDataArrayList.size()>0){
            DosageAdapter dosageAdapter=new DosageAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.dosageDataArrayList);
            actMorning.setAdapter(dosageAdapter);
            actMorning.setThreshold(1);
            actEvening.setAdapter(dosageAdapter);
            actEvening.setThreshold(1);
            actNight.setAdapter(dosageAdapter);
            actNight.setThreshold(1);
        }

        //load all Instruction...
        if(DashBoardActivity.instructionDataArrayList.size()>0){
            InstructionAdapter instructionAdapter=new InstructionAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.instructionDataArrayList);
            actNotesInstruction.setAdapter(instructionAdapter);
            actNotesInstruction.setThreshold(1);
        }


        //load all Complaints
        if(DashBoardActivity.getAllComplaintsDataArrayList.size()>0){
            GetAllComplaintsAdapter getAllComplaintsAdapter=new GetAllComplaintsAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.getAllComplaintsDataArrayList);
            etComplaints.setAdapter(getAllComplaintsAdapter);
            etComplaints.setThreshold(1);
        }

        //load all diagnosis...
        if(DashBoardActivity.allDiagnosisDataArrayList.size()>0){
            DiagnosisAdapter diagnosisAdapter=new DiagnosisAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.allDiagnosisDataArrayList);
            etDiagnosis.setAdapter(diagnosisAdapter);
            etDiagnosis.setThreshold(1);
        }

        //load all advice...
        if(DashBoardActivity.getAdviceDataArrayList.size()>0){
            GetAdviceAdapter getAdviceAdapter=new GetAdviceAdapter(RxActivity.this,R.layout.layout_autocomplete_row,DashBoardActivity.getAdviceDataArrayList);
            etAdvice.setAdapter(getAdviceAdapter);
            etAdvice.setThreshold(1);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                /*if(isValidated()){
                    addPrescriptions();
                }*/
                if(actMedicine.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please add prescription!", Toast.LENGTH_SHORT).show();
                }else{
                    addPrescriptions(type,0);
                }

                break;

            case R.id.btn_clear:
               clearAllFields();
                break;

            case R.id.btn_add_prescription:
               // String data=new Gson().toJson(addMedicinesArrayList);
                uploadPrescription();
                break;

            case R.id.et_nextvisitdate:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        (view, year, monthOfYear, dayOfMonth) ->
                                etNextVisitDate.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + dayOfMonth), mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
        }
    }

    private void addPrescriptions(String type,int position){
        AddMedicines addMedicines =new AddMedicines(actType.getText().toString(),
                actMedicine.getText().toString(),
                actMorning.getText().toString(),
                actEvening.getText().toString(),
                actNight.getText().toString(),
                actDuration.getText().toString(),
                actNotesInstruction.getText().toString());

        if(type.equals("add")){
             addMedicinesArrayList.add(addMedicines);
             addPrescriptionsAdapter=new AddPrescriptionsAdapter(RxActivity.this, addMedicinesArrayList);
             rvNewPrescription.setAdapter(addPrescriptionsAdapter);
        }else if(type.equals("update")){
            addMedicinesArrayList.add(addMedicines);
            addPrescriptionsAdapter.notifyDataSetChanged();
            btnAdd.setText("Add");
            RxActivity.type="add";
        }
        //reset all fields....
        clearAllFields();
    }

    private void uploadPrescription(){
        String data1="";
        JsonArray medicinesDetails = new JsonArray();
        for(AddMedicines da : addMedicinesArrayList)
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("typeId",da.getTypeId());
            jsonObject.addProperty("medicineId",da.getMedicineId());
            jsonObject.addProperty("morning",da.getMorning());
            jsonObject.addProperty("evining",da.getEvining());
            jsonObject.addProperty("night",da.getNight());
            jsonObject.addProperty("duration",da.getDuration());
            jsonObject.addProperty("inst",da.getInst());

            medicinesDetails.add(jsonObject);
        }

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        PostData postdata=new PostData(medicinesDetails,
                allPatientData.getPatientId(),
                CommonMethods.getPrefrence(RxActivity.this,AllKeys.USER_ID),
                "",
                formattedDate,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                etComplaints.getText().toString(),
                etDiagnosis.getText().toString(),
                etAdvice.getText().toString());
        try{
            data1=new Gson().toJson(postdata,PostData.class);
            Log.e(TAG, "uploadPrescription: "+data1 );

        }catch (IllegalStateException | JsonSyntaxException e){
            e.printStackTrace();
        }

        Map<String,String> params=new HashMap<>();
        params.put("postdata",data1);
        final ProgressDialog progress = new ProgressDialog(RxActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().uploadPrescription(params,new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                UploadPrescriptionResponse uploadPrescriptionResponse=(UploadPrescriptionResponse) object;

                Log.e(TAG, "onSuccess: "+uploadPrescriptionResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+uploadPrescriptionResponse.getMessage());

                progress.dismiss();

                if(uploadPrescriptionResponse.getResponsecode()==200){
                    Toast.makeText(RxActivity.this, uploadPrescriptionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    String url = "http://192.168.0.105/Aloha/p.php?pflag=1 && ppatientId="+uploadPrescriptionResponse.getPatientId()+"&&  pdoctorId="+uploadPrescriptionResponse.getDoctorId()+"&& pvisitDate="+uploadPrescriptionResponse.getVdate();

                    //Print pdf...
                    Intent intent = new Intent(RxActivity.this,WebViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

                    //printPrescription(uploadPrescriptionResponse.getPatientId(),uploadPrescriptionResponse.getDoctorId(),uploadPrescriptionResponse.getVdate());
                }else{
                    Toast.makeText(RxActivity.this, uploadPrescriptionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(RxActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePrescription(AddMedicines addMedicines, int position){
        actMedicine.setText(addMedicines.getMedicineId());
        actType.setText(addMedicines.getTypeId());
        actMorning.setText(addMedicines.getMorning());
        actEvening.setText(addMedicines.getEvining());
        actNight.setText(addMedicines.getNight());
        actNotesInstruction.setText(addMedicines.getInst());
        actDuration.setText(addMedicines.getDuration());

        btnAdd.setText("Update");
        RxActivity.type="update";

        addMedicinesArrayList.remove(position);
        //addPrescriptionsAdapter.notifyItemRemoved(position);
        //addPrescriptions("update",position);
    }

    private void clearAllFields(){
        actMedicine.setText("");
        actType.setText("");
        actMorning.setText("");
        actEvening.setText("");
        actNight.setText("");
        actNotesInstruction.setText("");
        actDuration.setText("");
    }

    private void setPreviousPrescription(){
        final ProgressDialog progress = new ProgressDialog(RxActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllPrescription(String.valueOf(allPatientData.getPatientId()),new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                GetAllPrescriptionResponse getAllPrescriptionResponse=(GetAllPrescriptionResponse) object;

                //Log.e(TAG, "onSuccess: "+dosageResponse.getResponsecode());
                // Log.e(TAG, "onSuccess: "+dosageResponse.getMessage());
                progress.dismiss();
                if(getAllPrescriptionResponse.getResponsecode()==200){
                    // Toast.makeText(RxActivity.this, dosageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(getAllPrescriptionResponse.getData()!=null){
                        OldPrescriptionAdapter oldPrescriptionAdapter=new OldPrescriptionAdapter(RxActivity.this,getAllPrescriptionResponse.getData());
                        rvOldPrescription.setAdapter(oldPrescriptionAdapter);
                    }

                }else{
                    Toast.makeText(RxActivity.this, getAllPrescriptionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(RxActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isValidated(){

        if(actType.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Type required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(actMedicine.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Medicines required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(actMorning.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Morning required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(actEvening.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Evening required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(actNight.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Night required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(actDuration.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Duration required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(actNotesInstruction.getText().toString().isEmpty()){
            Toast.makeText(RxActivity.this, "Instruction required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
