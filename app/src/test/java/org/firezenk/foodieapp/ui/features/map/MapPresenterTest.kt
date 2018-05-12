package org.firezenk.foodieapp.ui.features.map

import com.nhaarman.mockito_kotlin.mock
import org.firezenk.foodieapp.configureRxThreading
import org.firezenk.foodieapp.domain.repositories.CoordinatesRepository
import org.firezenk.foodieapp.domain.repositories.VenuesRepository
import org.firezenk.foodieapp.domain.usecases.CancelReservation
import org.firezenk.foodieapp.domain.usecases.MakeReservation
import org.firezenk.foodieapp.domain.usecases.ObtainCoordinates
import org.firezenk.foodieapp.domain.usecases.ObtainVenue
import org.firezenk.foodieapp.domain.usecases.ObtainVenues
import org.junit.Before
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.MockitoAnnotations

class MapPresenterTest {

    private lateinit var coordinatesRepository: CoordinatesRepository
    private lateinit var venuesRepository: VenuesRepository
    private lateinit var obtainVenues: ObtainVenues
    private lateinit var obtainCoordinates: ObtainCoordinates
    private lateinit var obtainVenue: ObtainVenue
    private lateinit var makeReservation: MakeReservation
    private lateinit var cancelReservation: CancelReservation

    private lateinit var actions: MapActions
    private lateinit var presenter: MapPresenter
    private lateinit var screen: MapScreen

    @Captor
    private lateinit var captor: ArgumentCaptor<States>

    init {
        configureRxThreading()
    }

    @Before
    fun setup() {
        createMocks()

        obtainVenues = ObtainVenues(venuesRepository)
        obtainCoordinates = ObtainCoordinates(coordinatesRepository)
        obtainVenue = ObtainVenue(venuesRepository)
        makeReservation = MakeReservation(venuesRepository)
        cancelReservation = CancelReservation(venuesRepository)
        actions = MapActions(obtainVenues, obtainCoordinates, obtainVenue, makeReservation,
                cancelReservation)

        presenter = MapPresenter()
        presenter init screen
    }

    private fun createMocks() {
        MockitoAnnotations.initMocks(this)

        coordinatesRepository = mock()
        venuesRepository = mock()
        screen = mock()
    }
}