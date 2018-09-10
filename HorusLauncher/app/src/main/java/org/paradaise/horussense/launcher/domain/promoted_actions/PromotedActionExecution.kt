
package org.paradaise.horussense.launcher.domain.promoted_actions

import org.paradaise.horussense.launcher.domain.action.PromotedAction
import java.util.*


open class PromotedActionExecution {

	constructor(action: PromotedAction?) {
		this.action = action
		this.executionTime = Calendar.getInstance().time
	}

	open var action: PromotedAction?
	open var executionTime: Date

}
