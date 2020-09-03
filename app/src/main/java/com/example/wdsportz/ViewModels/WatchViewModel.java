package com.example.wdsportz.ViewModels;

import java.util.Date;

public class WatchViewModel {

    String title;
    String videoimageURL;
    String videoURL;
    String chatBox_ID;
    String video_desc;
    String live;
    String  date;

//    public WatchViewModel() {
//
//    }

//    public WatchViewModel(String league_name, String league_image, String league_url, String chatbox_id, String video_desc) {
//
//    }

    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc, String live, String date) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
        this.video_desc = video_desc;
        this.live = live;
        this.date = date;
    }

    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
        this.video_desc = video_desc;

    }

    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc, String live) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
        this.video_desc = video_desc;
        this.live = live;
    }


    public  String getChatBox_ID() {
        return chatBox_ID;
    }

    public void setChatBox_ID(String chatBox_ID) {this.chatBox_ID = chatBox_ID; }


    public  String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {this.videoURL = videoURL; }



    public String getVideoImageURL() {
        return videoimageURL;
    }

    public void setVideoimageURL(String videoimageURL) {this.videoimageURL = videoimageURL; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public  String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {this.video_desc = video_desc; }

    public  String getLive() {
        return live;
    }

    public void setLive(String chatBox_ID) {this.live = live; }

    public  String getDate() {
        return date;
    }

    public void setDate(String date) {this.live = date; }
}
