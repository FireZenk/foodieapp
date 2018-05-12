package org.firezenk.foodieapp.domain.models

data class Venue(val id: Int, val name: String, val location: Location)

data class Location(val address: String, val crossStreet: String, val city: String,
                    val state: String, val postalCode: String, val country: String,
                    val lat: Double, val lng: Double, val distance: Float)