
package org.paradaise.horussense.launcher.infrastructure

import android.util.Log
import org.paradaise.horussense.launcher.domain.*


class FakePromotedActionsService : PromotedActionsService {

	override fun getPromotedActionsFor(profile: UserProfile?): PromotedActions {
		val promotedAction = PromotedAction()
		promotedAction.name = "Speak English Now"
		promotedAction.description = "Keep Calm & Speak English Now!"
		return listOf(promotedAction)
	}


	override fun notifyActionExecuted(execution: PromotedActionExecution?) {
		Log.d("NOTIFY_ACTION_EXECUTED", execution?.action?.name)
	}

}
