package org.firezenk.foodieapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import org.firezenk.foodieapp.data.db.venues.VenueDao
import org.firezenk.foodieapp.data.db.venues.VenueEntity

@Database(entities = [VenueEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao
}