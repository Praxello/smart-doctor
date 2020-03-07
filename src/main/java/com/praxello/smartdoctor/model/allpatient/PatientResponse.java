package com.praxello.smartdoctor.model.allpatient;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class PatientResponse implements Parcelable {
    private String Message;
    private int Responsecode;
    private ArrayList<AllPatientData> Data;

    protected PatientResponse(Parcel in) {
        Message = in.readString();
        Responsecode = in.readInt();
        Data = in.createTypedArrayList(AllPatientData.CREATOR);
    }

    public static final Creator<PatientResponse> CREATOR = new Creator<PatientResponse>() {
        @Override
        public PatientResponse createFromParcel(Parcel in) {
            return new PatientResponse(in);
        }

        @Override
        public PatientResponse[] newArray(int size) {
            return new PatientResponse[size];
        }
    };

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }

    public ArrayList<AllPatientData> getData() {
        if(this.Data==null){
            Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<AllPatientData> data) {
        Data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Message);
        dest.writeInt(Responsecode);
        dest.writeTypedList(Data);
    }
}
