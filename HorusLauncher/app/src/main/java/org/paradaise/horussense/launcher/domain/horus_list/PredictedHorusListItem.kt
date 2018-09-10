
package org.paradaise.horussense.launcher.domain.horus_list

import org.paradaise.horussense.launcher.domain.action.HorusAction
import java.util.*


open class PredictedHorusListItem: HorusListItem {

	constructor(action: HorusAction, lastExecutionMoment: Date?,
	            numberOfExecutionsLastWeek: Int):
			super(action, lastExecutionMoment, numberOfExecutionsLastWeek)

	open val horusAction: HorusAction
		get() = super.action as HorusAction

}
