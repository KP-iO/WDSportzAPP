package com.example.wdsportz.viewmodels;

/**
 * Created by khrishawn
 */
public class VideoViewModel {
    String title;
    String videoimageURL;
    String videoURL;



    public VideoViewModel() {

    }

    public VideoViewModel(String title, String videoimageURL, String videoURL) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
    }




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
