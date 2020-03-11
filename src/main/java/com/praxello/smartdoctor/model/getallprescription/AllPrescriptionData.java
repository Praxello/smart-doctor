package com.praxello.smartdoctor.model.getallprescription;

import java.util.ArrayList;

public class AllPrescriptionData {
    private int patientId;
    private String visitDate;
    private String nextVisitDate;
    private String complaint;
    private String advice;
    private String diagnosis;
    private int doctorId;
    private String firstName;
    private String lastName;
    private ArrayList<MedicinesData> medicines;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(String nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getDoctorId() { return doctorId; }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<MedicinesData> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<MedicinesData> medicines) {
        this.medicines = medicines;
    }
}
