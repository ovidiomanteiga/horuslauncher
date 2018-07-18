
package org.paradaise.horussense.launcher.domain

import android.location.Location


interface UserLocationManager {

	fun getCurrentLocation(): Location?

}


class LocationPermissionNeededException : Exception()
