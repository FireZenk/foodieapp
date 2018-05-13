package org.firezenk.foodieapp.data.net.venues

import io.reactivex.Observable
import io.reactivex.Single
import org.firezenk.foodieapp.data.net.FoursquareApi
import org.firezenk.foodieapp.domain.models.*
import javax.inject.Inject

class VenueDataSource @Inject constructor(private val foursquareApi: FoursquareApi,
                                          private val mapper: VenueMapper) {

    fun findNearbyVenues(lat: Double, lng: Double): Observable<List<Venue>>
            = foursquareApi.venueSearch("$lat,$lng")
            .map { it.response.venues.map { mapper.mapPartialVenue(it) } }

    fun getVenue(venueId: String): Single<Venue>
            = foursquareApi.venue(venueId)
            .map { mapper.mapFullVenue(it.response.venue) }
}