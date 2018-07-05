
package org.paradaise.horussense.launcher.domain


class UserProfileManager {

	// region Lifecycle

	constructor(locationManager: UserLocationManager, repository: ActionExecutionRepository) {
		this.locationManager = locationManager
		this.repository = repository
	}

	// endregion
	// region Public Methods

	fun getUserProfile(): UserProfile? {
		val userProfile = UserProfile()
		userProfile.currentLocation = this.locationManager.getCurrentLocation()
		userProfile.top10actions = this.buildTopActionsList()
		return userProfile
	}

	// endregion
	// region Private Properties

	private var locationManager: UserLocationManager
	private var repository: ActionExecutionRepository

	private val horusListItemComparator: Comparator<in HorusListItem>
		get() = compareByDescending<HorusListItem> { it.numberOfExecutionsLastWeek }
				.thenByDescending { it.lastExecutionMoment }

	// endregion
	// region Private Methods

	private fun buildTopActionsList(): List<HorusAction> {
		val executions = this.repository.all
		return executions.groupBy {
			it.action.url
		} .map {
			this.mapToHorusListItem(it.value)
		} .sortedWith(this.horusListItemComparator) .map { it.action }
	}


	private fun mapToHorusListItem(executions: List<ActionExecution>): HorusListItem {
		val action = executions.first().action
		val lastExecutionMoment = executions.maxBy { it.moment } ?.moment
		val numberOfExecutionsLastWeek = executions.count()
		return HorusListItem(action, lastExecutionMoment,  numberOfExecutionsLastWeek)
	}

	// endregion

}
