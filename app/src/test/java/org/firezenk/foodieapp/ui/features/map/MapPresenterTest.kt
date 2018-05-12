package org.firezenk.foodieapp.ui.features.map

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Single
import org.firezenk.foodieapp.configureRxThreading
import org.firezenk.foodieapp.domain.models.Coordinates
import org.firezenk.foodieapp.domain.models.Location
import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.domain.repositories.CoordinatesRepository
import org.firezenk.foodieapp.domain.repositories.VenuesRepository
import org.firezenk.foodieapp.domain.usecases.CancelReservation
import org.firezenk.foodieapp.domain.usecases.MakeReservation
import org.firezenk.foodieapp.domain.usecases.ObtainCoordinates
import org.firezenk.foodieapp.domain.usecases.ObtainVenue
import org.firezenk.foodieapp.domain.usecases.ObtainVenues
import org.junit.Assert
import org.junit.Before
import org.junit.Test
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

    @Test
    fun onMapReady_putsUserAndVenuePointers() {
        given(coordinatesRepository.subscribeForCoordinates())
                .willReturn(Flowable.just(Coordinates(43.0, 1.0)))

        given(venuesRepository.findNearbyVenues(43.0, 1.0))
                .willReturn(Single.just(listOf(Venue("", "",
                        Location("", "", "", "", "",
                                "", 43.0, 1.0, 0f), false))))

        presenter reduce actions.loadVenues()

        verify(screen, times(2)).render(capture(captor))

        with(captor.allValues) {
            Assert.assertTrue(get(0) is PositionReady)
            Assert.assertTrue(get(1) is PointersReady)
        }
    }
}