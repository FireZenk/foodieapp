package org.firezenk.foodieapp.domain.repositories

import org.firezenk.foodieapp.data.net.venues.VenueDataSource
import org.firezenk.foodieapp.domain.subscribeOnIO
import javax.inject.Inject

class VenuesRepository @Inject constructor(private val dataSource: VenueDataSource) {

    fun findNearbyVenues(lat: Double, lng: Double)
            = dataSource.findNearbyVenues(lat, lng).subscribeOnIO()
}