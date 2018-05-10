package org.firezenk.foodieapp.di.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun getGlide(): RequestManager = Glide.with(context)

    @Provides
    fun getContext(): Context = context
}