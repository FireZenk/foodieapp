package org.firezenk.foodieapp.ui.features.map.di

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class MapModule(val activity: Activity) {

    @Provides
    fun providesActivity(): Activity = activity
}