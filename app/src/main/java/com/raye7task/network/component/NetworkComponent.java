package com.raye7task.network.component;

import android.content.Context;

import com.raye7task.MyApplication;
import com.raye7task.network.module.ApplicationModule;
import com.raye7task.network.module.NetworkModule;
import com.raye7task.newsdatastore.NewsRemoteDataStore;

import javax.inject.Singleton;

import dagger.Component;

import static com.raye7task.MyApplication.BASE_URL;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(NewsRemoteDataStore newsDataStore);

    class Initializer {

        private static NetworkComponent component;

        public static NetworkComponent buildComponent() {
            if (component == null) {
                Context context = MyApplication.getContext();
                component = DaggerNetworkComponent.builder()
                        .applicationModule(new ApplicationModule(context))
                        .networkModule(new NetworkModule(BASE_URL))
                        .build();
            }
            return component;
        }
    }
}