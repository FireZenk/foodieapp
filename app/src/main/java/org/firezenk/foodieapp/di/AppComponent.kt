package org.firezenk.foodieapp.di

import dagger.Component
import org.firezenk.foodieapp.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

}