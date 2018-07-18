
package org.paradaise.horussense.launcher.domain


open class LauncherStatsVO : ValueObject {

	open var averageActionTime: Seconds? = null
	open var averageTimeGettingHorusList: Milliseconds? = null
	open var horusListAccuracy: Percentage? = null

}


typealias Percentage = Double
