package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MainGroupResponse extends RealmObject implements Parcelable {



    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.description);
    }

    public MainGroupResponse() {
    }

    protected MainGroupResponse(Parcel in) {
        this.id = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<MainGroupResponse> CREATOR = new Parcelable.Creator<MainGroupResponse>() {
        @Override
        public MainGroupResponse createFromParcel(Parcel source) {
            return new MainGroupResponse(source);
        }

        @Override
        public MainGroupResponse[] newArray(int size) {
            return new MainGroupResponse[size];
        }
    };
}
