package com.example.user1.myapplication.Model;

import io.realm.RealmObject;

public class AnswerHeaderModel extends RealmObject {
    private String consentForm, enumeratorName, area, kecamatan, lembagaPaud, nomorNpsn, jabatan, user_id, period;

    public AnswerHeaderModel() {
    }

    public void setConsentForm(String consentForm) {
        this.consentForm = consentForm;
    }

    public void setEnumeratorName(String enumeratorName) {
        this.enumeratorName = enumeratorName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setLembagaPaud(String lembagaPaud) {
        this.lembagaPaud = lembagaPaud;
    }

    public void setNomorNpsn(String nomorNpsn) {
        this.nomorNpsn = nomorNpsn;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getConsentForm() {
        return consentForm;
    }

    public String getEnumeratorName() {
        return enumeratorName;
    }

    public String getArea() {
        return area;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getLembagaPaud() {
        return lembagaPaud;
    }

    public String getNomorNpsn() {
        return nomorNpsn;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPeriod() {
        return period;
    }
}

