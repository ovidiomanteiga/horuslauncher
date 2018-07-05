
package org.paradaise.horussense.launcher.domain

import android.location.Location
import java.time.Year


class UserProfile {

	lateinit var currentLocation: Location
	var gender: Gender? = null
	lateinit var top10actions: List<HorusAction>
	var yearOfBirth: Year? = null

}


typealias Gender = String
