package ypw.app.wdsportz.ViewModels;

import android.util.Log;

public class WatchViewModel {

    String title;
    String videoimageURL;
    String videoURL;
    String chatBox_ID;
    String video_desc;
    Boolean live;
    String Category;
    String  date;
    String accessPassword;

    /**
     *
     * @param title
     * @param videoimageURL
     * @param videoURL
     * @param chatBox_ID
     * @param video_desc
     * @param live
     * @param date
     */

    //Live Videos
    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc, Boolean live, String date, String accessPassword) {
        this.title = title;
        this.videoimageURL = videoimageURL;
        this.videoURL = videoURL;
        this.chatBox_ID = chatBox_ID;
        this.video_desc = video_desc;
        this.live = live;
        this.date = date;

        if(accessPassword == ""){
            this.accessPassword = "null";
        } else {
            this.accessPassword = accessPassword;
        }

        Log.d("Model Test",  accessPassword);
    }

    /**
     *
     * @param title
     * @param videoimageURL
     * @param videoURL
     * @param chatBox_ID
     * @param video_desc
     * @param Category
     * @param date
     */

    public WatchViewModel(String title, String videoimageURL, String videoURL, String chatBox_ID, String video_desc, Object Category,String date) {
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
        this.date = date;

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

    public  String getDate() {
        return date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAccessPassword() {
        return accessPassword;
    }
}
