package org.firezenk.foodieapp.ui.features.map

import org.firezenk.foodieapp.domain.observeOnUI
import org.firezenk.foodieapp.ui.extensions.plusAssign
import org.firezenk.foodieapp.ui.features.commons.Presenter
import javax.inject.Inject

class MapPresenter @Inject constructor() : Presenter<Actions, States>() {

    override fun reduce(action: Actions) {
        when (action) {
            is LoadVenues -> getCoordinates(action)
            is OpenVenueDetail -> getVenue(action)
            is MakeReservation -> makeReservation(action)
            is CancelReservation -> cancelReservation(action)
        }
    }

    private fun getCoordinates(action: LoadVenues) {
        disposables += action.obtainCoordinates.execute()
                .observeOnUI()
                .subscribe(
                        {
                            render(PositionReady(it.lat, it.lng))
                            getVenues(action, it.lat, it.lng)
                        },
                        { render(ErrorMessage(it)) })
    }

    private fun getVenues(action: LoadVenues, lat: Double, lng: Double) {
        disposables += action.obtainVenues.execute(lat, lng)
                .observeOnUI()
                .subscribe(
                        { render(PointersReady(it)) },
                        { render(ErrorMessage(it)) })
    }

    private fun getVenue(action: OpenVenueDetail) {
        disposables += action.obtainVenue.execute(action.venueName)
                .observeOnUI()
                .subscribe(
                        { render(VenueReady(it)) },
                        { render(ErrorMessage(it)) })
    }

    private fun makeReservation(action: MakeReservation) {
        disposables += action.makeReservation.execute(action.id)
                .observeOnUI()
                .subscribe(
                        { render(ReservationChanged(true)) },
                        { render(ErrorMessage(it)) })
    }

    private fun cancelReservation(action: CancelReservation) {
        disposables += action.cancelReservation.execute(action.id)
                .observeOnUI()
                .subscribe(
                        { render(ReservationChanged(false)) },
                        { render(ErrorMessage(it)) })
    }
}