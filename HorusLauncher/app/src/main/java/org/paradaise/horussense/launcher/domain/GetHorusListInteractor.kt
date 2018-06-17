
package org.paradaise.horussense.launcher.domain

import java.util.*


class GetHorusListInteractor {

	// region Lifecycle

	constructor(repository: ActionExecutionRepository) {
		this.repository = repository
	}

	// endregion
	// region Public Properties

	lateinit var actions: PredictedActions

	// endregion
	// region Public Methods

	internal fun perform() {
		val executions = this.repository.all
		val lastWeek = Calendar.getInstance()
		lastWeek.add(Calendar.DATE, -7)
		val lastWeekMoment = lastWeek.time
		val recentExecutions = executions.filter {
			it.moment.after(lastWeekMoment)
		}
		val grouped = recentExecutions.groupBy { it.action.url }
		val sorted = grouped.map {
			Pair(it.value.first().action, it.value.count())
		} .sortedByDescending { it.second }
		this.actions = sorted.map { it.first }
	}

	// endregion
	// region Private Properties

	private var repository: ActionExecutionRepository

	// endregion

}


typealias  PredictedActions = List<HorusAction>
