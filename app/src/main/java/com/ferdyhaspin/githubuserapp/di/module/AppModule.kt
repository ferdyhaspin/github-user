/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 4:33 PM
 */

package com.ferdyhaspin.githubuserapp.di.module

import android.app.Application
import android.content.Context
import android.location.Geocoder
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ferdyhaspin.githubuserapp.data.remote.config.ServiceGenerator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(context: Application): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    fun provideLocale(): Locale {
        return Locale.getDefault()
    }

    @Provides
    @Singleton
    fun provideGeocoder(context: Context, locale: Locale): Geocoder {
        return Geocoder(context, locale)
    }

    @Provides
    @Singleton
    fun provideServiceGenerator(context: Context, gson: Gson): ServiceGenerator {
        return ServiceGenerator(context, gson)
    }


    @Provides
    @Singleton
    fun provideRecyclerDivider(context: Context): DividerItemDecoration {
        return DividerItemDecoration(context, RecyclerView.VERTICAL)
    }

}
