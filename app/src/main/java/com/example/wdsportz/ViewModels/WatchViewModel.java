package com.example.wdsportz.ViewModels;

public class WatchViewModel {

    String title;
    String videoimageURL;
    String videoURL;
    String chatBox_ID;



    public WatchViewModel() {

    }

    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
    }



    public  String getChatBox_ID() {
        return chatBox_ID;
    }

    public void setChatBox_ID(String chatBox_ID) {this.chatBox_ID = chatBox_ID; }


    public  String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {this.videoURL = videoURL; }



    public String getVideoimageURL() {
        return videoimageURL;
    }

    public void setVideoimageURL(String videoimageURL) {this.videoimageURL = videoimageURL; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
