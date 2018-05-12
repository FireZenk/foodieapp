package org.firezenk.foodieapp.di

import dagger.Component
import org.firezenk.foodieapp.di.module.AppModule
import org.firezenk.foodieapp.di.module.DataModule
import org.firezenk.foodieapp.di.module.NetworkModule
import org.firezenk.foodieapp.ui.features.map.di.MapComponent
import org.firezenk.foodieapp.ui.features.map.di.MapModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataModule::class])
interface AppComponent {

    infix fun add(mapModule: MapModule): MapComponent
}