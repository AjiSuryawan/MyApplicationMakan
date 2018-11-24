package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dicoding on 12/6/2016.
 */

public class ModelDatabase implements Parcelable {
    private int id;
    private String name;
    private String nim;
    private String uriImage;

    public ModelDatabase(){

    }


    public ModelDatabase(String name, String nim, String uriImage) {
        this.name = name;
        this.nim = nim;
        this.uriImage = uriImage;
    }

    public String getUriImage() {
        return uriImage;
    }


    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nim);
    }

    protected ModelDatabase(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.nim = in.readString();
    }

    public static final Parcelable.Creator<ModelDatabase> CREATOR = new Parcelable.Creator<ModelDatabase>() {
        @Override
        public ModelDatabase createFromParcel(Parcel source) {
            return new ModelDatabase(source);
        }

        @Override
        public ModelDatabase[] newArray(int size) {
            return new ModelDatabase[size];
        }
    };
}
