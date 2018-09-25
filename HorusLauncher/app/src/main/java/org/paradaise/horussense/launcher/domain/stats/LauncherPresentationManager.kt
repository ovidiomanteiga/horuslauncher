
package org.paradaise.horussense.launcher.domain.stats

import org.paradaise.horussense.launcher.domain.time.TimeProvider
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationResult.*


open class LauncherPresentationManager {

	constructor(repository: LauncherPresentationRepository, timeProvider: TimeProvider) {
		this.repository = repository
		this.timeProvider = timeProvider
	}

	open var current: LauncherPresentationVO? = null

	open fun finish(): LauncherPresentationVO? {
		return this.saveResult(NO_ACTION_PERFORMED)
	}

	open fun start() {
		this.initializeCurrentPresentation()
		this.current?.launchTime = this.timeProvider.now
	}

	open fun notifyActionPerformed(): LauncherPresentationVO? {
		return this.saveResult(PERFORMED_ACTION)
	}

	open fun notifyPredictedActionPerformed(): LauncherPresentationVO? {
		return this.saveResult(PERFORMED_PREDICTED_ACTION)
	}

	open fun notifyPromotedActionPerformed(): LauncherPresentationVO? {
		return this.saveResult(PERFORMED_PROMOTED_ACTION)
	}

	open fun notifyMillisecondsTakenToGetHorusList(milliseconds: Long) {
		this.initializeCurrentPresentation()
		this.current?.millisecondsTakenToGetHorusList = milliseconds
	}

	private var repository: LauncherPresentationRepository
	private var timeProvider: TimeProvider

	private fun initializeCurrentPresentation() {
		this.current = this.current ?: LauncherPresentationVO()
	}

	private fun saveResult(result: LauncherPresentationResult): LauncherPresentationVO? {
		val current = this.current ?: return null
		current.actionTime = this.timeProvider.now
		current.result = result
		this.save()
		return current
	}

	private fun save() {
		this.repository.add(this.current)
		this.current = null
	}

}
