
package org.paradaise.horussense.launcher.domain


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
