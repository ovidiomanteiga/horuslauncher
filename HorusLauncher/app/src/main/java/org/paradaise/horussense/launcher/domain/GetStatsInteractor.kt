
package org.paradaise.horussense.launcher.domain


open class GetStatsInteractor : Interactor {

	constructor(repository: LauncherPresentationRepository) {
		this.repository = repository
	}

	open var stats: LauncherStatsVO? = null

	override fun perform() {
		this.stats = this.buildStats()
	}

	private fun buildStats(): LauncherStatsVO? {
		val all = this.repository.all
		val averageTimeGettingHorusList = this.getAverageTimeGettingHorusList(all)
		val averageActionTime = this.getAverageActionTime(all)
		val horusListAccuracy = this.getHorusListAccuracy(all)
		val stats = LauncherStatsVO()
		stats.averageActionTime = averageActionTime
		stats.averageTimeGettingHorusList = averageTimeGettingHorusList
		stats.horusListAccuracy = horusListAccuracy
		return stats
	}

	private fun getAverageTimeGettingHorusList(all: Collection<LauncherPresentationVO>):
			Milliseconds?
	{
		val nonNullValues = all.mapNotNull { it.millisecondsTakenToGetHorusList }
		val count = nonNullValues.count()
		if (count == 0) return null
		val total = nonNullValues.reduce { acc, milliseconds ->
			acc + milliseconds
		}
		return total / count
	}

	private fun getAverageActionTime(all: Collection<LauncherPresentationVO>): Seconds? {
		val nonNullValues = all.mapNotNull {
			val launchTime = it.launchTime ?: return@mapNotNull null
			val actionTime = it.actionTime ?: return@mapNotNull null
			 actionTime.time - launchTime.time
		}
		val count = nonNullValues.count()
		if (count == 0) return null
		val total = nonNullValues.reduce { acc, diff ->
			acc + diff
		}
		return total / count.toDouble() / 1000.0
	}

	private fun getHorusListAccuracy(all: Collection<LauncherPresentationVO>): Percentage? {
		val totalHits = all.count {
			it.result == LauncherPresentationResult.PERFORMED_PREDICTED_ACTION
		}
		if (all.count() == 0) return null
		return totalHits * 100.0 / all.count()
	}

	private var repository: LauncherPresentationRepository

}
