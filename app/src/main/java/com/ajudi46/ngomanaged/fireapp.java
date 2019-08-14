package com.ajudi46.ngomanaged;
import android.app.Application;
import com.firebase.client.Firebase;

public class fireapp extends Application {

    public void OnCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
