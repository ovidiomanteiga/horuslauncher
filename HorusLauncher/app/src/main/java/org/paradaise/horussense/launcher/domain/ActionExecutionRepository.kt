
package org.paradaise.horussense.launcher.domain


interface ActionExecutionRepository {

	val all: List<ActionExecutionVO>

	fun add(execution: ActionExecutionVO?)

}
