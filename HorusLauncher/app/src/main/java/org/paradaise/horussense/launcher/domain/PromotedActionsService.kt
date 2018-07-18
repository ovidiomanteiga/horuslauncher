
package org.paradaise.horussense.launcher.domain


interface PromotedActionsService {

	fun getPromotedActionsFor(profile: UserProfileVO?): PromotedActions
	fun notifyActionExecuted(execution: PromotedActionExecution?)

}
