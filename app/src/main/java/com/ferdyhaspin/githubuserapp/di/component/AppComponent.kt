/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 4:28 PM
 */

package com.ferdyhaspin.githubuserapp.di.component

import android.app.Application
import com.ferdyhaspin.App
import com.ferdyhaspin.githubuserapp.di.module.AppModule
import com.ferdyhaspin.githubuserapp.di.module.DataModule
import com.ferdyhaspin.githubuserapp.di.module.ViewModelModule
import com.ferdyhaspin.githubuserapp.di.module.builder.ActivityModuleBuilder
import com.ferdyhaspin.githubuserapp.di.module.builder.FragmentModuleBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        ActivityModuleBuilder::class,
        FragmentModuleBuilder::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }
}