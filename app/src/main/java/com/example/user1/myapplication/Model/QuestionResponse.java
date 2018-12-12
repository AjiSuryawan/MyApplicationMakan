package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pertanyaan")
    @Expose
    private String pertanyaan;
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("domain_id")
    @Expose
    private String domainId;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("jawaban")
    @Expose
    private ArrayList<String> jawabanAwal;

    private ArrayList<String> jawabanUser = null;

    public QuestionResponse(String id, String pertanyaan, String tipe, String domainId, String domain, String notes, ArrayList<String> jawabanAwal) {
        this.id = id;
        this.pertanyaan = pertanyaan;
        this.tipe = tipe;
        this.domainId = domainId;
        this.domain = domain;
        this.notes = notes;
        this.jawabanAwal = jawabanAwal;
    }

    protected QuestionResponse(Parcel in) {
        id = in.readString();
        pertanyaan = in.readString();
        tipe = in.readString();
        domainId = in.readString();
        domain = in.readString();
        notes = in.readString();
        jawabanAwal = in.createStringArrayList();
    }

    public static final Creator<QuestionResponse> CREATOR = new Creator<QuestionResponse>() {
        @Override
        public QuestionResponse createFromParcel(Parcel in) {
            return new QuestionResponse(in);
        }

        @Override
        public QuestionResponse[] newArray(int size) {
            return new QuestionResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getTipe() {
        return tipe;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getDomain() {
        return domain;
    }

    public String getNotes() {
        return notes;
    }

    public ArrayList<String> getJawabanAwal() {
        return jawabanAwal;
    }

    public void setJawabanUser(ArrayList<String> jawabanUser) {
        if(this.jawabanUser == null)
            this.jawabanUser = new ArrayList<>();
        this.jawabanUser.clear();
        this.jawabanUser.addAll(jawabanUser);
    }

    public void setJawabanUser(String jawaban){
        if(this.jawabanUser == null)
            this.jawabanUser = new ArrayList<>();
        this.jawabanUser.clear();
        this.jawabanUser.add(jawaban);
    }

    public ArrayList<String> getJawabanUser() {
        if(this.jawabanUser == null)
            jawabanUser = new ArrayList<>();
        return jawabanUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pertanyaan);
        dest.writeString(tipe);
        dest.writeString(domainId);
        dest.writeString(domain);
        dest.writeString(notes);
        dest.writeStringList(jawabanAwal);
    }
}
