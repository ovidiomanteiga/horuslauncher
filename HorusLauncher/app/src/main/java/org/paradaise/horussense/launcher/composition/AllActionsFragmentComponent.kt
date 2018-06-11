
package org.paradaise.horussense.launcher.composition


import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.paradaise.horussense.launcher.domain.AllActionsRepository
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.infrastructure.AllAppsRepository
import org.paradaise.horussense.launcher.main.HorusLauncherApplication
import org.paradaise.horussense.launcher.ui.AllActionsFragment
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class])
abstract class HorusLauncherFragmentModule {

	@Binds
	@Singleton
	abstract fun application(app: HorusLauncherApplication): Application


	@ContributesAndroidInjector
	abstract fun allActionsFragmentInjector(): AllActionsFragment

}


@Module(includes = [AndroidSupportInjectionModule::class])
class HorusLauncherModule(val application: HorusLauncherApplication) {

	@Provides
	fun getAllActionsInteractor(repository: AllActionsRepository): GetAllActionsInteractor {
		return GetAllActionsInteractor(repository)
	}


	@Provides
	fun allActionsRepository(context: Context): AllActionsRepository {
		return AllAppsRepository(context)
	}


	@Provides
	fun context(): Context {
		return this.application.applicationContext
	}

}


@Component(modules = [AndroidSupportInjectionModule::class,
	HorusLauncherFragmentModule::class, HorusLauncherModule::class])
interface HorusLauncherComponent : AndroidInjector<HorusLauncherApplication> {

	override fun inject(application: HorusLauncherApplication)

}