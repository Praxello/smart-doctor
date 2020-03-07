package com.praxello.smartdoctor.model.medicinetype;

public class MedicineType {
        private int medicineTypeId;
        private String type;
        private int isActive;

    public int getMedicineTypeId() {
        return medicineTypeId;
    }

    public void setMedicineTypeId(int medicineTypeId) {
        this.medicineTypeId = medicineTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
