package com.praxello.smartdoctor.model.allinstruction;

import java.util.ArrayList;

public class InstructionResponse {
    private int Responsecode;
    private String Message;
    private ArrayList<InstructionData> Data;

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

    public ArrayList<InstructionData> getData() {
        if(this.Data==null){
            Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<InstructionData> data) {
        Data = data;
    }
}
