package com.praxello.smartdoctor.model.getalldiagnosis;

import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsData;

import java.util.ArrayList;

public class AllDiagnosisResponse {
    private String Message;
    private ArrayList<AllDiagnosisData> Data;
    private int Responsecode;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<AllDiagnosisData> getData() {
        return Data;
    }

    public void setData(ArrayList<AllDiagnosisData> data) {
        Data = data;
    }

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }
}
