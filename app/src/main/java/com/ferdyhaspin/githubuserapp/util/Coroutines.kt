/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/6/20 5:44 PM
 */

package com.ferdyhaspin.githubuserapp.util

import kotlinx.coroutines.*

object Coroutines {

    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun main(job: Job, work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main + job).launch {
            work()
        }

    fun default(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Default).launch {
            work()
        }

    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    fun io(job: Job, work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO + job).launch {
            work()
        }

    fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
        return lazy {
            GlobalScope.async(start = CoroutineStart.LAZY) {
                block.invoke(this)
            }
        }
    }

}