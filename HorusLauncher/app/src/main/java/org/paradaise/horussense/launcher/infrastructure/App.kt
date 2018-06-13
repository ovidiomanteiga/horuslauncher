
package org.paradaise.horussense.launcher.infrastructure


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.domain.LocalizableString



class App: HorusAction {

	constructor(icon: Drawable, intent: Intent?, name: LocalizableString): super(icon, name) {
		this.intent = intent
	}


	var context: Context? = null
	var intent: Intent?


	override fun perform() {
		super.perform()
		this.intent?.let {
			this.context?.startActivity(it)
		}
	}

}
