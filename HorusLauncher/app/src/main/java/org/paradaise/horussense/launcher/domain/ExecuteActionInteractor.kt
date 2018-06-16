
package org.paradaise.horussense.launcher.domain

import java.util.*


open class ExecuteActionInteractor {

	// region Lifecycle

	constructor(repository: ActionExecutionRepository) {
		this.repository = repository
	}

	// endregion
	// region Public Properties

	var action: HorusAction? = null

	// endregion
	// region Public Methods

	open fun perform() {
		val action = this.action ?: return
		action.perform()
		val execution = this.getExecution(action = action)
		this.repository.add(execution)
	}

	// endregion
	// region Private Properties

	private var repository: ActionExecutionRepository

	// endregion
	// region Public Methods

	private fun getExecution(action: HorusAction): ActionExecution {
		val moment = Calendar.getInstance().time
		return ActionExecution(action, moment)
	}

	// endregion

}
