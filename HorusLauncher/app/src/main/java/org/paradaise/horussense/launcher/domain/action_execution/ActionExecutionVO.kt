
package org.paradaise.horussense.launcher.domain.action_execution

import org.paradaise.horussense.launcher.domain.action.HorusAction
import java.util.*


open class ActionExecutionVO {

	constructor(action: HorusAction, moment: Date) {
		this.action = action
		this.moment = moment
	}

	open var action: HorusAction
	open var moment: Date

}
