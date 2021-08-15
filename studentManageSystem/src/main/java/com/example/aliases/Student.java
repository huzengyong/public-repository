package com.example.aliases;

public class Student {
    private int stuNo;
    private String stuName;
    private String speciality;
    private int stuClass;
    private String stuAddr;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStuNo(int stuNo) {
        this.stuNo = stuNo;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setStuClass(int stuClass) {
        this.stuClass = stuClass;
    }

    public void setStuAddr(String stuAddr) {
        this.stuAddr = stuAddr;
    }

    public int getStuNo() {
        return stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getStuClass() {
        return stuClass;
    }

    public String getStuAddr() {
        return stuAddr;
    }
}
