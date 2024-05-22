package com.example.qlct.API_Entity;

public class SharedDaTa {
    private static SharedDaTa instance = null;
    private UserProfile userProfile;

    private SharedDaTa() {
        // Private constructor to prevent instantiation
    }

    public static synchronized SharedDaTa getInstance() {
        if (instance == null) {
            instance = new SharedDaTa();
        }
        return instance;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile)  {
        this.userProfile = userProfile;
    }
}
