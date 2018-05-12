package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.domain.usecases.ObtainCoordinates
import org.firezenk.foodieapp.domain.usecases.ObtainVenues
import org.firezenk.foodieapp.ui.features.commons.Action
import javax.inject.Inject

class MapActions @Inject constructor(private val obtainVenues: ObtainVenues,
                                     private val obtainCoordinates: ObtainCoordinates) {

    fun loadVenues() = LoadVenues(obtainVenues, obtainCoordinates)

    sealed class MapAction : Action() {

        class LoadVenues(val obtainVenues: ObtainVenues, val obtainCoordinates: ObtainCoordinates)
            : MapAction()
    }
}

typealias Actions = MapActions.MapAction
typealias LoadVenues = MapActions.MapAction.LoadVenues