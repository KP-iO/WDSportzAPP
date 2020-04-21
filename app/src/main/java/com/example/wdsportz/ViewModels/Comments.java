package com.example.wdsportz.ViewModels;

import com.google.firebase.database.ServerValue;

/**
 * Created by khrishawn
 */
public class Comments {


    private String content,uid,uimg, uname;
    private Object timestamp;

    public Comments() {
    }

    public Comments(String content, String uid, String uname, String uimg) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.timestamp = ServerValue.TIMESTAMP ;

    }

    public Comments(String content, String uid, String uname, Object timestamp, String uimg) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.timestamp = ServerValue.TIMESTAMP ;

    }




    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
