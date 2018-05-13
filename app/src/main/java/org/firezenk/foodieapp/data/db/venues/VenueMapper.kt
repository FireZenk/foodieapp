package org.firezenk.foodieapp.data.db.venues

import org.firezenk.foodieapp.domain.models.*
import javax.inject.Inject

class VenueMapper @Inject constructor() {

    fun mapVenueEntity(it: Venue): VenueEntity {
        return VenueEntity(it.id, it.name, it.reserved, LocationEntity(it.location.address,
                it.location.crossStreet, it.location.city, it.location.state,
                it.location.postalCode, it.location.country, it.location.lat, it.location.lng,
                it.location.distance), mapExtrasEntity(it))
    }

    private fun mapExtrasEntity(venue: Venue): ExtrasEntity? {
        return venue.extras?.let {
            ExtrasEntity(
                    ExtrasContactEntity(it.contact?.formattedPhone),
                    ExtrasLikesEntity(it.likes?.summary),
                    it.rating,
                    ExtrasPhotoEntity(it.url),
                    it.url)
        }
    }

    fun mapVenue(it: VenueEntity): Venue {
        return Venue(it.id, it.name, Location(it.location.address, it.location.crossStreet,
                it.location.city, it.location.state, it.location.postalCode, it.location.country,
                it.location.lat, it.location.lng, it.location.distance), it.reserved, mapExtras(it))
    }

    private fun mapExtras(it: VenueEntity): Extras? {
        return it.extras?.let {
            Extras(
                    ExtrasContact(it.contact?.formattedPhone),
                    ExtrasLikes(it.likes?.summary),
                    it.rating,
                    ExtrasPhoto(it.bestPhoto?.url),
                    it.url
            )
        }
    }
}