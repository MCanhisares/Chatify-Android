package com.azell.chatify_android.Api.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.azell.chatify_android.Api.Utils.Resources;

/**
 * Created by mcanhisares on 01/03/17.
 */

public class User implements Parcelable {

    private String email;
    private String profileImageUrl;
    private String uid;
    private String username;

    public User() {
    }

    public User(String email, String profileImageUrl, String uid, String username) {
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.uid = uid;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl.isEmpty() ? Resources.defaultProfileImageUrl : profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.uid);
        dest.writeString(this.username);
    }

    protected User(Parcel in) {
        this.email = in.readString();
        this.profileImageUrl = in.readString();
        this.uid = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
