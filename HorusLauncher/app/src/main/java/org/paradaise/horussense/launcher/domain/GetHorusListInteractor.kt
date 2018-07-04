
package org.paradaise.horussense.launcher.domain

import android.graphics.drawable.Drawable
import java.util.*
import kotlin.Comparator


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

	private val horusListItemComparator: Comparator<in HorusListItem>
		get() = compareByDescending<HorusListItem> { it.numberOfExecutionsLastWeek }
				.thenByDescending { it.lastExecutionMoment }

	// endregion
	// region Private Methods
	
	private fun buildHorusList(executions: List<ActionExecution>) {
		val lastWeekMoment = this.lastWeekMoment()
		this.horusList = executions.filter {
			it.moment.after(lastWeekMoment)
		} .groupBy {
			it.action.url
		} .map {
			this.mapToHorusListItem(it.value)
		} .sortedWith(this.horusListItemComparator)
	}


	private fun lastWeekMoment(): Date? {
		val lastWeek = Calendar.getInstance()
		lastWeek.add(Calendar.DATE, -7)
		return lastWeek.time
	}


	private fun mapToHorusListItem(executions: List<ActionExecution>): HorusListItem {
		val action = executions.first().action
		val lastExecutionMoment = executions.maxBy { it.moment } ?.moment
		val numberOfExecutionsLastWeek = executions.count()
		return HorusListItem(action, lastExecutionMoment,  numberOfExecutionsLastWeek)
	}

	// endregion

}


typealias HorusList = List<HorusListItem>


open class HorusListItem {

	constructor(action: HorusAction, lastExecutionMoment: Date?,
	            numberOfExecutionsLastWeek: Int)
	{
		this.action = action
		this.lastExecutionMoment = lastExecutionMoment
		this.numberOfExecutionsLastWeek = numberOfExecutionsLastWeek
	}

	open val action: HorusAction

	open val icon: Drawable?
		get() = this.action.icon

	open val name: String?
		get() = this.action.name

	open val lastExecutionMoment: Date?

	open val numberOfExecutionsLastWeek: Int

}
