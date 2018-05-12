package org.firezenk.foodieapp.domain.repositories

import io.reactivex.Single
import org.firezenk.foodieapp.data.net.venues.VenueDataSource
import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.domain.subscribeOnIO
import javax.inject.Inject

class VenuesRepository @Inject constructor(private val dataSource: VenueDataSource) {

    fun findNearbyVenues(lat: Double, lng: Double): Single<List<Venue>>
            = dataSource.findNearbyVenues(lat, lng).subscribeOnIO()
}