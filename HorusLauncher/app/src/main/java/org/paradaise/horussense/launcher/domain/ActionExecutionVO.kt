
package org.paradaise.horussense.launcher.domain

import java.util.*


open class ActionExecutionVO {

	constructor(action: HorusAction, moment: Date) {
		this.action = action
		this.moment = moment
	}

	open var action: HorusAction
	open var moment: Date

}
