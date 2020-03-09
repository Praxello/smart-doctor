package com.praxello.smartdoctor.services;

import android.text.Html;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.praxello.smartdoctor.AllKeys;
import com.praxello.smartdoctor.ConfigUrl;
import com.praxello.smartdoctor.model.CommonResponse;
import com.praxello.smartdoctor.model.UploadPrescriptionResponse;
import com.praxello.smartdoctor.model.alldosage.DosageResponse;
import com.praxello.smartdoctor.model.allinstruction.InstructionResponse;
import com.praxello.smartdoctor.model.allmedicine.MedicineResponse;
import com.praxello.smartdoctor.model.allpatient.AddPatientResponse;
import com.praxello.smartdoctor.model.allpatient.PatientResponse;
import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsResponse;
import com.praxello.smartdoctor.model.getalldiagnosis.AllDiagnosisResponse;
import com.praxello.smartdoctor.model.login.LoginResponse;
import com.praxello.smartdoctor.model.medicinetype.MedicineTypeResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    public interface OnRequestComplete {
        void onSuccess(Object object);

        void onFailure(String apiResponse);
    }

    private static ApiRequestHelper instance;
    private SmartQuiz application;
    private SmartQuizServices SmartQuizServices;
    static Gson gson;


    public static synchronized ApiRequestHelper init(SmartQuiz application) {
        if (null == instance) {
            instance = new ApiRequestHelper();
            instance.setApplication(application);
            gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                    .setExclusionStrategies(new ExclusionStrategy() {
//                        @Override
//                        public boolean shouldSkipField(FieldAttributes f) {
//                            return f.getDeclaringClass().equals(RealmObject.class);
//                        }
//
//                        @Override
//                        public boolean shouldSkipClass(Class<?> clazz) {
//                            return false;
//                        }
//                    })
                    .create();
            instance.createRestAdapter();
        }
        return instance;
    }

    public void login(String email,String password, final OnRequestComplete onRequestComplete) {
        Call<LoginResponse> call = SmartQuizServices.login(email,password);
        call_api_login(onRequestComplete, call);
    }

    public void getallpatient(String doctorId, final OnRequestComplete onRequestComplete) {
        Call<PatientResponse> call = SmartQuizServices.getallpatient(doctorId);
        call_api_all_patients(onRequestComplete, call);
    }

    public void deletePatient(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<CommonResponse> call = SmartQuizServices.deletePatient(params);
        call_api_add_leave_request(onRequestComplete, call);
    }

    public void addPatient(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<AddPatientResponse> call = SmartQuizServices.addPatient(params);
        call_api_add_patient_request(onRequestComplete, call);
    }

    public void updatePatient(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<AddPatientResponse> call = SmartQuizServices.updatePatient(params);
        call_api_add_patient_request(onRequestComplete, call);
    }

    public void uploadPrescription(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<UploadPrescriptionResponse> call = SmartQuizServices.uploadPrescription(params);
        call_api_upload_prescription_request(onRequestComplete, call);
    }

    public void printPrescription(Map<String, String> params, final OnRequestComplete onRequestComplete) {
        Call<CommonResponse> call = SmartQuizServices.printPrescription(params);
        call_api_add_leave_request(onRequestComplete, call);
    }

    public void getAllMedicine( final OnRequestComplete onRequestComplete) {
        Call<MedicineResponse> call = SmartQuizServices.getAllMedicine();
        call_api_medicine(onRequestComplete, call);
    }

    public void getAllMedicineType( final OnRequestComplete onRequestComplete) {
        Call<MedicineTypeResponse> call = SmartQuizServices.getAllMedicineType();
        call_api_medicine_type(onRequestComplete, call);
    }

    public void getAllDosage( final OnRequestComplete onRequestComplete) {
        Call<DosageResponse> call = SmartQuizServices.getAllDosage();
        call_api_dosage(onRequestComplete, call);
    }

    public void getAllInstruction( final OnRequestComplete onRequestComplete) {
        Call<InstructionResponse> call = SmartQuizServices.getAllInstruction();
        call_api_instruction(onRequestComplete, call);
    }

    public void getAllComplaints( final OnRequestComplete onRequestComplete) {
        Call<GetAllComplaintsResponse> call = SmartQuizServices.getAllComplaints();
        call_api_complaints(onRequestComplete, call);
    }

    public void getAllDiagnosis( final OnRequestComplete onRequestComplete) {
        Call<AllDiagnosisResponse> call = SmartQuizServices.getAllDiagnosis();
        call_api_diagnosis(onRequestComplete, call);
    }

    private void call_api_diagnosis(final OnRequestComplete onRequestComplete, Call<AllDiagnosisResponse> call) {
        call.enqueue(new Callback<AllDiagnosisResponse>() {
            @Override
            public void onResponse(Call<AllDiagnosisResponse> call, Response<AllDiagnosisResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<AllDiagnosisResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_complaints(final OnRequestComplete onRequestComplete, Call<GetAllComplaintsResponse> call) {
        call.enqueue(new Callback<GetAllComplaintsResponse>() {
            @Override
            public void onResponse(Call<GetAllComplaintsResponse> call, Response<GetAllComplaintsResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetAllComplaintsResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }


    private void call_api_upload_prescription_request(final OnRequestComplete onRequestComplete, Call<UploadPrescriptionResponse> call) {
        call.enqueue(new Callback<UploadPrescriptionResponse>() {
            @Override
            public void onResponse(Call<UploadPrescriptionResponse> call, Response<UploadPrescriptionResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<UploadPrescriptionResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_instruction(final OnRequestComplete onRequestComplete, Call<InstructionResponse> call) {
        call.enqueue(new Callback<InstructionResponse>() {
            @Override
            public void onResponse(Call<InstructionResponse> call, Response<InstructionResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<InstructionResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_dosage(final OnRequestComplete onRequestComplete, Call<DosageResponse> call) {
        call.enqueue(new Callback<DosageResponse>() {
            @Override
            public void onResponse(Call<DosageResponse> call, Response<DosageResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<DosageResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_medicine_type(final OnRequestComplete onRequestComplete, Call<MedicineTypeResponse> call) {
        call.enqueue(new Callback<MedicineTypeResponse>() {
            @Override
            public void onResponse(Call<MedicineTypeResponse> call, Response<MedicineTypeResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<MedicineTypeResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }


    private void call_api_medicine(final OnRequestComplete onRequestComplete, Call<MedicineResponse> call) {
        call.enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_add_patient_request(final OnRequestComplete onRequestComplete, Call<AddPatientResponse> call) {
        call.enqueue(new Callback<AddPatientResponse>() {
            @Override
            public void onResponse(Call<AddPatientResponse> call, Response<AddPatientResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPatientResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_add_leave_request(final OnRequestComplete onRequestComplete, Call<CommonResponse> call) {
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }



    private void call_api_all_patients(final OnRequestComplete onRequestComplete, Call<PatientResponse> call) {
        call.enqueue(new Callback<PatientResponse>() {
            @Override
            public void onResponse(Call<PatientResponse> call, Response<PatientResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void call_api_login(final OnRequestComplete onRequestComplete, Call<LoginResponse> call) {
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    onRequestComplete.onSuccess(response.body());
                } else {
                    try {
                        onRequestComplete.onFailure(Html.fromHtml(response.errorBody().string()) + "");
                    } catch (IOException e) {
                        onRequestComplete.onFailure("Unproper Response");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                handle_fail_response(t, onRequestComplete);
            }
        });
    }

    private void handle_fail_response(Throwable t, OnRequestComplete onRequestComplete) {
        if (t.getMessage() != null) {
            if (t.getMessage().contains("Unable to resolve host")) {
                onRequestComplete.onFailure(AllKeys.NO_INTERNET_AVAILABLE);
            } else {
                onRequestComplete.onFailure(Html.fromHtml(t.getLocalizedMessage()) + "");
            }
        } else
            onRequestComplete.onFailure(AllKeys.UNPROPER_RESPONSE);
    }

    private static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    /**
     * REST Adapter Configuration
     */
    private void createRestAdapter() {
//        ObjectMapper objectMapper = new ObjectMapper();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
// add your other interceptors …

// add logging as last interceptor
        httpClient.interceptors().add(logging);
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(ConfigUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.client(httpClient.build()).build();
        SmartQuizServices = retrofit.create(SmartQuizServices.class);
    }

    /**
     * End REST Adapter Configuration
     */

    public SmartQuiz getApplication() {
        return application;
    }

    public void setApplication(SmartQuiz application) {
        this.application = application;
    }

}
