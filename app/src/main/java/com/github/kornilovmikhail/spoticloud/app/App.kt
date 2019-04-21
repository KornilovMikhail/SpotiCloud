package com.github.kornilovmikhail.spoticloud.app

import android.app.Application
import com.github.kornilovmikhail.spoticloud.app.di.component.AppComponent
import com.github.kornilovmikhail.spoticloud.app.di.component.DaggerAppComponent
import com.github.kornilovmikhail.spoticloud.app.di.module.ApplicationModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        component.inject(this)
    }

    companion object {
        lateinit var component: AppComponent
    }
}
