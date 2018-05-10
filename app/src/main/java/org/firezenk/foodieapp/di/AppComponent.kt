package org.firezenk.foodieapp.di

import dagger.Component
import org.firezenk.foodieapp.di.module.AppModule
import org.firezenk.foodieapp.ui.features.map.MapScreen
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    infix fun inject(mapScreen: MapScreen)
}