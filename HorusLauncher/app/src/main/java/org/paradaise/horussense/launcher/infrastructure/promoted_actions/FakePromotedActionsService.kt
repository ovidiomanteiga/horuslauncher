
package org.paradaise.horussense.launcher.infrastructure.promoted_actions

import android.content.Context
import android.util.Log
import org.paradaise.horussense.launcher.domain.promoted_actions.PromotedActionExecution
import org.paradaise.horussense.launcher.domain.promoted_actions.PromotedActions
import org.paradaise.horussense.launcher.domain.promoted_actions.PromotedActionsService
import org.paradaise.horussense.launcher.domain.user_profile.UserProfileVO


class FakePromotedActionsService(private val context: Context? = null) : PromotedActionsService {

	override fun getPromotedActionsFor(profile: UserProfileVO?): PromotedActions {
		val promotedAction = OpenLinkPromotedAction(null, "Speak English Now",
				description = "Keep Calm & Speak English Now!")
		promotedAction.url = "http://www.google.com/search?q=Keep%20Calm"
		promotedAction.context = this.context
		return listOf(promotedAction)
	}


	override fun notifyActionExecuted(execution: PromotedActionExecution?) {
		Log.d("NOTIFY_ACTION_EXECUTED", execution?.action?.name)
	}

}
