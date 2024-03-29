
package org.paradaise.horussense.launcher.domain.horus_list

import android.graphics.drawable.Drawable
import org.paradaise.horussense.launcher.domain.action.Action
import java.util.*


typealias HorusList = List<HorusListItem>


abstract class HorusListItem {

	constructor(action: Action, lastExecutionMoment: Date?,
	            numberOfExecutionsLastWeek: Int)
	{
		this.action = action
		this.lastExecutionMoment = lastExecutionMoment
		this.numberOfExecutionsLastWeek = numberOfExecutionsLastWeek
	}

	open val action: Action

	open val icon: Drawable?
		get() = this.action.icon

	open val name: String?
		get() = this.action.name

	open val lastExecutionMoment: Date?

	open val numberOfExecutionsLastWeek: Int

}