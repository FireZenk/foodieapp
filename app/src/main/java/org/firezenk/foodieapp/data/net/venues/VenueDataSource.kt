package org.firezenk.foodieapp.data.net.venues

import io.reactivex.Single
import org.firezenk.foodieapp.data.net.FoursquareApi
import org.firezenk.foodieapp.domain.models.Location
import org.firezenk.foodieapp.domain.models.Venue
import javax.inject.Inject

class VenueDataSource @Inject constructor(private val foursquareApi: FoursquareApi) {

    fun findNearbyVenues(lat: Double, lng: Double): Single<List<Venue>>
            = foursquareApi.venueSearch("$lat,$lng")
            .map { it.response.venues.map { mapVenue(it) } }

    private fun mapVenue(it: VenueEntity): Venue {
        return Venue(it.id, it.name,
                Location(it.location.address, it.location.crossStreet, it.location.city,
                        it.location.state, it.location.postalCode, it.location.country,
                        it.location.lat, it.location.lng, it.location.distance))
    }
}