package com.example.wdsportz.ViewModels;

public class WatchViewModel {

    String title;
    String videoimageURL;
    String videoURL;
    String chatBox_ID;
    String video_desc;
    Boolean live;
    String Category;

//    public WatchViewModel() {
//
//    }

//    public WatchViewModel(String league_name, String league_image, String league_url, String chatbox_id, String video_desc) {
//
//    }

    //Live Videos
    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc, Boolean live) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
        this.video_desc = video_desc;
        this.live = live;
    }

    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc, Object Category) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
        this.video_desc = video_desc;

        if(Category != null){
            this.Category = Category.toString();
        } else {
            this.Category = "null";
        }

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

    public Boolean getLive() {
        return live;
    }

    public void setLive(String chatBox_ID) {this.live = live; }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
