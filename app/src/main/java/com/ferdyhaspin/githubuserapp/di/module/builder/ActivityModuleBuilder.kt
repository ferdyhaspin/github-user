package com.ferdyhaspin.githubuserapp.di.module.builder

/**
 * Created by ferdyhaspin on 2020-02-18.
 */

import com.ferdyhaspin.githubuserapp.ui.detail.DetailActivity
import com.ferdyhaspin.githubuserapp.ui.favorite.FavoriteActivity
import com.ferdyhaspin.githubuserapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoriteActivity(): FavoriteActivity

}
