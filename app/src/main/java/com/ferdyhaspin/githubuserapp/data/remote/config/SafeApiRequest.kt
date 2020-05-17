/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 5:36 PM
 */

package com.ferdyhaspin.githubuserapp.data.remote.config

import com.ferdyhaspin.githubuserapp.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: run {
                throw ApiException("null body")
            }
        } else {
            val error = response.errorBody().toString()
            val message = StringBuilder()
            message.append("Error Code: ${response.code()}")
            error.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                    message.append("\n")
                } catch (e: JSONException) {
                }
            }

            throw ApiException(message.toString())
        }
    }

}