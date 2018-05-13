package org.firezenk.foodieapp.domain.models

data class Venue(val id: String, val name: String, val location: Location,
                 val reserved: Boolean, val extras: Extras?)

data class Location(val address: String?, val crossStreet: String?, val city: String?,
                    val state: String?, val postalCode: String?, val country: String?,
                    val lat: Double, val lng: Double, val distance: Float?)

data class Extras(val contact: ExtrasContact?, val likes: ExtrasLikes?, val rating: Float,
                  val bestPhoto: ExtrasPhoto?, val url: String?)

data class ExtrasContact(val formattedPhone: String?)

data class ExtrasLikes(val summary: String?)

data class ExtrasPhoto(val url: String?)