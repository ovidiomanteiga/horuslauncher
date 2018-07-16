
package org.paradaise.horussense.launcher.domain

import java.util.*


interface TimeProvider {

	val now: Date

}


class DefaultTimeProvider: TimeProvider {

	override val now: Date
		get() = Calendar.getInstance().time

}
