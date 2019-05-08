package ru.vpcb.test.map.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private double mLatitude;
    private double mLongitude;
    private String mText;
    private String mUser;

    public Note(double latitude, double longitude, String text, String user) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mText = text;
        this.mUser = user;
    }

// parcelable

    protected Note(Parcel in) {
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mText = in.readString();
        mUser = in.readString();
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
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mText);
        dest.writeString(mUser);
    }

// methods

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        this.mUser = user;
    }


}
