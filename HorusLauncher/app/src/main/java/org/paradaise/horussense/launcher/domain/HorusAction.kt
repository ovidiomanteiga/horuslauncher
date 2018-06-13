
package org.paradaise.horussense.launcher.domain


import android.graphics.drawable.Drawable
import java.util.*



open class HorusAction {

	var icon: Drawable? = null
	var name: LocalizableString? = null
	var uuid: UUID = UUID.randomUUID()


	constructor(icon: Drawable? = null, name: LocalizableString? = null) {
		this.icon = icon
		this.name = name
	}


	open fun perform() { }

}



typealias LocalizableString = String
