
package org.paradaise.horussense.launcher.domain


class GetPromotedActions: Interactor {

	constructor(service: PromotedActionsService) {
		this.service = service
	}

	lateinit var actions: PromotedActions


	override fun perform() {
		this.actions = this.service.getPromotedActionsFor(null)
	}

	private var service: PromotedActionsService

}


typealias PromotedActions = List<PromotedAction>
