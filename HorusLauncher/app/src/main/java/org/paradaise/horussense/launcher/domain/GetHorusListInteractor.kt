
package org.paradaise.horussense.launcher.domain

import android.graphics.drawable.Drawable
import java.util.*


open class GetHorusListInteractor {

	// region Lifecycle

	constructor(repository: ActionExecutionRepository) {
		this.repository = repository
	}

	// endregion
	// region Public Properties

	open lateinit var horusList: HorusList

	// endregion
	// region Public Methods

	open fun perform() {
		val allActionExecutions = this.repository.all
		this.buildHorusList(allActionExecutions)
	}

	// endregion
	// region Private Properties

	private var repository: ActionExecutionRepository

	// endregion
	// region Private Methods

	private fun buildHorusList(executions: List<ActionExecution>) {
		val lastWeekMoment = this.lastWeekMoment()
		this.horusList = executions.filter {
			it.moment.after(lastWeekMoment)
		} .groupBy {
			it.action.url
		} .map {
			HorusListItem(it.value.first().action, it.value.count())
		} .sortedByDescending {
			it.numberOfExecutionsLastWeek
		}
	}

	private fun lastWeekMoment(): Date? {
		val lastWeek = Calendar.getInstance()
		lastWeek.add(Calendar.DATE, -7)
		return lastWeek.time
	}

	// endregion

}


typealias HorusList = List<HorusListItem>


open class HorusListItem(action: HorusAction, numberOfExecutionsLastWeek: Int) {

	open val action = action

	open val icon: Drawable?
		get() = this.action.icon

	open val name: String?
		get() = this.action.name

	open val numberOfExecutionsLastWeek = numberOfExecutionsLastWeek

}
