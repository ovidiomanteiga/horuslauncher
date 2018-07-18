
package org.paradaise.horussense.launcher.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsSendStatsInteractor
import org.paradaise.horussense.launcher.domain.SendStatsInteractor


class SendStatsReceiver : BroadcastReceiver(), NeedsSendStatsInteractor {

	override lateinit var sendStatsInteractor: SendStatsInteractor

	override fun onReceive(context: Context, intent: Intent) {
		AsyncTask.execute {
			MainInjector.inject(this, context)
			this.sendStatsInteractor.perform()
		}
	}

}
