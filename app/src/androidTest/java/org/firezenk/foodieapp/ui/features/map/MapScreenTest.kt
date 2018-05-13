package org.firezenk.foodieapp.ui.features.map

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import io.reactivex.Flowable
import io.reactivex.Single
import org.firezenk.foodieapp.R
import org.firezenk.foodieapp.config.ProjectTestRule
import org.firezenk.foodieapp.domain.models.Coordinates
import org.firezenk.foodieapp.domain.models.Location
import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.domain.repositories.CoordinatesRepository
import org.firezenk.foodieapp.domain.repositories.VenuesRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapScreenTest {

    @get:Rule
    var rule = ProjectTestRule<MapScreen>(MapScreen::class.java, this)

    private val coordinatesRepository: CoordinatesRepository = mock()
    private val venuesRepository: VenuesRepository = mock()

    @Before
    fun setup() {
        val lat = 43.0
        val lng = 1.0

        given(coordinatesRepository.subscribeForCoordinates())
                .willReturn(Flowable.just(Coordinates(lat, lng)))

        given(venuesRepository.findNearbyVenues(lat, lng))
                .willReturn(Single.just(listOf(singleVenue())))
    }

    @Test
    fun onAppLaunch_ShowsCurrentPosition() {
        rule.launchActivity()

        assertDisplayed(R.string.app_name)
    }

    private fun singleVenue(): Venue = Venue("111", "MacDonald's",
            Location("Avinguda de Rio de Janeiro, 42", null, "Barcelona",
                    "Catalonia", "08016", "Spain", 41.425003,
                    2.1658096, 1f), false, null)
}