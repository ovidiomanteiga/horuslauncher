
package org.paradaise.horussense.launcher.domain.factory

import org.paradaise.horussense.launcher.domain.time.DefaultTimeProvider
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationManager
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import org.paradaise.horussense.launcher.domain.time.TimeProvider


interface DomainFactory {

	companion object {
		internal var current: DomainFactory = DefaultDomainFactory()
	}

	var launcherPresentationRepository: LauncherPresentationRepository

	fun provideLauncherPresentationManager(): LauncherPresentationManager
	fun provideTimeProvider(): TimeProvider

}


class DefaultDomainFactory : DomainFactory {

	override lateinit var launcherPresentationRepository: LauncherPresentationRepository

	override fun provideLauncherPresentationManager(): LauncherPresentationManager {
		if (this.launcherPresentationManager == null) {
			val repository = this.launcherPresentationRepository
			val timeProvider = this.provideTimeProvider()
			val manager = LauncherPresentationManager(repository, timeProvider)
			this.launcherPresentationManager = manager
		}
		return this.launcherPresentationManager!!
	}

	override fun provideTimeProvider(): TimeProvider {
		return DefaultTimeProvider()
	}

	private var launcherPresentationManager: LauncherPresentationManager? = null

}