
package org.paradaise.horussense.launcher.domain.all_actions

import org.paradaise.horussense.launcher.domain.action.HorusAction



interface AllActionsRepository {

	fun get(): AllActions

}



typealias  AllActions = List<HorusAction>