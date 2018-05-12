package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.domain.usecases.ObtainCoordinates
import org.firezenk.foodieapp.domain.usecases.ObtainVenue
import org.firezenk.foodieapp.domain.usecases.ObtainVenues
import org.firezenk.foodieapp.ui.features.commons.Action
import javax.inject.Inject
import org.firezenk.foodieapp.domain.usecases.CancelReservation as CancelReservationUC
import org.firezenk.foodieapp.domain.usecases.MakeReservation as MakeReservationUC

class MapActions @Inject constructor(private val obtainVenues: ObtainVenues,
                                     private val obtainCoordinates: ObtainCoordinates,
                                     private val obtainVenue: ObtainVenue,
                                     private val makeReservation: MakeReservationUC,
                                     private val cancelReservation: CancelReservationUC) {

    fun loadVenues() = LoadVenues(obtainVenues, obtainCoordinates)
    fun openVenue(venueName: String) = OpenVenueDetail(obtainVenue, venueName)
    fun makeReservation(id: String) = MakeReservation(makeReservation, id)
    fun cancelReservation(id: String) = CancelReservation(cancelReservation, id)

    sealed class MapAction : Action() {

        class LoadVenues(val obtainVenues: ObtainVenues, val obtainCoordinates: ObtainCoordinates)
            : MapAction()

        class OpenVenueDetail(val obtainVenue: ObtainVenue, val venueName: String) : MapAction()

        class MakeReservation(val makeReservation: MakeReservationUC, val id: String) : MapAction()
        class CancelReservation(val cancelReservation: CancelReservationUC, val id: String) : MapAction()
    }
}

typealias Actions = MapActions.MapAction
typealias LoadVenues = MapActions.MapAction.LoadVenues
typealias OpenVenueDetail = MapActions.MapAction.OpenVenueDetail
typealias MakeReservation = MapActions.MapAction.MakeReservation
typealias CancelReservation = MapActions.MapAction.CancelReservation