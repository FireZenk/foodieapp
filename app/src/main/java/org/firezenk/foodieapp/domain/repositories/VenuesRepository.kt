package org.firezenk.foodieapp.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import org.firezenk.foodieapp.data.db.venues.VenueDataSource as Database
import org.firezenk.foodieapp.data.net.venues.VenueDataSource as Network
import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.domain.subscribeOnIO
import javax.inject.Inject

class VenuesRepository @Inject constructor(private val netDataSource: Network,
                                           private val dbDataSource: Database) {

    fun findNearbyVenues(lat: Double, lng: Double): Single<List<Venue>> {
        return netDataSource.findNearbyVenues(lat, lng)
                .flatMapIterable { it -> it }
                .map { replaceReservationValue(it) }
                .toList()
                .map { dbDataSource.addAll(it) }
                .subscribeOnIO()
    }

    fun findVenue(venueId: String): Single<Venue>
            = dbDataSource.findVenue(venueId).subscribeOnIO()

    fun downloadFullVenue(venueId: String): Single<Venue>
            = netDataSource.getVenue(venueId)
            .map { replaceReservationValue(it) }
            .map { dbDataSource.updateVenue(it) }
            .subscribeOnIO()

    fun makeReservation(venueId: String): Completable
            = Completable.fromAction { dbDataSource.makeReservation(venueId) }.subscribeOnIO()

    fun cancelReservation(venueId: String): Completable
            = Completable.fromAction { dbDataSource.cancelReservation(venueId) }.subscribeOnIO()

    private fun replaceReservationValue(it: Venue): Venue {
        return try {
            val dbResult = dbDataSource.findVenue(it.id).blockingGet()
            it.copy(reserved = dbResult.reserved)
        } catch (e: Exception) {
            it
        }
    }
}