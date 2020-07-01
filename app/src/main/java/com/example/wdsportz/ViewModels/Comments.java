package com.example.wdsportz.ViewModels;

/**
 * Created by khrishawn
 */
public class Comments {


    private String content,uid,uimg, uname, key, chatID, reportID,report1;
    private Object currentUsereReportID, timestamp;

    public Comments(Object currentUsereReportID) {
        this.currentUsereReportID = currentUsereReportID;
    }
    public Comments() {

    }

    public Comments(String content, String uid, String uname, String uimg,String key) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.key = key;

    }


    public Comments(String content, String uid, String uname, String uimg) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;

    }

    public Comments(String content, String uid, String uname, String uimg, String key, String chatID) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.key = key;
        this.chatID = chatID;
    }
    public Comments(String content, String uid, String uname, String uimg, String key, String chatID, Object currentUsereReportID) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.key = key;
        this.chatID = chatID;
        this.currentUsereReportID = currentUsereReportID;
    }

    public Comments(String content, String uid, String uname, String uimg, String key, String chatID, String reportID) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.key = key;
        this.chatID = chatID;
        this.reportID = reportID;
    }

    public Comments(String content, String uid, String uname, String uimg, String key, String chatID, String reportID, Object timestamp) {
        this.content = content;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.key = key;
        this.chatID = chatID;
        this.reportID = reportID;
        this.timestamp = timestamp;

    }




    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }


    public String getReport1() {
        return report1;
    }

    public void setReport1(String report1) {
        this.report1 = report1;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
