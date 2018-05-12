package org.firezenk.foodieapp.data.net.venues

data class VenueEntity(val id: Int, val name: String, val location: LocationEntity)

data class LocationEntity(val address: String, val crossStreet: String, val city: String,
                          val state: String, val postalCode: String, val country: String,
                          val lat: Double, val lng: Double, val distance: Float)