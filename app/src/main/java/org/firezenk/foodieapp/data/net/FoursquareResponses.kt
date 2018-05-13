package org.firezenk.foodieapp.data.net

import org.firezenk.foodieapp.data.net.venues.VenueEntity

data class FoursquareResponse(val response: Response)

data class Response(val venues: List<VenueEntity>)

data class FoursquareSingleResponse(val response: SingleResponse)

data class SingleResponse(val venue: VenueEntity)