package org.paradaise.horussense.launcher



open class GetAllAppsInteractor {

	// region Lifecycle

	constructor(service: AllAppsService) {
		this.service = service
	}

	// region Public Properties

    var allApps: List<App> = ArrayList()

	// endregion
	// region Public Methods

    internal fun perform() {
	    this.allApps = this.getAllAvailableApps()
    }

	// endregion
	// region Private Properties

	private var service: AllAppsService

	// endregion
	// region Private Methods

	internal open fun getAllAvailableApps(): List<App> {
		return this.service.get()
	}

	// endregion
}



typealias App = String
