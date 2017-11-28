package bornbaby.com.daggersample;

import android.app.Activity;
import android.app.Application;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by madhu on 25-Nov-17.
 */

public class MyApplication extends Application implements HasActivityInjector{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }
}
