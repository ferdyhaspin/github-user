package com.ferdyhaspin.githubuserapp.reminder

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.ferdyhaspin.githubuserapp.R
import com.ferdyhaspin.githubuserapp.ui.main.MainActivity
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        private const val ID_DAILY_REMINDER = 1000
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "Channel_1"
        const val CHANNEL_NAME = "Daily Reminder Channel"
    }

    override fun onReceive(context: Context, intent: Intent) {
        showDailyReminder(context)
    }

    private fun showDailyReminder(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val uriRingtone =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_github)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.daily_message_reminder))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uriRingtone)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification: Notification = mBuilder.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun setDailyReminder(context: Context) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, ID_DAILY_REMINDER,
            intent,
            0
        )
        val alarmManager = context.getSystemService<AlarmManager>()

        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getReminderTime().timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelReminder(context: Context) {
        val alarmManager = context.getSystemService<AlarmManager>()
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)
        pendingIntent.cancel()
        alarmManager?.cancel(pendingIntent)
    }

    private fun getReminderTime(): Calendar {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 9
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar
    }
}
