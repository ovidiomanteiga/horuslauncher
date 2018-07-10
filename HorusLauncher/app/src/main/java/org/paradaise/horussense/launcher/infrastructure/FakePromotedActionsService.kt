
package org.paradaise.horussense.launcher.infrastructure

import android.content.Context
import android.util.Log
import org.paradaise.horussense.launcher.domain.*


class FakePromotedActionsService(private val context: Context? = null) : PromotedActionsService {

	override fun getPromotedActionsFor(profile: UserProfile?): PromotedActions {
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
