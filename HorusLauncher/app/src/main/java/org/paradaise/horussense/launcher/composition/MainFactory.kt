
package org.paradaise.horussense.launcher.composition

import android.content.Context
import org.paradaise.horussense.launcher.domain.*
import org.paradaise.horussense.launcher.infrastructure.*


interface MainFactory {

	var context: Context

	fun provideActionExecutionRepository(): ActionExecutionRepository

	fun provideAllActionsRepository(): AllActionsRepository

	fun provideContext(): Context

	fun provideExecuteActionInteractor(): ExecuteActionInteractor

	fun provideGetAllActionsInteractor(): GetAllActionsInteractor

	fun provideGetHorusListInteractor(): GetHorusListInteractor

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


	override fun provideExecuteActionInteractor(): ExecuteActionInteractor {
		val context = this.provideContext()
		val repository = this.provideActionExecutionRepository()
		return ExecuteAppInteractor(context, repository)
	}


	override fun provideGetAllActionsInteractor(): GetAllActionsInteractor {
		val repository = this.provideAllActionsRepository()
		return GetAllActionsInteractor(repository)
	}


	override fun provideGetHorusListInteractor(): GetHorusListInteractor{
		val repository = this.provideActionExecutionRepository()
		return GetHorusListInteractor(repository)
	}


	override fun provideLocalDatabase(): LocalDatabase {
		return 	Databases.main(this.context)
	}

}
