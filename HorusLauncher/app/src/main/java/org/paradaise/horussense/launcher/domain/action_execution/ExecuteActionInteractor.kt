
package org.paradaise.horussense.launcher.domain.action_execution

import org.paradaise.horussense.launcher.domain.action.HorusAction
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationManager
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import org.paradaise.horussense.launcher.domain.factory.DomainFactory
import java.util.*


open class ExecuteActionInteractor {

	// region Lifecycle

	constructor(actionExecutionRepository: ActionExecutionRepository,
	            launcherPresentationRepository: LauncherPresentationRepository)
	{
		this.actionExecutionRepository = actionExecutionRepository
		this.launcherPresentationRepository = launcherPresentationRepository
	}

	// endregion
	// region Public Properties

	var action: HorusAction? = null
	var fromHorusList: Boolean = false

	// endregion
	// region Public Methods

	open fun perform() {
		val action = this.action ?: return
		action.perform()
		val execution = this.getExecution(action = action)
		this.actionExecutionRepository.add(execution)
		this.notifyLauncherPresentationManager()
	}

	// endregion
	// region Private Properties

	private var actionExecutionRepository: ActionExecutionRepository
	private val factory: DomainFactory
		get() {
			val factory = DomainFactory.current
			factory.launcherPresentationRepository = this.launcherPresentationRepository
			return factory
		}
	private var launcherPresentationRepository: LauncherPresentationRepository
	private val manager: LauncherPresentationManager
		get() = this.factory.provideLauncherPresentationManager()

	// endregion
	// region Private Methods

	private fun getExecution(action: HorusAction): ActionExecutionVO {
		val moment = Calendar.getInstance().time
		return ActionExecutionVO(action, moment)
	}


	private fun notifyLauncherPresentationManager() {
		if (this.fromHorusList) {
			this.manager.notifyPredictedActionPerformed()
		} else {
			this.manager.notifyActionPerformed()
		}
	}

	// endregion

}
