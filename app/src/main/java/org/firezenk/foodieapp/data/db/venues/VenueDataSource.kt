package org.firezenk.foodieapp.data.db.venues

import io.reactivex.Single
import org.firezenk.foodieapp.domain.models.Location
import org.firezenk.foodieapp.domain.models.Venue
import javax.inject.Inject

class VenueDataSource @Inject constructor() {

    fun findVenue(venueName: String): Single<Venue> {
        return Single.just(Venue("", "",
                Location("", "", "", "", "",
                        "",23424.453, 345.534, 1000f)))
    }
}