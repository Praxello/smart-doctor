package com.praxello.smartdoctor.model.getAdvice;

import java.util.ArrayList;

public class GetAdviceResponse {
    private String Message;
    private int Responsecode;
    private ArrayList<GetAdviceData> Data;

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

    public ArrayList<GetAdviceData> getData() {
        return Data;
    }

    public void setData(ArrayList<GetAdviceData> data) {
        Data = data;
    }
}
