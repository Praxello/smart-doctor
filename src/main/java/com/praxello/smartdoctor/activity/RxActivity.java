package com.praxello.smartdoctor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.praxello.smartdoctor.adapter.autotextadapters.AllMedicineAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.DosageAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.InstructionAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.TypeAdapter;
import com.praxello.smartdoctor.model.AddPrescriptionData;
import com.praxello.smartdoctor.model.PostData;
import com.praxello.smartdoctor.model.UploadPrescriptionResponse;
import com.praxello.smartdoctor.model.alldosage.DosageData;
import com.praxello.smartdoctor.model.alldosage.DosageResponse;
import com.praxello.smartdoctor.model.allinstruction.InstructionData;
import com.praxello.smartdoctor.model.allinstruction.InstructionResponse;
import com.praxello.smartdoctor.model.allmedicine.MedicineData;
import com.praxello.smartdoctor.model.allmedicine.MedicineResponse;
import com.praxello.smartdoctor.model.allpatient.AddPatientResponse;
import com.praxello.smartdoctor.model.allpatient.AllPatientData;
import com.praxello.smartdoctor.model.medicinetype.MedicineType;
import com.praxello.smartdoctor.model.medicinetype.MedicineTypeResponse;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxActivity extends AppCompatActivity implements View.OnClickListener {

    public SmartQuiz smartQuiz;
    public static AutoCompleteTextView actType;
    public static AutoCompleteTextView actMedicine;
    public static AutoCompleteTextView actMorning;
    public static AutoCompleteTextView actEvening;
    public static AutoCompleteTextView actNight;
    public static  AutoCompleteTextView actNotesInstruction;
    @BindView(R.id.act_duration)
    public AutoCompleteTextView actDuration;
    @BindView(R.id.btn_clear)
    public AppCompatButton btnClear;
    @BindView(R.id.btn_add)
    public AppCompatButton btnAdd;
    @BindView(R.id.rv_new_prescription)
    public RecyclerView rvNewPrescription;
    @BindView(R.id.btn_add_prescription)
    public AppCompatButton btnAddPrescription;
    @BindView(R.id.et_complaints)
    public EditText etComplaints;
    @BindView(R.id.et_diagnosis)
    public EditText etDiagnosis;
    @BindView(R.id.et_advice)
    public EditText etAdvice;
    @BindView(R.id.et_nextvisitdate)
    public EditText etNextVisitDate;
    ArrayList<MedicineType> medicineTypeArrayList=new ArrayList<>();
    ArrayList<MedicineData> medicineDataArrayList=new ArrayList<>();
    ArrayList<DosageData> dosageDataArrayList=new ArrayList<>();
    ArrayList<InstructionData> instructionDataArrayList=new ArrayList<>();
    private static String TAG="RxActivity";
    ArrayList<AddPrescriptionData> addPrescriptionDataArrayList =new ArrayList<>();
    AddPrescriptionsAdapter addPrescriptionsAdapter;
    static String type="add";
    private int mYear, mMonth, mDay,position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();

        //basic intialisation...
        initViews();

        //load Data Medicine type....
        loadDataMedicineType();

        //load All Medicine...
        loadAllMedicines();

        //load All Dosage...
        loadAllDosage();

        //load all Instruction...
        loadAllInstruction();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Rx");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        //AutoCompleteTextview binding..
        actType=findViewById(R.id.act_type);
        actMorning=findViewById(R.id.act_morning);
        actEvening=findViewById(R.id.act_evening);
        actNight=findViewById(R.id.act_night);
        actNotesInstruction=findViewById(R.id.act_notes_inst);
        actMedicine=findViewById(R.id.act_medicine);

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
                String data=new Gson().toJson(addPrescriptionDataArrayList);
                uploadPrescription();
                break;

            case R.id.et_nextvisitdate:
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

                                etNextVisitDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
        }
    }

    private void addPrescriptions(String type,int position){
        AddPrescriptionData addPrescriptionData =new AddPrescriptionData(actType.getText().toString(),
                actMedicine.getText().toString(),
                actMorning.getText().toString(),
                actEvening.getText().toString(),
                actNight.getText().toString(),
                actDuration.getText().toString(),
                actNotesInstruction.getText().toString());

        if(type.equals("add")){
            addPrescriptionDataArrayList.add(addPrescriptionData);
             addPrescriptionsAdapter=new AddPrescriptionsAdapter(RxActivity.this, addPrescriptionDataArrayList);
             rvNewPrescription.setAdapter(addPrescriptionsAdapter);
        }else if(type.equals("update")){
            addPrescriptionDataArrayList.set(position, addPrescriptionData);
            addPrescriptionsAdapter.notifyItemChanged(position);
            btnAdd.setText("Add");
            RxActivity.type="add";
        }
        //reset all fields....
        clearAllFields();
    }

    private void uploadPrescription(){
        String data1="";
        String data=new Gson().toJson(addPrescriptionDataArrayList);
        JsonArray citiesArray = new JsonArray();
        for(AddPrescriptionData da : addPrescriptionDataArrayList)
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("typeId",da.getTypeId());
            jsonObject.addProperty("medicineId",da.getMedicineId());
            jsonObject.addProperty("morning",da.getMorning());
            jsonObject.addProperty("evining",da.getEvining());
            jsonObject.addProperty("night",da.getNight());
            jsonObject.addProperty("duration",da.getDuration());
            jsonObject.addProperty("inst",da.getInst());

            citiesArray.add(jsonObject);
        }
        // data = data.replaceAll("\\\\", "");
        PostData postdata=new PostData(citiesArray,
                "14",
                CommonMethods.getPrefrence(RxActivity.this,AllKeys.USER_ID),
                etNextVisitDate.getText().toString(),
                "2020-03-06",
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

    public void updatePrescription(AddPrescriptionData addPrescriptionData, int position){
        actMedicine.setText(addPrescriptionData.getMedicineId());
        actType.setText(addPrescriptionData.getTypeId());
        actMorning.setText(addPrescriptionData.getMorning());
        actEvening.setText(addPrescriptionData.getEvining());
        actNight.setText(addPrescriptionData.getNight());
        actNotesInstruction.setText(addPrescriptionData.getInst());
        actDuration.setText(addPrescriptionData.getDuration());

        btnAdd.setText("Update");
        RxActivity.type="update";

        //addPrescriptions("update",position);
    }

    private void loadDataMedicineType(){
        final ProgressDialog progress = new ProgressDialog(RxActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

       smartQuiz.getApiRequestHelper().getAllMedicineType(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                MedicineTypeResponse medicineTypeResponse=(MedicineTypeResponse) object;

                Log.e(TAG, "onSuccess: "+medicineTypeResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+medicineTypeResponse.getMessage());
                progress.dismiss();
                if(medicineTypeResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(medicineTypeResponse.getData()!=null){
                        medicineTypeArrayList = medicineTypeResponse.getData();
                        TypeAdapter typeAdapter=new TypeAdapter(RxActivity.this,R.layout.layout_autocomplete_row,medicineTypeArrayList);
                        actType.setAdapter(typeAdapter);
                        actType.setThreshold(1);
                    }

                }else{
                    Toast.makeText(RxActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(RxActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllMedicines(){
        final ProgressDialog progress = new ProgressDialog(RxActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

       smartQuiz.getApiRequestHelper().getAllMedicine(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                MedicineResponse medicineResponse=(MedicineResponse) object;

                Log.e(TAG, "onSuccess: "+medicineResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+medicineResponse.getMessage());

                progress.dismiss();
                if(medicineResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(medicineResponse.getData()!=null){    actType.setText("");
                   actMorning.setText("");
                   actEvening.setText("");
                   actNight.setText("");
                   actNotesInstruction.setText("");
                   actDuration.setText("");
                        medicineDataArrayList = medicineResponse.getData();
                        AllMedicineAdapter allMedicineAdapter=new AllMedicineAdapter(RxActivity.this,R.layout.layout_autocomplete_row,medicineDataArrayList);
                        actMedicine.setAdapter(allMedicineAdapter);
                        actMedicine.setThreshold(1);
                    }

                }else{
                    Toast.makeText(RxActivity.this, medicineResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(RxActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllDosage(){
        final ProgressDialog progress = new ProgressDialog(RxActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllDosage(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                DosageResponse dosageResponse=(DosageResponse) object;

                Log.e(TAG, "onSuccess: "+dosageResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+dosageResponse.getMessage());
                progress.dismiss();
                if(dosageResponse.getResponsecode()==200){
                   // Toast.makeText(RxActivity.this, dosageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(dosageResponse.getData()!=null){
                        dosageDataArrayList=dosageResponse.getData();
                        DosageAdapter dosageAdapter=new DosageAdapter(RxActivity.this,R.layout.layout_autocomplete_row,dosageDataArrayList);
                        actMorning.setAdapter(dosageAdapter);
                        actMorning.setThreshold(1);
                        actEvening.setAdapter(dosageAdapter);
                        actEvening.setThreshold(1);
                        actNight.setAdapter(dosageAdapter);
                        actNight.setThreshold(1);
                    }

                }else{
                    Toast.makeText(RxActivity.this, dosageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(RxActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllInstruction() {
        final ProgressDialog progress = new ProgressDialog(RxActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllInstruction(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                InstructionResponse instructionResponse=(InstructionResponse) object;

                Log.e(TAG, "onSuccess: "+instructionResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+instructionResponse.getMessage());
                progress.dismiss();
                if(instructionResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, instructionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(instructionResponse.getData()!=null){
                        instructionDataArrayList=instructionResponse.getData();
                        InstructionAdapter instructionAdapter=new InstructionAdapter(RxActivity.this,R.layout.layout_autocomplete_row,instructionDataArrayList);
                        actNotesInstruction.setAdapter(instructionAdapter);
                        actNotesInstruction.setThreshold(1);
                    }

                }else{
                    Toast.makeText(RxActivity.this, instructionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(RxActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
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
