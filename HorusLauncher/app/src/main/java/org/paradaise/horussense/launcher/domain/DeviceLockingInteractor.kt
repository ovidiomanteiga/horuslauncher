
package org.paradaise.horussense.launcher.domain


open class DeviceLockingInteractor : Interactor {

	constructor(repository: LauncherPresentationRepository) {
		this.repository = repository
	}

	var locking: Boolean = false

	override fun perform() {
		if (this.locking) {
			this.launcherPresentationManager.finish()
		}  else {
			this.launcherPresentationManager.start()
		}
	}

	private val factory: DomainFactory
		get() {
			val factory = DomainFactory.current
			factory.launcherPresentationRepository = this.repository
			return factory
		}
	private val launcherPresentationManager: LauncherPresentationManager
		get() = this.factory.provideLauncherPresentationManager()
	private var repository: LauncherPresentationRepository

}
