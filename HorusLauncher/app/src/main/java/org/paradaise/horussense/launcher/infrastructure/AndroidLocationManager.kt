
package org.paradaise.horussense.launcher.infrastructure

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.paradaise.horussense.launcher.domain.LocationPermissionNeededException
import org.paradaise.horussense.launcher.domain.UserLocationManager
import java.util.concurrent.CountDownLatch


class AndroidLocationManager : UserLocationManager {

	// region Lifecycle

	@Throws(LocationPermissionNeededException::class)
	constructor(context: Context) {
		this.locationClient = LocationServices.getFusedLocationProviderClient(context)
	}

	// endregion
	// region UserLocationManager Override

	override fun getCurrentLocation(): Location? {
		var location: Location? = null
		val latch = CountDownLatch(1)
		try {
			this.locationClient.lastLocation.addOnCompleteListener {
				if (it.isSuccessful && it.result != null) {
					location = it.result
				}
				latch.countDown()
			}
			latch.await()
		} catch (_: SecurityException) {
		}
		return location
	}

	// endregion
	// region Private Properties

	private var locationClient: FusedLocationProviderClient

	// endregion

}
