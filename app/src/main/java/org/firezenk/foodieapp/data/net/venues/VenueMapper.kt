package org.firezenk.foodieapp.data.net.venues

import org.firezenk.foodieapp.domain.models.*
import javax.inject.Inject

class VenueMapper @Inject constructor() {

    fun mapPartialVenue(it: VenueEntity): Venue {
        return Venue(it.id, it.name,
                Location(it.location.address, it.location.crossStreet, it.location.city,
                        it.location.state, it.location.postalCode, it.location.country,
                        it.location.lat, it.location.lng, it.location.distance), false, null)
    }

    fun mapFullVenue(it: VenueEntity): Venue {
        return mapPartialVenue(it).copy(
                extras = mapExtras(it)
        )
    }

    private fun mapExtras(it: VenueEntity): Extras {
        return Extras(
                ExtrasContact(it.contact?.formattedPhone),
                ExtrasLikes(it.likes?.summary),
                it.rating,
                mapPhoto(it.bestPhoto),
                it.url)
    }

    private fun mapPhoto(it: PhotoEntity?): ExtrasPhoto? {
        return it?.let { ExtrasPhoto("${it.prefix}${it.width}x${it.height}${it.suffix}") }
    }
}