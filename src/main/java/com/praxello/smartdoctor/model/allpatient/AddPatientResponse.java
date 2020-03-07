package com.praxello.smartdoctor.model.allpatient;

import android.os.Parcel;
import android.os.Parcelable;

public class AddPatientResponse implements Parcelable {
    private String Message;
    private int Responsecode;
    AllPatientData Data;

    protected AddPatientResponse(Parcel in) {
        Message = in.readString();
        Responsecode = in.readInt();
        Data = in.readParcelable(AllPatientData.class.getClassLoader());
    }

    public static final Creator<AddPatientResponse> CREATOR = new Creator<AddPatientResponse>() {
        @Override
        public AddPatientResponse createFromParcel(Parcel in) {
            return new AddPatientResponse(in);
        }

        @Override
        public AddPatientResponse[] newArray(int size) {
            return new AddPatientResponse[size];
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

    public AllPatientData getData() {
        return Data;
    }

    public void setData(AllPatientData data) {
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
        dest.writeParcelable(Data, flags);
    }
}
