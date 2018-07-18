
package org.paradaise.horussense.launcher.domain

import java.util.*


interface TimeProvider {

	val now: Date

}


typealias Seconds = Double
typealias Milliseconds = Long


class DefaultTimeProvider: TimeProvider {

	override val now: Date
		get() = Calendar.getInstance().time

}
