
package org.paradaise.horussense.launcher.domain.stats

import org.paradaise.horussense.launcher.domain.time.Milliseconds
import org.paradaise.horussense.launcher.domain.supertypes.ValueObject
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
