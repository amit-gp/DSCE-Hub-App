package com.example.amit.dsiofficial;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Amit on 10-08-2017.
 */

public class MessageNotification implements Parcelable{

    private String notificationTitle;
    private String getNotificationBody;
    private Date dateUploaded;

    private MessageNotification(Parcel in){
        notificationTitle = in.readString();
        getNotificationBody = in.readString();
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getGetNotificationBody() {
        return getNotificationBody;
    }

    public void setNotificationBody(String getNotificationBody) {
        this.getNotificationBody = getNotificationBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(notificationTitle);
        parcel.writeString(getNotificationBody);
    }

    public static final Parcelable.Creator<MessageNotification> CREATOR
            = new Parcelable.Creator<MessageNotification>() {
        public MessageNotification createFromParcel(Parcel in) {
            return new MessageNotification(in);
        }

        public MessageNotification[] newArray(int size) {
            return new MessageNotification[size];
        }
    };


    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }
}
