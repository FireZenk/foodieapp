package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.ui.features.commons.State
import javax.inject.Inject

class MapStates @Inject constructor() {

    sealed class MapState : State() {
        class PositionReady(val lat: Double, val lng: Double) : MapState()
        class PointersReady(val venues: List<Venue>) : MapState()
        class VenueReady(val venue: Venue) : MapState()
        class ReservationChanged(val reserved: Boolean) : MapState()
        class ErrorMessage(val e: Throwable) : MapState()
    }
}

typealias States = MapStates.MapState
typealias PositionReady = MapStates.MapState.PositionReady
typealias PointersReady = MapStates.MapState.PointersReady
typealias VenueReady = MapStates.MapState.VenueReady
typealias ReservationChanged = MapStates.MapState.ReservationChanged
typealias ErrorMessage = MapStates.MapState.ErrorMessage