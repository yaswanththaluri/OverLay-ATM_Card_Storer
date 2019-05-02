package com.audigosolutions.android.outlay;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataHelper extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name("MyData.realm").build();

        Realm.setDefaultConfiguration(configuration);
    }
}
