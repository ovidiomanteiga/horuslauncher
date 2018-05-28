
package org.paradaise.horussense.launcher.infrastructure


import android.content.Intent
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.domain.LocalizableString



class App: HorusAction {

	constructor(name: LocalizableString, intent: Intent): super(name) {
		this.name = name
		this.intent = intent
	}


	private var intent: Intent

}
