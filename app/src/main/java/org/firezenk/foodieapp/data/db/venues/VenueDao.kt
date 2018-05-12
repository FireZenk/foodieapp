package org.firezenk.foodieapp.data.db.venues

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Single
import org.firezenk.foodieapp.data.db.BaseDao
import org.intellij.lang.annotations.Language

@Dao
interface VenueDao: BaseDao<VenueEntity> {

    companion object {
        const val VENUE_TABLE = "venues"
    }

    @Language("RoomSql")
    @Query("SELECT * FROM $VENUE_TABLE WHERE name = :venueName LIMIT 1")
    fun findVenue(venueName: String): Single<VenueEntity>
}