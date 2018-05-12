package org.firezenk.foodieapp.data.db.venues

import io.reactivex.Completable
import io.reactivex.Single
import org.firezenk.foodieapp.domain.models.Location
import org.firezenk.foodieapp.domain.models.Venue
import javax.inject.Inject

class VenueDataSource @Inject constructor(private val venueDao: VenueDao) {

    fun addAll(venues: List<Venue>): List<Venue> {
        venues.forEach {
            venueDao.insert(mapVenueEntity(it))
        }
        return venues
    }

    fun findVenue(venueName: String): Single<Venue> {
        return venueDao.findVenue(venueName).map { mapVenue(it) }
    }

    fun makeReservation(venueId: String) = venueDao.makeReservation(venueId)

    fun cancelReservation(venueId: String) = venueDao.cancelReservation(venueId)

    private fun mapVenueEntity(it: Venue): VenueEntity {
        return VenueEntity(it.id, it.name, false, LocationEntity(it.location.address,
                it.location.crossStreet, it.location.city, it.location.state,
                it.location.postalCode, it.location.country, it.location.lat, it.location.lng,
                it.location.distance))
    }

    private fun mapVenue(it: VenueEntity): Venue {
        return Venue(it.id, it.name, Location(it.location.address, it.location.crossStreet,
                it.location.city, it.location.state, it.location.postalCode, it.location.country,
                it.location.lat, it.location.lng, it.location.distance))
    }
}