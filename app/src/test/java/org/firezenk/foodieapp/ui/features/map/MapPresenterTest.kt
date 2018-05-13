package org.firezenk.foodieapp.ui.features.map

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
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
import org.junit.Assert.assertTrue
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
        val lat = 43.0
        val lng = 1.0

        given(coordinatesRepository.subscribeForCoordinates())
                .willReturn(Flowable.just(Coordinates(lat, lng)))

        given(venuesRepository.findNearbyVenues(lat, lng))
                .willReturn(Single.just(listOf(singleVenue())))

        presenter reduce actions.loadVenues()

        verify(screen, times(2)).render(capture(captor))

        with(captor.allValues) {
            assertTrue(get(0) is PositionReady)
            assertTrue(get(1) is PointersReady)
        }
    }

    @Test
    fun onVenueClick_detailsWillOpen() {
        val venue = singleVenue()
        given(venuesRepository.findVenue(venue.name))
                .willReturn(Single.just(venue))

        presenter reduce actions.openVenue(venue.name)

        verify(screen, times(1)).render(capture(captor))

        with(captor.allValues) {
            assertTrue(get(0) is VenueReady)
            assertTrue((get(0) as VenueReady).venue.name == venue.name)
        }
    }

    @Test
    fun onMakeReservation_theReservationStateWillChange() {
        val venue = singleVenue()
        given(venuesRepository.makeReservation(venue.name)).willReturn(Completable.complete())

        presenter reduce actions.makeReservation(venue.name)

        verify(screen, times(1)).render(capture(captor))

        with(captor.allValues) {
            assertTrue(get(0) is ReservationChanged)
            assertTrue((get(0) as ReservationChanged).reserved)
        }
    }

    private fun singleVenue(): Venue = Venue("111", "MacDonald's",
            Location("Avinguda de Rio de Janeiro, 42", null, "Barcelona",
                    "Catalonia", "08016", "Spain", 41.425003,
                    2.1658096, 1f), false)
}