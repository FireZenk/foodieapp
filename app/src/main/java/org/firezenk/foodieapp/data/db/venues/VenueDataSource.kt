package org.firezenk.foodieapp.data.db.venues

import io.reactivex.Single
import org.firezenk.foodieapp.domain.models.*
import javax.inject.Inject

class VenueDataSource @Inject constructor(private val venueDao: VenueDao,
                                          private val mapper: VenueMapper) {

    fun addAll(venues: List<Venue>): List<Venue> {
        venues.forEach {
            venueDao.insert(mapper.mapVenueEntity(it))
        }
        return venues
    }

    fun findVenue(venueName: String): Single<Venue>
            = venueDao.findVenue(venueName).map { mapper.mapVenue(it) }

    fun updateVenue(venue: Venue): Venue {
        venueDao.insert(mapper.mapVenueEntity(venue))
        return venue
    }

    fun makeReservation(venueId: String) = venueDao.makeReservation(venueId)

    fun cancelReservation(venueId: String) = venueDao.cancelReservation(venueId)
}