package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class QuestionResponse extends RealmObject implements Parcelable {

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
    private RealmList<String> jawabanAwal;
    private RealmList<String> jawabanUser = null;

    public QuestionResponse() {
    }

    protected QuestionResponse(Parcel in) {
        id = in.readString();
        pertanyaan = in.readString();
        tipe = in.readString();
        domainId = in.readString();
        domain = in.readString();
        notes = in.readString();
        jawabanAwal = new RealmList<>();
        jawabanAwal.addAll(in.createStringArrayList());
        jawabanUser = new RealmList<>();
        jawabanUser.addAll(in.createStringArrayList());
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

    public void setId(String id) {
        this.id = id;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public RealmList<String> getJawabanAwal() {
        if(jawabanAwal == null)
            jawabanAwal = new RealmList<>();
        return jawabanAwal;
    }

    public String getTipe() {
        return tipe;
    }

    public void setJawabanUser(ArrayList<String> jawabanUser) {
        if (this.jawabanUser == null)
            this.jawabanUser = new RealmList<>();
        this.jawabanUser.clear();
        this.jawabanUser.addAll(jawabanUser);
    }

    public void setJawabanUser(String jawaban) {
        if (this.jawabanUser == null)
            this.jawabanUser = new RealmList<>();
        this.jawabanUser.clear();
        this.jawabanUser.add(jawaban);
    }

    public void setJawabanUser(RealmList<String> jawabanUser) {
        this.jawabanUser = jawabanUser;
    }

    public RealmList<String> getJawabanUser() {
        if (this.jawabanUser == null)
            jawabanUser = new RealmList<>();
        return jawabanUser;
    }

    public String getJawabanUserAsString(){
        if(this.jawabanUser == null)
            jawabanUser = new RealmList<>();
        StringBuilder answers = new StringBuilder();
        for (int i = 0; i < getJawabanUser().size(); i++) {
            String answer = getJawabanUser().get(i);
            if (i == getJawabanUser().size() - 1 )
                answers.append(answer);
            else
                answers.append(answer+ ", ");
        }
        return answers.toString();
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
        dest.writeStringList(this.jawabanUser);
    }
}
