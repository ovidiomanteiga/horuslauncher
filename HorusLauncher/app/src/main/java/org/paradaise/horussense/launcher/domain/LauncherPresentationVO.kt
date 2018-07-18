
package org.paradaise.horussense.launcher.domain

import java.util.*


open class LauncherPresentationVO : ValueObject {

	open var actionTime: Date? = null
	open var launchTime: Date? = null
	open var millisecondsTakenToGetHorusList: Milliseconds? = null
	open var result: LauncherPresentationResult? = null

}


enum class LauncherPresentationResult {
	NO_ACTION_PERFORMED,
	PERFORMED_ACTION,
	PERFORMED_PREDICTED_ACTION,
	PERFORMED_PROMOTED_ACTION,
}
