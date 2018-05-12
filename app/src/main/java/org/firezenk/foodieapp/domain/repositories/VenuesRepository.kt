package org.firezenk.foodieapp.domain.repositories

import io.reactivex.Single
import org.firezenk.foodieapp.data.db.venues.VenueDataSource as Database
import org.firezenk.foodieapp.data.net.venues.VenueDataSource as Network
import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.domain.subscribeOnIO
import javax.inject.Inject

class VenuesRepository @Inject constructor(private val netDataSource: Network,
                                           private val dbDataSource: Database) {

    fun findNearbyVenues(lat: Double, lng: Double): Single<List<Venue>>
            = netDataSource.findNearbyVenues(lat, lng)
            .map { dbDataSource.addAll(it) }
            .subscribeOnIO()

    fun findVenue(venueName: String): Single<Venue>
            = dbDataSource.findVenue(venueName).subscribeOnIO()
}