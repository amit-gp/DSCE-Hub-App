package com.example.amit.dsiofficial;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Amit on 10-08-2017.
 */

public class MessageNotification{

    private String notificationTitle;
    private String getNotificationBody;
    private Date dateUploaded;


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


    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }
}
