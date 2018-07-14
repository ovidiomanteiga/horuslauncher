
package org.paradaise.horussense.launcher.infrastructure


import android.content.Context
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.ExecuteActionInteractor


class ExecuteAppInteractor : ExecuteActionInteractor {

	// region Lifecycle

	constructor(context: Context, repository: ActionExecutionRepository): super(repository) {
		this.context = context
	}

	// endregion
	// region ExecuteActionInteractor Override

	override fun perform() {
		val app = this.openAppAction ?: return
		app.context = this.context
		super.perform()
	}

	// endregion
	// region Private Properties

	private val openAppAction: OpenAppAction?
		get() = super.action as? OpenAppAction

	private var context: Context

	// endregion

}