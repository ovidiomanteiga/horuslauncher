
package org.paradaise.horussense.launcher.domain.all_actions



open class GetAllActionsInteractor {

	// region Lifecycle

	constructor(repository: AllActionsRepository) {
		this.repository = repository
	}

	//endregion
	// region Public Properties

    open var allActions: AllActions = ArrayList()
	open var paging: Paging? = null

	// endregion
	// region Public Methods

    open fun perform() {
	    this.allActions = this.getAllAvailableActions()
    }

	// endregion
	// region Private Properties

	private var repository: AllActionsRepository

	// endregion
	// region Private Methods

	private fun getAllAvailableActions(): AllActions {
		val actions = this.repository.get()
		val sortedActions = actions.sortedBy { it.name }
		this.paging?.let {
			return sortedActions.drop(it.startIndex).take(it.maxItems)
		}
		return sortedActions
	}

	// endregion
}



data class Paging(val startIndex: Int, val maxItems: Int)
