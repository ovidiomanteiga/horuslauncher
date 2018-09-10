
package org.paradaise.horussense.launcher.domain.horus_list

import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationManager
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionVO
import org.paradaise.horussense.launcher.domain.factory.DomainFactory
import java.util.*
import kotlin.Comparator
import kotlin.system.measureTimeMillis


open class GetHorusListInteractor {

	// region Lifecycle

	constructor(actionExecutionRepository: ActionExecutionRepository,
	            launcherPresentationRepository: LauncherPresentationRepository)
	{
		this.repository = actionExecutionRepository
		this.launcherPresentationRepository = launcherPresentationRepository
	}

	// endregion
	// region Public Properties

	open lateinit var horusList: HorusList

	// endregion
	// region Public Methods

	open fun perform() {
		val millis = measureTimeMillis {
			this.getHorusList()
		}
		this.recordTimeTakenToGetHorusList(millis)
	}

	// endregion
	// region Private Properties

	private val factory: DomainFactory
		get() {
			val factory = DomainFactory.current
			factory.launcherPresentationRepository = this.launcherPresentationRepository
			return factory
		}
	private var launcherPresentationRepository: LauncherPresentationRepository
	private val manager: LauncherPresentationManager
		get() = this.factory.provideLauncherPresentationManager()
	private var repository: ActionExecutionRepository

	private val horusListItemComparator: Comparator<in HorusListItem>
		get() = compareByDescending<HorusListItem> { it.numberOfExecutionsLastWeek }
				.thenByDescending { it.lastExecutionMoment }

	// endregion
	// region Private Methods

	private fun getHorusList() {
		val allActionExecutions = this.repository.all
		this.buildHorusList(allActionExecutions)
	}


	private fun buildHorusList(executions: Collection<ActionExecutionVO>) {
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


	private fun mapToHorusListItem(executions: List<ActionExecutionVO>): HorusListItem {
		val action = executions.first().action
		val lastExecutionMoment = executions.maxBy { it.moment } ?.moment
		val numberOfExecutionsLastWeek = executions.count()
		return PredictedHorusListItem(action, lastExecutionMoment, numberOfExecutionsLastWeek)
	}


	private fun recordTimeTakenToGetHorusList(milliseconds: Long) {
		this.manager.notifyMillisecondsTakenToGetHorusList(milliseconds)
	}

	// endregion

}
