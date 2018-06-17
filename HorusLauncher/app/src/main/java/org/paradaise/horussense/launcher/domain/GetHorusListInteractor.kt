
package org.paradaise.horussense.launcher.domain

import java.util.*


class GetHorusListInteractor {

	// region Lifecycle

	constructor(repository: ActionExecutionRepository) {
		this.repository = repository
	}

	// endregion
	// region Public Properties

	lateinit var horusList: HorusList

	// endregion
	// region Public Methods

	internal fun perform() {
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
			Pair(it.value.first().action, it.value.count())
		} .sortedByDescending {
			it.second
		} .map {
			it.first
		}
	}

	private fun lastWeekMoment(): Date? {
		val lastWeek = Calendar.getInstance()
		lastWeek.add(Calendar.DATE, -7)
		return lastWeek.time
	}

	// endregion

}


typealias  HorusList = List<HorusAction>
