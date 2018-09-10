
package org.paradaise.horussense.launcher.domain.stats

import org.paradaise.horussense.launcher.domain.supertypes.Interactor


open class SendStatsInteractor : Interactor {

	constructor(getStatsInteractor: GetStatsInteractor, statsService: StatsService) {
		this.getStatsInteractor = getStatsInteractor
		this.statsService = statsService
	}

	override fun perform() {
		this.getStatsInteractor.perform()
		this.getStatsInteractor.stats?.let {
			this.statsService.send(it)
		}
	}

	private val getStatsInteractor: GetStatsInteractor
	private var statsService: StatsService

}
