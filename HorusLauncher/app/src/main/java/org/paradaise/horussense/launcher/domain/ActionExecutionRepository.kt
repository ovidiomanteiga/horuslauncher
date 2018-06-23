
package org.paradaise.horussense.launcher.domain


interface ActionExecutionRepository {

	val all: List<ActionExecution>

	fun add(execution: ActionExecution?)

}
