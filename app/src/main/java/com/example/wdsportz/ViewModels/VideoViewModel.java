package com.example.wdsportz.ViewModels;

/**
 * Created by khrishawn
 */
public class VideoViewModel {
    private String name;
    private String email;
    private String profilePic;
    private String permission;

    public VideoViewModel() {

    }

    public VideoViewModel(String name, String email, String profilePic, String permission) {
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
