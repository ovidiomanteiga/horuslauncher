
package org.paradaise.horussense.launcher.infrastructure

import org.paradaise.horussense.launcher.domain.PromotedAction
import org.paradaise.horussense.launcher.domain.PromotedActions
import org.paradaise.horussense.launcher.domain.PromotedActionsService
import org.paradaise.horussense.launcher.domain.UserProfile


class FakePromotedActionsService : PromotedActionsService {

	override fun getPromotedActionsFor(profile: UserProfile?): PromotedActions {
		val promotedAction = PromotedAction()
		promotedAction.name = "Buy me!"
		return listOf(promotedAction)
	}

}
