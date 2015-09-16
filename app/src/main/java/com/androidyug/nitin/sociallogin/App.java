package com.androidyug.nitin.sociallogin;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by IAMONE on 9/16/2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(getApplicationContext(), "jLrlfXlaKpqB4qbl25EzPqVquPti7EuY334HFecB", "hdcnw67K9pensgJCqzVdzhppE0ZcPXVuIMjPXA1K");
    }
}
