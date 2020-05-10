package com.ferdyhaspin.githubuserapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.ui.main.MainActivity
import com.ferdyhaspin.githubuserapp.util.Coroutines
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Coroutines.main {
            delay(2000L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
