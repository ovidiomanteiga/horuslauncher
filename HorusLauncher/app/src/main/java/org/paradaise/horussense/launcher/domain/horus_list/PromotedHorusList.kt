
package org.paradaise.horussense.launcher.domain.horus_list

import org.paradaise.horussense.launcher.domain.action.Action
import java.util.*


typealias PromotedHorusList = List<PromotedHorusListItem>


open class PromotedHorusListItem(action: Action,
                                 lastExecutionMoment: Date?,
                                 numberOfExecutionsLastWeek: Int) :
		HorusListItem(action, lastExecutionMoment, numberOfExecutionsLastWeek)
