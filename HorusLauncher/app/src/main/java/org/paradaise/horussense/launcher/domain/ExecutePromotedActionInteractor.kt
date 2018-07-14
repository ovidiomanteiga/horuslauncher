
package org.paradaise.horussense.launcher.domain


open class ExecutePromotedActionInteractor : Interactor {


	constructor(service: PromotedActionsService) {
		this.service = service
	}

	open var action: PromotedAction? = null

	override fun perform() {
		val action = this.action ?: return
		action.perform()
		val execution = PromotedActionExecution(action)
		this.service.notifyActionExecuted(execution)
	}

	private val service: PromotedActionsService

}
