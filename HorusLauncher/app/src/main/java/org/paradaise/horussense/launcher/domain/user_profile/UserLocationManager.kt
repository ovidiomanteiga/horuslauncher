
package org.paradaise.horussense.launcher.domain.user_profile

import android.location.Location


interface UserLocationManager {

	fun getCurrentLocation(): Location?

}


class LocationPermissionNeededException : Exception()
