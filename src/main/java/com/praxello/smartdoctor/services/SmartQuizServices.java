package com.praxello.smartdoctor.services;

import com.praxello.smartdoctor.model.CommonResponse;
import com.praxello.smartdoctor.model.UploadPrescriptionResponse;
import com.praxello.smartdoctor.model.alldosage.DosageResponse;
import com.praxello.smartdoctor.model.allinstruction.InstructionResponse;
import com.praxello.smartdoctor.model.allmedicine.MedicineResponse;
import com.praxello.smartdoctor.model.allpatient.AddPatientResponse;
import com.praxello.smartdoctor.model.allpatient.PatientResponse;
import com.praxello.smartdoctor.model.getAdvice.GetAdviceResponse;
import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsResponse;
import com.praxello.smartdoctor.model.getalldiagnosis.AllDiagnosisResponse;
import com.praxello.smartdoctor.model.getallprescription.GetAllPrescriptionResponse;
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

   // @GET("~tailor/Spine360/apis/getAllMedicines.php")
    @GET("~tailor/smartquiz/smartdoctor/getAllMedicines.php")
    Call<MedicineResponse> getAllMedicine();

    //@GET("~tailor/Spine360/apis/getAllMedicinesType.php")
    @GET("~tailor/smartquiz/smartdoctor/getAllMedicinesType.php")
    Call<MedicineTypeResponse> getAllMedicineType();

    //@GET("~tailor/Spine360/apis/getAllMedicineDosage.php")
    @GET("~tailor/smartquiz/smartdoctor/getAllMedicineDosage.php")
    Call<DosageResponse> getAllDosage();

    //@GET("~tailor/Spine360/apis/getAllInstruction.php")
    @GET("~tailor/smartquiz/smartdoctor/getAllInstruction.php")
    Call<InstructionResponse> getAllInstruction();

    @GET("~tailor/smartquiz/smartdoctor/getAllcomplaints.php")
    Call<GetAllComplaintsResponse> getAllComplaints();

    @GET("~tailor/smartquiz/smartdoctor/getAllDiagnosis.php")
    Call<AllDiagnosisResponse> getAllDiagnosis();

    @GET("~tailor/smartquiz/smartdoctor/get_Advice.php")
    Call<GetAdviceResponse> getAdvice();

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/addPrescription.php")
    Call<UploadPrescriptionResponse> uploadPrescription(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/prescription-print.php")
    Call<CommonResponse> printPrescription(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("~tailor/smartquiz/smartdoctor/getAllPrescriptions.php")
    Call<GetAllPrescriptionResponse> getAllPrescription(@Field("patientId") String patientId);


}
