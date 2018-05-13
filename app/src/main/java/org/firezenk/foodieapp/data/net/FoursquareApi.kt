package org.firezenk.foodieapp.data.net

import io.reactivex.Observable
import org.firezenk.foodieapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {

    @GET("venues/search")
    fun venueSearch(@Query("ll") coordinates: String,
                    @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
                    @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
                    @Query("v") version: String = "20180512",
                    @Query("categoryId") pageNumber: String = "4d4b7105d754a06374d81259")
            : Observable<FoursquareResponse>
}