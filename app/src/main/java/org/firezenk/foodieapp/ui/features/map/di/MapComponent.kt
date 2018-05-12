package org.firezenk.foodieapp.ui.features.map.di

import dagger.Subcomponent
import org.firezenk.foodieapp.di.ScreenScope
import org.firezenk.foodieapp.ui.features.map.MapScreen

@ScreenScope
@Subcomponent(modules = [MapModule::class])
interface MapComponent {

    infix fun inject(mapScreen: MapScreen)
}