
package org.paradaise.horussense.launcher.domain.promoted_actions

import org.paradaise.horussense.launcher.domain.action.PromotedAction
import org.paradaise.horussense.launcher.domain.factory.DomainFactory
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationManager
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import org.paradaise.horussense.launcher.domain.supertypes.Interactor


open class ExecutePromotedActionInteractor : Interactor {


	constructor(repository: LauncherPresentationRepository,
	            service: PromotedActionsService)
	{
		this.repository = repository
		this.service = service
	}

	open var action: PromotedAction? = null

	override fun perform() {
		val action = this.action ?: return
		action.perform()
		val execution = PromotedActionExecution(action)
		this.service.notifyActionExecuted(execution)
		this.manager.notifyPromotedActionPerformed()
	}

	private val factory: DomainFactory
		get() {
			val factory = DomainFactory.current
			factory.launcherPresentationRepository = this.repository
			return factory
		}
	private val manager: LauncherPresentationManager
		get() = this.factory.provideLauncherPresentationManager()
	private val repository: LauncherPresentationRepository
	private val service: PromotedActionsService

}
