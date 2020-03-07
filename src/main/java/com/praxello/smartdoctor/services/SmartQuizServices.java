package com.praxello.smartdoctor.services;

import com.praxello.smartdoctor.model.CommonResponse;
import com.praxello.smartdoctor.model.alldosage.DosageResponse;
import com.praxello.smartdoctor.model.allinstruction.InstructionResponse;
import com.praxello.smartdoctor.model.allmedicine.MedicineResponse;
import com.praxello.smartdoctor.model.allpatient.AddPatientResponse;
import com.praxello.smartdoctor.model.allpatient.PatientResponse;
import com.praxello.smartdoctor.model.login.LoginResponse;
import com.praxello.smartdoctor.model.medicinetype.MedicineTypeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SmartQuizServices {

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/login.php")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/getpatientdata.php")
    Call<PatientResponse> getallpatient( @Field("doctorId") String doctorId);


    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/deletepatient.php")
    Call<CommonResponse> deletePatient(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/addpatient.php")
    Call<AddPatientResponse> addPatient(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/updatepatient.php")
    Call<AddPatientResponse> updatePatient(@FieldMap Map<String, String> params);

    @GET("~tailor/Spine360/apis/getAllMedicines.php")
    Call<MedicineResponse> getAllMedicine();

    @GET("~tailor/Spine360/apis/getAllMedicinesType.php")
    Call<MedicineTypeResponse> getAllMedicineType();

    @GET("~tailor/Spine360/apis/getAllMedicineDosage.php")
    Call<DosageResponse> getAllDosage();

    @GET("~tailor/Spine360/apis/getAllInstruction.php")
    Call<InstructionResponse> getAllInstruction();
}