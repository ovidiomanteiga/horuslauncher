
package org.paradaise.horussense.launcher.domain

import android.graphics.drawable.Drawable


open class HorusAction {

	var icon: Drawable? = null
	open var name: LocalizableString? = null
	open var url: String? = null


	constructor(icon: Drawable? = null, name: LocalizableString? = null) {
		this.icon = icon
		this.name = name
	}


	open fun perform() { }

}


typealias LocalizableString = String
