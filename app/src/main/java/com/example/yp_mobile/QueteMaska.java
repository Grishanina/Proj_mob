package com.example.yp_mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class QueteMaska implements Parcelable {

    private int id;
    private String title;
    private String image;
    private String description;

    public QueteMaska(int id, String title, String image, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
    }

    protected QueteMaska(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<QueteMaska> CREATOR = new Creator<QueteMaska>() {
        @Override
        public QueteMaska createFromParcel(Parcel in) {
            return new QueteMaska(in);
        }

        @Override
        public QueteMaska[] newArray(int size) {
            return new QueteMaska[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
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
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
    }
}
