package com.praxello.smartdoctor.model;

public class AddPrescriptionData {

    private String typeId;
    private String medicineId;
    private String morning;
    private String evining;
    private String night;
    private String duration;
    private String inst;

    public AddPrescriptionData(String typeId, String medicineId, String morning, String evining, String night, String duration, String inst) {
        this.typeId = typeId;
        this.medicineId = medicineId;
        this.morning = morning;
        this.evining = evining;
        this.night = night;
        this.duration = duration;
        this.inst = inst;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getEvining() {
        return evining;
    }

    public void setEvining(String evining) {
        this.evining = evining;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }
}
