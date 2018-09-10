
package org.paradaise.horussense.launcher.ui.stats

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*


class SendStatsScheduler(val context: Context) {

	fun scheduleIfNeeded() {
		if (!this.isNeeded) return
		val calendar = Calendar.getInstance()
		calendar.timeInMillis = System.currentTimeMillis()
		calendar.set(Calendar.HOUR_OF_DAY, 3)
		this.alarmManager.setInexactRepeating(
				AlarmManager.RTC,
				calendar.timeInMillis,
				AlarmManager.INTERVAL_DAY,
				this.pendingIntent)
	}

	private val alarmManager: AlarmManager
		get() = this.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

	private val intent = Intent(this.context, SendStatsReceiver::class.java)

	private val isNeeded: Boolean
		get() = this.pendingIntentNoCreate == null

	private val pendingIntent: PendingIntent
		get() = PendingIntent.getBroadcast(this.context, 0,
			this.intent, PendingIntent.FLAG_UPDATE_CURRENT)

	private val pendingIntentNoCreate = PendingIntent.getBroadcast(this.context, 0,
			this.intent, PendingIntent.FLAG_NO_CREATE)

}
