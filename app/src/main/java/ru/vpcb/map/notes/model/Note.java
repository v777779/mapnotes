package ru.vpcb.map.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String key;
    private double latitude;
    private double longitude;
    private String text;
    private String user;

    public Note() {  // for database reading ok
    }

    public Note(double latitude, double longitude, String text, String user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.text = text;
        this.user = user;
    }

// parcelable

    protected Note(Parcel in) {
        key = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        text = in.readString();
        user = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(text);
        dest.writeString(user);
    }

// methods


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


}
