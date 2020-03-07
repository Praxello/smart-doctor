package com.praxello.smartdoctor.model.allmedicine;

import java.util.ArrayList;

public class MedicineResponse {
    private int Responsecode;
    private String Message;
    public ArrayList<MedicineData> Data;

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<MedicineData> getData() {
        if(this.Data==null){
            Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<MedicineData> data) {
        Data = data;
    }
}
