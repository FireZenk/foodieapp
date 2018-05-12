package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.ui.features.commons.State
import javax.inject.Inject

class MapStates @Inject constructor() {

    sealed class MapState : State() {

    }
}

typealias States = MapStates.MapState