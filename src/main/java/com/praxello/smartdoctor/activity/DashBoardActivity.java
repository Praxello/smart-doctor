package com.praxello.smartdoctor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.CommonMethods;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.adapter.autotextadapters.AllMedicineAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.DiagnosisAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.DosageAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.GetAllComplaintsAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.InstructionAdapter;
import com.praxello.smartdoctor.adapter.autotextadapters.TypeAdapter;
import com.praxello.smartdoctor.model.alldosage.DosageData;
import com.praxello.smartdoctor.model.alldosage.DosageResponse;
import com.praxello.smartdoctor.model.allinstruction.InstructionData;
import com.praxello.smartdoctor.model.allinstruction.InstructionResponse;
import com.praxello.smartdoctor.model.allmedicine.MedicineData;
import com.praxello.smartdoctor.model.allmedicine.MedicineResponse;
import com.praxello.smartdoctor.model.getAdvice.GetAdviceData;
import com.praxello.smartdoctor.model.getAdvice.GetAdviceResponse;
import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsData;
import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsResponse;
import com.praxello.smartdoctor.model.getalldiagnosis.AllDiagnosisData;
import com.praxello.smartdoctor.model.getalldiagnosis.AllDiagnosisResponse;
import com.praxello.smartdoctor.model.medicinetype.MedicineType;
import com.praxello.smartdoctor.model.medicinetype.MedicineTypeResponse;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import com.praxello.smartdoctor.services.SmartQuiz;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ll_allpatients)
    LinearLayout llAllPatients;
    @BindView(R.id.ll_collections)
    LinearLayout llCollections;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    public SmartQuiz smartQuiz;
    public static ArrayList<MedicineType> medicineTypeArrayList=new ArrayList<>();
    public static ArrayList<MedicineData> medicineDataArrayList=new ArrayList<>();
    public static ArrayList<DosageData> dosageDataArrayList=new ArrayList<>();
    public static ArrayList<InstructionData> instructionDataArrayList=new ArrayList<>();
    public static ArrayList<GetAllComplaintsData> getAllComplaintsDataArrayList=new ArrayList<>();
    public static ArrayList<AllDiagnosisData> allDiagnosisDataArrayList=new ArrayList<>();
    public static ArrayList<GetAdviceData> getAdviceDataArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        smartQuiz = (SmartQuiz) getApplication();
        llAllPatients.setOnClickListener(this);
        llCollections.setOnClickListener(this);
        llLogout.setOnClickListener(this);

        //load Data Medicine type....
        loadDataMedicineType();

        //load All Medicine...
        loadAllMedicines();

        //load all Instruction...
        loadAllInstruction();

        //load all complaints...
        loadAllComplaints();

        //load all diagnosis...
        loadAllDiagnosis();

        //load All dosage..
        loadAllDosage();

        //load All Advice...
        loadAllAdvice();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_allpatients:
                Intent intent = new Intent(DashBoardActivity.this,AllPatientsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                break;

            case R.id.ll_collections:
                break;

            case R.id.ll_logout:
                new android.app.AlertDialog.Builder(DashBoardActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage("Are you sure you want to logout?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.USER_ID, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.FIRST_NAME, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.LAST_NAME, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.MOBILE, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.EMAIL, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.CITY, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.STATE, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.COUNTRY, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.PINCODE, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.DATEOFBIRTH, AllKeys.DNF);
                                CommonMethods.setPreference(DashBoardActivity.this, AllKeys.ADDRESS, AllKeys.DNF);
                                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                                Toast.makeText(DashBoardActivity.this, "See you again!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    private void loadDataMedicineType(){
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllMedicineType(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                MedicineTypeResponse medicineTypeResponse=(MedicineTypeResponse) object;

                //Log.e(TAG, "onSuccess: "+medicineTypeResponse.getResponsecode());
               // Log.e(TAG, "onSuccess: "+medicineTypeResponse.getMessage());
                progress.dismiss();
                if(medicineTypeResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(medicineTypeResponse.getData()!=null){
                        medicineTypeArrayList = medicineTypeResponse.getData();

                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllMedicines(){
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllMedicine(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                MedicineResponse medicineResponse=(MedicineResponse) object;

              //  Log.e(TAG, "onSuccess: "+medicineResponse.getResponsecode());
               // Log.e(TAG, "onSuccess: "+medicineResponse.getMessage());

                progress.dismiss();
                if(medicineResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(medicineResponse.getData()!=null){
                          medicineDataArrayList = medicineResponse.getData();
                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, medicineResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllDosage(){
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllDosage(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                DosageResponse dosageResponse=(DosageResponse) object;

                //Log.e(TAG, "onSuccess: "+dosageResponse.getResponsecode());
               // Log.e(TAG, "onSuccess: "+dosageResponse.getMessage());
                progress.dismiss();
                if(dosageResponse.getResponsecode()==200){
                    // Toast.makeText(RxActivity.this, dosageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(dosageResponse.getData()!=null){
                        dosageDataArrayList=dosageResponse.getData();

                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, dosageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllInstruction() {
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllInstruction(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                InstructionResponse instructionResponse=(InstructionResponse) object;

              //  Log.e(TAG, "onSuccess: "+instructionResponse.getResponsecode());
              //  Log.e(TAG, "onSuccess: "+instructionResponse.getMessage());
                progress.dismiss();
                if(instructionResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, instructionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(instructionResponse.getData()!=null){
                        instructionDataArrayList=instructionResponse.getData();
                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, instructionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllComplaints(){
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllComplaints(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                GetAllComplaintsResponse getAllComplaintsResponse=(GetAllComplaintsResponse) object;

                //Log.e(TAG, "onSuccess: "+getAllComplaintsResponse.getResponsecode());
                //Log.e(TAG, "onSuccess: "+getAllComplaintsResponse.getMessage());
                progress.dismiss();
                if(getAllComplaintsResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(getAllComplaintsResponse.getData()!=null){
                        getAllComplaintsDataArrayList=getAllComplaintsResponse.getData();

                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, getAllComplaintsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllDiagnosis(){
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAllDiagnosis(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                AllDiagnosisResponse allDiagnosisResponse=(AllDiagnosisResponse) object;

              //  Log.e(TAG, "onSuccess: "+allDiagnosisResponse.getResponsecode());
                //Log.e(TAG, "onSuccess: "+allDiagnosisResponse.getMessage());
                progress.dismiss();
                if(allDiagnosisResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(allDiagnosisResponse.getData()!=null){
                        allDiagnosisDataArrayList=allDiagnosisResponse.getData();
                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, allDiagnosisResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllAdvice(){
        final ProgressDialog progress = new ProgressDialog(DashBoardActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        smartQuiz.getApiRequestHelper().getAdvice(new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                GetAdviceResponse getAdviceResponse=(GetAdviceResponse) object;

                //  Log.e(TAG, "onSuccess: "+allDiagnosisResponse.getResponsecode());
                //Log.e(TAG, "onSuccess: "+allDiagnosisResponse.getMessage());
                progress.dismiss();
                if(getAdviceResponse.getResponsecode()==200){
                    //Toast.makeText(RxActivity.this, medicineTypeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(getAdviceResponse.getData()!=null){
                        getAdviceDataArrayList=getAdviceResponse.getData();
                    }

                }else{
                    Toast.makeText(DashBoardActivity.this, getAdviceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(DashBoardActivity.this, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //finishAffinity();
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
