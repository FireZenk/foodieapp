package org.firezenk.foodieapp.domain.usecases

import io.reactivex.Single
import org.firezenk.foodieapp.domain.models.Venue
import org.firezenk.foodieapp.domain.repositories.VenuesRepository
import javax.inject.Inject

class ObtainVenuesUseCase @Inject constructor(private val repository: VenuesRepository) {

    fun execute(lat: Double, lng: Double): Single<List<Venue>>
            = repository.findNearbyVenues(lat, lng)
}