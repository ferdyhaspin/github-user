/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 4:31 PM
 */

package com.ferdyhaspin.githubuserapp.di.module.builder

import com.ferdyhaspin.githubuserapp.ui.detail.DetailTabFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModuleBuilder {

    @ContributesAndroidInjector
    abstract fun contributeDetailTabFragment(): DetailTabFragment
}