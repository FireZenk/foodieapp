package org.firezenk.foodieapp.data.net.venues

data class VenueEntity(val id: String, val name: String, val location: LocationEntity,
                       val contact: ContactEntity?, val url: String?, val likes: LikesEntity?,
                       val rating: Float, val bestPhoto: PhotoEntity?)

data class LocationEntity(val address: String?, val crossStreet: String?, val city: String?,
                          val state: String?, val postalCode: String?, val country: String?,
                          val lat: Double, val lng: Double, val distance: Float?)

data class ContactEntity(val formattedPhone: String?)

data class LikesEntity(val summary: String?)

data class PhotoEntity(val id: String?, val width: Int?, val height: Int?, val prefix: String?,
                       val suffix: String?)