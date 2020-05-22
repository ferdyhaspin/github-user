package com.ferdyhaspin.githubuserapp.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.reminder.NotificationReceiver

/**
 * Created by ferdyhaspin on 21/05/20.
 * Copyright (c) 2020 Github User Apps All rights reserved.
 */

class PreferencesFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var applicationContext: Context
    private lateinit var notificationReceiver: NotificationReceiver
    private lateinit var isActivateMuPreference: CheckBoxPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isActivateMuPreference.isChecked = sh.getBoolean(getString(R.string.reminder), false)
    }

    private fun init() {
        applicationContext = requireContext().applicationContext
        notificationReceiver = NotificationReceiver()
        isActivateMuPreference =
            findPreference<CheckBoxPreference>(getString(R.string.reminder)) as CheckBoxPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == getString(R.string.reminder)) {
            val isReminder = sharedPreferences.getBoolean(getString(R.string.reminder), false)
            isActivateMuPreference.isChecked = isReminder
            if (isReminder) {
                notificationReceiver.setDailyReminder(applicationContext)
            } else {
                notificationReceiver.cancelReminder(applicationContext)
            }

        }
    }
}