package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.ui.features.commons.Action
import javax.inject.Inject

class MapActions @Inject constructor() {

    sealed class MapAction : Action() {

    }
}

typealias Actions = MapActions.MapAction