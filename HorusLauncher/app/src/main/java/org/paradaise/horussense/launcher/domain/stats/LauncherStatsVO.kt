
package org.paradaise.horussense.launcher.domain.stats

import org.paradaise.horussense.launcher.domain.time.Milliseconds
import org.paradaise.horussense.launcher.domain.time.Seconds
import org.paradaise.horussense.launcher.domain.supertypes.ValueObject


open class LauncherStatsVO : ValueObject {

	open var averageActionTime: Seconds? = null
	open var averageTimeGettingHorusList: Milliseconds? = null
	open var horusListAccuracy: Percentage? = null

}


typealias Percentage = Double
