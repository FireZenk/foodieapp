package org.firezenk.foodieapp.data.db.venues

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = VenueDao.VENUE_TABLE)
data class VenueEntity(@PrimaryKey val id: String,
                       @ColumnInfo(name = "name") val name: String,
                       @ColumnInfo(name = "reserved") val reserved: Boolean = false,
                       @Embedded(prefix = "location_") val location: LocationEntity,
                       @Embedded(prefix = "extras_") val extras: ExtrasEntity?)

data class LocationEntity(@ColumnInfo(name = "address") val address: String?,
                          @ColumnInfo(name = "crossStreet") val crossStreet: String?,
                          @ColumnInfo(name = "city") val city: String?,
                          @ColumnInfo(name = "state") val state: String?,
                          @ColumnInfo(name = "postalCode") val postalCode: String?,
                          @ColumnInfo(name = "country") val country: String?,
                          @ColumnInfo(name = "lat") val lat: Double,
                          @ColumnInfo(name = "lng") val lng: Double,
                          @ColumnInfo(name = "distance") val distance: Float?)

data class ExtrasEntity(@Embedded(prefix = "contact_") val contact: ExtrasContactEntity?,
                        @Embedded(prefix = "likes_") val likes: ExtrasLikesEntity?,
                        @ColumnInfo(name = "rating") val rating: Float,
                        @Embedded(prefix = "bestPhoto_") val bestPhoto: ExtrasPhotoEntity?, val url: String?)

data class ExtrasContactEntity(@ColumnInfo(name = "formattedPhone") val formattedPhone: String?)

data class ExtrasLikesEntity(@ColumnInfo(name = "summary") val summary: String?)

data class ExtrasPhotoEntity(@ColumnInfo(name = "url") val url: String?)