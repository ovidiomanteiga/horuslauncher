
package org.paradaise.horussense.launcher.domain.promoted_actions

import org.paradaise.horussense.launcher.domain.user_profile.UserProfileVO


interface PromotedActionsService {

	fun getPromotedActionsFor(profile: UserProfileVO?): PromotedActions
	fun notifyActionExecuted(execution: PromotedActionExecution?)

}
