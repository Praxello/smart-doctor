package com.praxello.smartdoctor.model.medicinetype;

import java.util.ArrayList;

public class MedicineTypeResponse {
    private int Responsecode;
    private ArrayList<MedicineType> Data;
    private String Message;

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }

    public ArrayList<MedicineType> getData() {
        if(this.Data==null){
            Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<MedicineType> data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
