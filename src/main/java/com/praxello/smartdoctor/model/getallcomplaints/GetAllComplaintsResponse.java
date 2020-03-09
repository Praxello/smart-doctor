package com.praxello.smartdoctor.model.getallcomplaints;

import java.util.ArrayList;

public class GetAllComplaintsResponse {
    private String Message;
    private ArrayList<GetAllComplaintsData> Data;
    private int Responsecode;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<GetAllComplaintsData> getData() {
        return Data;
    }

    public void setData(ArrayList<GetAllComplaintsData> data) {
        Data = data;
    }

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }
}
