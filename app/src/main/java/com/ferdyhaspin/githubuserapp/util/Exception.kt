/*
 * Created by Ferdi Haspi Nur Imanulloh on 2/4/20 5:36 PM
 */

package com.ferdyhaspin.githubuserapp.util

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)