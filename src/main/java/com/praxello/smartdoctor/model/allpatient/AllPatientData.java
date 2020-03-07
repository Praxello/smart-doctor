package com.praxello.smartdoctor.model.allpatient;

import android.os.Parcel;
import android.os.Parcelable;

public class AllPatientData implements Parcelable {
    private int patientId;
    private int doctorId;
    private String firstName;
    private String midlleName;
    private String lastName;
    private String  mobile;
    private String birthDate;
    private int age;
    private String gender;
    private String height;
    private String weight;
    private String city;
    private String Address;
    private String Reference;
    private int isActive;

    public AllPatientData(int patientId, int doctorId, String firstName, String midlleName, String lastName, String mobile, String birthDate, int age, String gender, String height, String weight, String city, String address, String reference, int isActive) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.midlleName = midlleName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.birthDate = birthDate;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.city = city;
        Address = address;
        Reference = reference;
        this.isActive = isActive;
    }

    protected AllPatientData(Parcel in) {
        patientId = in.readInt();
        doctorId = in.readInt();
        firstName = in.readString();
        midlleName = in.readString();
        lastName = in.readString();
        mobile = in.readString();
        birthDate = in.readString();
        age = in.readInt();
        gender = in.readString();
        height = in.readString();
        weight = in.readString();
        city = in.readString();
        Address = in.readString();
        Reference = in.readString();
        isActive = in.readInt();
    }

    public static final Creator<AllPatientData> CREATOR = new Creator<AllPatientData>() {
        @Override
        public AllPatientData createFromParcel(Parcel in) {
            return new AllPatientData(in);
        }

        @Override
        public AllPatientData[] newArray(int size) {
            return new AllPatientData[size];
        }
    };

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidlleName() {
        return midlleName;
    }

    public void setMidlleName(String midlleName) {
        this.midlleName = midlleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String  getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(patientId);
        dest.writeInt(doctorId);
        dest.writeString(firstName);
        dest.writeString(midlleName);
        dest.writeString(lastName);
        dest.writeString(mobile);
        dest.writeString(birthDate);
        dest.writeInt(age);
        dest.writeString(gender);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(city);
        dest.writeString(Address);
        dest.writeString(Reference);
        dest.writeInt(isActive);
    }
}
