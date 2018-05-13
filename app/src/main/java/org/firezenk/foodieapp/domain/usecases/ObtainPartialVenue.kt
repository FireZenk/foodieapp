package org.firezenk.foodieapp.domain.usecases

import org.firezenk.foodieapp.domain.repositories.VenuesRepository
import javax.inject.Inject

class ObtainPartialVenue @Inject constructor(private val repository: VenuesRepository) {

    fun execute(venueId: String) = repository.findVenue(venueId)
}