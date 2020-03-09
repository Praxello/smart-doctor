package com.praxello.smartdoctor.model;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class PostData {

    @SerializedName("medicinesDetails")
    private JsonArray medicinesDetails;

    private String patientId;
    private String doctorId;
    private String nextvisit;
    private String visitDate;
    private String pulse;
    private String height;
    private String weight;
    private String west;
    private String hip;
    private String temp;
    private String spo2;
    private String bp;
    private String complaints;
    private String diagnosis;
    private String remarks;

    public PostData(JsonArray medicinesDetails, String patientId, String doctorId, String nextvisit, String visitDate, String pulse, String height, String weight, String west, String hip, String temp, String spo2, String bp, String complaints, String diagnosis, String remarks) {
        this.medicinesDetails = medicinesDetails;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.nextvisit = nextvisit;
        this.visitDate = visitDate;
        this.pulse = pulse;
        this.height = height;
        this.weight = weight;
        this.west = west;
        this.hip = hip;
        this.temp = temp;
        this.spo2 = spo2;
        this.bp = bp;
        this.complaints = complaints;
        this.diagnosis = diagnosis;
        this.remarks = remarks;
    }

    public JsonArray getMedicinesDetails() {

        return medicinesDetails;
    }

    public void setMedicinesDetails(JsonArray medicinesDetails) {
        this.medicinesDetails = medicinesDetails;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getNextvisit() {
        return nextvisit;
    }

    public void setNextvisit(String nextvisit) {
        this.nextvisit = nextvisit;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getHip() {
        return hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSpo2() {
        return spo2;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }


    @NonNull
    @Override
    public String toString() {
        return medicinesDetails+" "+patientId+" "+doctorId+" "+nextvisit+" "+visitDate+" "+pulse+" "+height+" "+weight+" "+west+" "+hip+" "+temp+" "+spo2+" "+bp;
    }
}
