
package org.paradaise.horussense.launcher.domain



open class GetAllActionsInteractor {

	// region Lifecycle

	constructor(repository: AllActionsRepository) {
		this.repository = repository
	}

	//endregion
	// region Public Properties

    var allActions: AllActions = ArrayList()

	// endregion
	// region Public Methods

    internal fun perform() {
	    this.allActions = this.getAllAvailableActions()
    }

	// endregion
	// region Private Properties

	private var repository: AllActionsRepository

	// endregion
	// region Private Methods

	private fun getAllAvailableActions(): AllActions {
		val actions = this.repository.get()
		return actions.sortedBy { it.name }
	}

	// endregion
}
