
package org.paradaise.horussense.launcher.domain

import java.util.*


open class PredictedHorusListItem: HorusListItem {

	constructor(action: HorusAction, lastExecutionMoment: Date?,
	            numberOfExecutionsLastWeek: Int):
			super(action, lastExecutionMoment, numberOfExecutionsLastWeek)

	val horusAction: HorusAction
		get() = super.action as HorusAction

}
