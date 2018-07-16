
package org.paradaise.horussense.launcher.infrastructure


import android.content.Context
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.LauncherPresentationRepository


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