package com.androidyug.nitin.sociallogin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getCallbackRequestCodeOffset;

public class MainActivity extends AppCompatActivity {

    TextView mErr;
    private VideoView mVideoBg;

    Button mFbLogInBtn;
    Button mTwitterLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FacebookSdk.sdkInitialize(getApplicationContext());

        //Parse.enableLocalDatastore(this);
         //Parse.initialize(this, "jLrlfXlaKpqB4qbl25EzPqVquPti7EuY334HFecB", "hdcnw67K9pensgJCqzVdzhppE0ZcPXVuIMjPXA1K");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseFacebookUtils.initialize(this);
        ParseTwitterUtils.initialize("ftrB4BYqfT0LjuwIxkEVvrXeN", "EzKP1FOI1vVXtr34dNHQiV9rv1efm6IIcuupNHVWG8fYD6fpGN");



        // initilize the views
        mFbLogInBtn = (Button) findViewById(R.id.fb_login_button);
        mTwitterLoginBtn = (Button) findViewById(R.id.twitter_login_button);

        mFbLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> permissions = Arrays.asList("user_birthday", "user_location", "user_friends", "email", "public_profile");

                ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
        });


        mTwitterLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseTwitterUtils.logIn(MainActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Twitter!");
                        } else {
                            Log.d("MyApp", "User logged in through Twitter!");
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
        });



        mVideoBg = (VideoView) findViewById(R.id.vvBgVideo);
        mVideoBg.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video));

    }

    @Override
    protected void onStop() {
        super.onStop();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(MainActivity.this);

        // initilize video
        mVideoBg.start();
        mVideoBg.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoBg.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(MainActivity.this);

        // stop the video
        mVideoBg.stopPlayback();
    }


}
