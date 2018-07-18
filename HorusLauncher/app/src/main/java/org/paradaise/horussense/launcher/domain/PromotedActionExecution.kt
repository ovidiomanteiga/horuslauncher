
package org.paradaise.horussense.launcher.domain

import java.util.*


open class PromotedActionExecution {

	constructor(action: PromotedAction?) {
		this.action = action
		this.executionTime = Calendar.getInstance().time
	}

	open var action: PromotedAction?
	open var executionTime: Date

}
