/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 4:52 PM
 */

package com.ferdyhaspin.githubuserapp.di.module

import android.content.Context
import androidx.room.Room
import com.ferdyhaspin.githubuserapp.data.local.AppDatabase
import com.ferdyhaspin.githubuserapp.data.local.DB_NAME
import com.ferdyhaspin.githubuserapp.data.local.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}
