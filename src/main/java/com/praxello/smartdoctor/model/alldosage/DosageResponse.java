package com.praxello.smartdoctor.model.alldosage;

import java.util.ArrayList;

public class DosageResponse {
    private int Responsecode;
    private String Message;
    private ArrayList<DosageData> Data;

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

    public ArrayList<DosageData> getData() {
        if(this.Data==null){
            Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<DosageData> data) {
        Data = data;
    }
}
