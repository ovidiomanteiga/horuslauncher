
package org.paradaise.horussense.launcher.domain


interface PromotedActionsService {

	fun getPromotedActionsFor(profile: UserProfile?): PromotedActions
	fun notifyActionExecuted(action: PromotedActionExecution?)

}
