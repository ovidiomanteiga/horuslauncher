
package org.paradaise.horussense.launcher.domain

import java.util.*


typealias PromotedHorusList= List<PromotedHorusListItem>


open class PromotedHorusListItem(action: Action,
                                 lastExecutionMoment: Date?,
                                 numberOfExecutionsLastWeek: Int) :
		HorusListItem(action, lastExecutionMoment, numberOfExecutionsLastWeek)
