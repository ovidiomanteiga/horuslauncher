
package org.paradaise.horussense.launcher.domain



interface AllActionsRepository {

	fun get(): AllActions

}



typealias  AllActions = List<HorusAction>