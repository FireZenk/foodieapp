package org.firezenk.foodieapp

import android.app.Application
import org.firezenk.foodieapp.di.AppComponent
import org.firezenk.foodieapp.di.DaggerAppComponent
import org.firezenk.foodieapp.di.module.AppModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun updateComponent(updateComponent: AppComponent) {
        component = updateComponent
    }
}