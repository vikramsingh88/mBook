package vik.com.mbooks.login.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class User implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String pincode;
    private String profilePic;

    public User() {
    }

    public User(String id, String name, String email, String pincode, String profilePic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pincode = pincode;
        this.profilePic = profilePic;
    }

    public User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.pincode = in.readString();
        this.profilePic = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.email);
        parcel.writeString(this.pincode);
        parcel.writeString(this.profilePic);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
