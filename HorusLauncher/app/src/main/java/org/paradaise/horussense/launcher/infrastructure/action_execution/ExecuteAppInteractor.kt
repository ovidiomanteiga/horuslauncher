
package org.paradaise.horussense.launcher.infrastructure.action_execution


import android.content.Context
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.action_execution.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import org.paradaise.horussense.launcher.infrastructure.action.OpenAppAction


class ExecuteAppInteractor : ExecuteActionInteractor {

	// region Lifecycle

	constructor(context: Context, actionExecutionRepository: ActionExecutionRepository,
	            launcherPresentationRepository: LauncherPresentationRepository)
			: super(actionExecutionRepository, launcherPresentationRepository)
	{
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