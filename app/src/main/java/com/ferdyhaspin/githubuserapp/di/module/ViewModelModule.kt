/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 4:33 PM
 */

package com.ferdyhaspin.githubuserapp.di.module

import androidx.lifecycle.ViewModel
import com.ferdyhaspin.githubuserapp.di.scope.ViewModelKey
import com.ferdyhaspin.githubuserapp.ui.detail.DetailViewModel
import com.ferdyhaspin.githubuserapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}
