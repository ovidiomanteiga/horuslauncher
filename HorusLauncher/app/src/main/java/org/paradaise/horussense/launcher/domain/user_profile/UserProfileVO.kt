
package org.paradaise.horussense.launcher.domain.user_profile

import android.location.Location
import org.paradaise.horussense.launcher.domain.action.HorusAction
import java.time.Year


class UserProfileVO {

	var currentLocation: Location? = null
	var gender: Gender? = null
	lateinit var top10actions: List<HorusAction>
	var yearOfBirth: Year? = null

}


typealias Gender = String
