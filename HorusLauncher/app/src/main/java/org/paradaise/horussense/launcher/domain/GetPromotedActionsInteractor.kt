
package org.paradaise.horussense.launcher.domain


open class GetPromotedActionsInteractor: Interactor {

	constructor(service: PromotedActionsService, userProfileManager: UserProfileManager?) {
		this.service = service
		this.userProfileManager = userProfileManager
	}


	open lateinit var actions: PromotedActions


	override fun perform() {
		val userProfile = this.userProfileManager?.getUserProfile()
		this.actions = this.service.getPromotedActionsFor(profile = userProfile)
	}


	private var service: PromotedActionsService
	private var userProfileManager: UserProfileManager?

}


typealias PromotedActions = List<PromotedAction>
