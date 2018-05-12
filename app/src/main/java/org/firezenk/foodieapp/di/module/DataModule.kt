package org.firezenk.foodieapp.di.module

import android.arch.persistence.room.BuildConfig
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import org.firezenk.foodieapp.data.db.AppDatabase
import org.firezenk.foodieapp.data.db.venues.VenueDao
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        const val DATABASE_NAME: String = "database"
    }

    @Provides
    @Singleton
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME)
                .apply {
                    if (BuildConfig.DEBUG) fallbackToDestructiveMigration()
                }
                .build()
    }

    @Provides
    @Singleton
    fun getVenueDao(appDatabase: AppDatabase): VenueDao = appDatabase.venueDao()
}