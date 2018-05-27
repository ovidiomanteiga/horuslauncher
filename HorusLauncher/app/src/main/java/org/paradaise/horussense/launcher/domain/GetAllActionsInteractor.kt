
package org.paradaise.horussense.launcher.domain



open class GetAllActionsInteractor {

	// region Lifecycle

	constructor(repository: AllActionsRepository) {
		this.repository = repository
	}

	//endregion
	// region Public Properties

    var allApps: AllActions = ArrayList()

	// endregion
	// region Public Methods

    internal fun perform() {
	    this.allApps = this.getAllAvailableActions()
    }

	// endregion
	// region Private Properties

	private var repository: AllActionsRepository

	// endregion
	// region Private Methods

	private fun getAllAvailableActions(): AllActions {
		return this.repository.get()
	}

	// endregion
}
