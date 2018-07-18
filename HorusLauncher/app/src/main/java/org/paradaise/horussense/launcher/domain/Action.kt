package org.paradaise.horussense.launcher.domain

import android.graphics.drawable.Drawable


open class Action {

	constructor(icon: Drawable? = null, name: LocalizableString? = null) {
		this.icon = icon
		this.name = name
	}


	var icon: Drawable? = null
	open var name: LocalizableString? = null
	open var url: String? = null


	open fun perform() { }

}


typealias LocalizableString = String
