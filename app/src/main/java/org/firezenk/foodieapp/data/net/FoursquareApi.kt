package org.firezenk.foodieapp.data.net

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {

    @GET("venues/search")
    fun venueSearch(@Query("ll") coordinates: String,
                    @Query("categoryId") pageNumber: String = "4d4b7105d754a06374d81259")
            : Single<FoursquareResponse>
}