
package org.paradaise.horussense.launcher.domain

import java.util.*


open class LauncherPresentationManager {

	companion object {
		val shared = LauncherPresentationManager()
	}

	private var calendar: Calendar?

	constructor() {
		this.calendar = Calendar.getInstance()
	}

	constructor(calendar: Calendar) {
		this.calendar = calendar
	}

	open var current: LauncherPresentationVO? = null

	open fun finish(): LauncherPresentationVO {
		val current = this.current ?: throw Exception()
		current.actionTime = this.calendar?.time
		current.result = current.result ?: LauncherPresentationResult.NO_ACTION_PERFORMED
		return current
	}

	open fun start() {
		val current = LauncherPresentationVO()
		current.launchTime = this.calendar?.time
		this.current = current
	}

	open fun notifyActionPerformed() {
		this.current?.result = LauncherPresentationResult.PERFORMED_ACTION
	}

	open fun notifyPredictedActionPerformed() {
		this.current?.result = LauncherPresentationResult.PERFORMED_PREDICTED_ACTION
	}

	open fun notifyPromotedActionPerformed() {
		this.current?.result = LauncherPresentationResult.PERFORMED_PROMOTED_ACTION
	}

}
