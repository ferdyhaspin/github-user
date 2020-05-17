package com.ferdyhaspin.githubuserapp.util.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

//fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, block: (T) -> Unit) {
//    liveData.observe(this, Observer { event -> event?.process(block) })
//}

//fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, block: (T) -> Unit) {
//    observe(owner, Observer { event -> event?.process(block) })
//}

fun <T> LiveData<T>.post(data: T) = (this as MutableLiveData<T>).postValue(data)

//fun <T> LiveData<Event<T>>.postEvent(data: T) = this.post(
//    Event(data)
//)

//fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }