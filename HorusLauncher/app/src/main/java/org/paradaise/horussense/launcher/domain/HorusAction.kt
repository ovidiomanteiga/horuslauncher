
package org.paradaise.horussense.launcher.domain



open class HorusAction {

	var name: LocalizableString? = null


	constructor(name: LocalizableString? = null) {
		this.name = name
	}

}



typealias LocalizableString = String
