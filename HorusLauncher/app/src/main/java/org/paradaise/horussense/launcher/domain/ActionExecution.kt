
package org.paradaise.horussense.launcher.domain

import java.util.*


open class ActionExecution {

	constructor(action: HorusAction, moment: Date) {
		this.action = action
		this.moment = moment
	}

	var action: HorusAction
	var moment: Date

}
