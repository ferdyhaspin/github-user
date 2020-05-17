package com.ferdyhaspin

import com.ferdyhaspin.githubuserapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by ferdyhaspin  on 18/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().app(this).build()
    }
}