
package org.paradaise.horussense.launcher.domain


open class DeviceLockingInteractor : Interactor {

	constructor(repository: LauncherPresentationRepository) {
		this.launcherPresentationManager = LauncherPresentationManager.shared
		this.repository = repository
	}

	constructor(manager: LauncherPresentationManager,
	            repository: LauncherPresentationRepository): this(repository)
	{
		this.launcherPresentationManager = manager
	}

	var locking: Boolean = false
	var launcherPresentationManager: LauncherPresentationManager? = null
	var repository: LauncherPresentationRepository

	override fun perform() {
		if (this.locking) this.onLocking()
		else this.onUnlocking()
	}

	private fun onLocking() {
		this.launcherPresentationManager?.finish()
		val currentPresentation = this.launcherPresentationManager?.current ?: return
		this.repository.add(currentPresentation)
	}

	private fun onUnlocking() {
		this.launcherPresentationManager?.start()
	}

}
