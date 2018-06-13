
package org.paradaise.horussense.launcher.domain

import java.util.*


open class ActionExecution {

	constructor(action: HorusAction) {
		this.action = action
	}

	var action: HorusAction
	var deviceIdentifier: String? = null
	var moment: Date? = null
	var userIdentifier: String? = null

}
