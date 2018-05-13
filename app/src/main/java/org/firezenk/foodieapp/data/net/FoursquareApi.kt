package org.firezenk.foodieapp.data.net

import io.reactivex.Observable
import io.reactivex.Single
import org.firezenk.foodieapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareApi {

    companion object {
        private val FOOD_CATEGORY_ID = "4d4b7105d754a06374d81259"
        private val VERSION = "20180512"
    }

    @GET("venues/search")
    fun venueSearch(@Query("ll") coordinates: String,
                    @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
                    @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
                    @Query("v") version: String = VERSION,
                    @Query("categoryId") categoryId: String = FOOD_CATEGORY_ID)
            : Observable<FoursquareResponse>

    @GET("venues/{venueId}")
    fun venue(@Path("venueId") venueId: String,
              @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
              @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
              @Query("v") version: String = VERSION)
            : Single<FoursquareSingleResponse>
}