
package org.paradaise.horussense.launcher.composition

import android.content.Context
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.action_execution.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.all_actions.AllActionsRepository
import org.paradaise.horussense.launcher.domain.all_actions.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.horus_list.GetHorusListInteractor
import org.paradaise.horussense.launcher.domain.promoted_actions.ExecutePromotedActionInteractor
import org.paradaise.horussense.launcher.domain.promoted_actions.GetPromotedActionsInteractor
import org.paradaise.horussense.launcher.domain.promoted_actions.PromotedActionsService
import org.paradaise.horussense.launcher.domain.stats.*
import org.paradaise.horussense.launcher.domain.user_profile.UserLocationManager
import org.paradaise.horussense.launcher.domain.user_profile.UserProfileManager
import org.paradaise.horussense.launcher.infrastructure.action_execution.DBActionExecutionRepository
import org.paradaise.horussense.launcher.infrastructure.action_execution.ExecuteAppInteractor
import org.paradaise.horussense.launcher.infrastructure.all_actions.AllAppsRepository
import org.paradaise.horussense.launcher.infrastructure.database.Databases
import org.paradaise.horussense.launcher.infrastructure.database.LocalDatabase
import org.paradaise.horussense.launcher.infrastructure.promoted_actions.FakePromotedActionsService
import org.paradaise.horussense.launcher.infrastructure.stats.DBLauncherPresentationRepository
import org.paradaise.horussense.launcher.infrastructure.stats.FakeStatsService
import org.paradaise.horussense.launcher.infrastructure.user_profile.AndroidLocationManager


interface MainFactory {

	var context: Context

	fun provideActionExecutionRepository(): ActionExecutionRepository

	fun provideAllActionsRepository(): AllActionsRepository

	fun provideContext(): Context

	fun provideDeviceLockingInteractor(): DeviceLockingInteractor

	fun provideExecuteActionInteractor(): ExecuteActionInteractor

	fun provideExecutePromotedActionInteractor(): ExecutePromotedActionInteractor

	fun provideGetAllActionsInteractor(): GetAllActionsInteractor

	fun provideGetHorusListInteractor(): GetHorusListInteractor

	fun provideGetPromotedActionsInteractor(): GetPromotedActionsInteractor

	fun provideGetStatsInteractor(): GetStatsInteractor

	fun provideLauncherPresentationRepository(): LauncherPresentationRepository

	fun providePromotedActionsService(): PromotedActionsService

	fun provideUserLocationManager(): UserLocationManager

	fun provideSendStatsInteractor(): SendStatsInteractor

	fun provideStatsService(): StatsService

	fun provideLocalDatabase(): LocalDatabase

}


class DefaultMainFactory : MainFactory {

	override lateinit var context: Context


	override fun provideActionExecutionRepository(): ActionExecutionRepository {
		val db = this.provideLocalDatabase()
		val repository = this.provideAllActionsRepository()
		return DBActionExecutionRepository(repository, db)
	}


	override fun provideAllActionsRepository(): AllActionsRepository {
		return AllAppsRepository(this.provideContext())
	}


	override fun provideContext(): Context {
		return this.context
	}


	override fun provideDeviceLockingInteractor(): DeviceLockingInteractor {
		return DeviceLockingInteractor(this.provideLauncherPresentationRepository())
	}


	override fun provideExecuteActionInteractor(): ExecuteActionInteractor {
		val context = this.provideContext()
		val actionExecutionRepository = this.provideActionExecutionRepository()
		val launcherPresentationRepository = this.provideLauncherPresentationRepository()
		return ExecuteAppInteractor(context, actionExecutionRepository,
				launcherPresentationRepository)
	}


	override fun provideExecutePromotedActionInteractor(): ExecutePromotedActionInteractor {
		val repository = this.provideLauncherPresentationRepository()
		val service = this.providePromotedActionsService()
		return ExecutePromotedActionInteractor(repository, service)
	}


	override fun provideGetAllActionsInteractor(): GetAllActionsInteractor {
		val repository = this.provideAllActionsRepository()
		return GetAllActionsInteractor(repository)
	}


	override fun provideGetHorusListInteractor(): GetHorusListInteractor {
		val actionExecutionRepository = this.provideActionExecutionRepository()
		val launcherPresentationRepository = this.provideLauncherPresentationRepository()
		return GetHorusListInteractor(actionExecutionRepository,
				launcherPresentationRepository)
	}


	override fun provideGetPromotedActionsInteractor(): GetPromotedActionsInteractor {
		val repository = this.provideActionExecutionRepository()
		val service = this.providePromotedActionsService()
		val locationManager = this.provideUserLocationManager()
		val manager = UserProfileManager(locationManager, repository)
		return GetPromotedActionsInteractor(service = service, userProfileManager = manager)
	}


	override fun provideGetStatsInteractor(): GetStatsInteractor {
		return GetStatsInteractor(this.provideLauncherPresentationRepository())
	}


	override fun provideLauncherPresentationRepository(): LauncherPresentationRepository {
		return DBLauncherPresentationRepository(this.provideLocalDatabase())
	}


	override fun providePromotedActionsService(): PromotedActionsService {
		return FakePromotedActionsService(this.provideContext())
	}


	override fun provideUserLocationManager(): UserLocationManager {
		return AndroidLocationManager(this.provideContext())
	}


	override fun provideSendStatsInteractor(): SendStatsInteractor {
		return SendStatsInteractor(this.provideGetStatsInteractor(),
				this.provideStatsService())
	}


	override fun provideStatsService(): StatsService {
		return FakeStatsService()
	}


	override fun provideLocalDatabase(): LocalDatabase {
		return 	Databases.main(this.provideContext())
	}

}
