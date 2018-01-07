package com.example.vinayakahebbar.doctorapp.utils;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Vinayaka Hebbar on 04-01-2018.
 */

public class UserManager {
    private static FirebaseAuth auth;
    static {
        auth = FirebaseAuth.getInstance();
    }
    public static void sigOut(){
        auth.signOut();
    }

    public static String getUserName(){
       return auth.getCurrentUser().getEmail();
    }

    @NonNull
    public static String getUserId(){
        return auth.getCurrentUser().getUid();
    }
}
