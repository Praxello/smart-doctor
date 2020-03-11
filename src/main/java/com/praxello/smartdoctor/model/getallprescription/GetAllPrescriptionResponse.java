package com.praxello.smartdoctor.model.getallprescription;

import java.util.ArrayList;

public class GetAllPrescriptionResponse {
    private String Message;
    private ArrayList<AllPrescriptionData> Data;
    private int Responsecode;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<AllPrescriptionData> getData() {
        return Data;
    }

    public void setData(ArrayList<AllPrescriptionData> data) {
        Data = data;
    }

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }
}
