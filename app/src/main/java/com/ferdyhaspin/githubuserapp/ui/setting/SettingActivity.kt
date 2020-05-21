package com.ferdyhaspin.githubuserapp.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ferdyhaspin.githubuserapp.R
import kotlinx.android.synthetic.main.toolbar.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
        initSetting()
    }

    private fun initView() {
        toolbar.title = getString(R.string.settings_reminder)
        setSupportActionBar(toolbar)
    }

    private fun initSetting() {
        supportFragmentManager.commit {
            add(R.id.setting_holder, PreferencesFragment())
        }
    }
}
