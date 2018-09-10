
package org.paradaise.horussense.launcher.infrastructure.action


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import org.paradaise.horussense.launcher.domain.action.HorusAction
import org.paradaise.horussense.launcher.domain.action.LocalizableString



class OpenAppAction: HorusAction {

	constructor(icon: Drawable?, intent: Intent?, name: LocalizableString): super(icon, name) {
		this.intent = intent
	}


	var context: Context? = null
	var intent: Intent?


	override var url: String? = null
		get() = this.intent?.toUri(0)


	override fun perform() {
		super.perform()
		this.intent?.let {
			this.launch(it)
		}
	}


	private fun launch(it: Intent) {
		Handler(Looper.getMainLooper()).post {
			this.context?.startActivity(it)
		}
	}

}
