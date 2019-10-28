package com.example.wdsportz.viewmodels;

/**
 * Created by khrishawn
 */
public class VideoPlayViewModel {

    String videoURL;



    public VideoPlayViewModel() {

    }

    public VideoPlayViewModel (String title, String videoimageURL) {
//        this.title = title;
        this.videoURL = videoURL;
    }






    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {this.videoURL = videoURL; }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
}
