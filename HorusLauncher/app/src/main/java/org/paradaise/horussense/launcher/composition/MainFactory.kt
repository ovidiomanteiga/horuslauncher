
package org.paradaise.horussense.launcher.composition


import android.content.Context
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.AllActionsRepository
import org.paradaise.horussense.launcher.domain.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.infrastructure.*


interface MainFactory {

	fun provideActionExecutionRepository(): ActionExecutionRepository

	fun provideAllActionsRepository(): AllActionsRepository

	fun provideContext(): Context

	fun provideExecuteActionInteractor(): ExecuteActionInteractor

	fun provideGetAllActionsInteractor(): GetAllActionsInteractor

	fun provideLocalDatabase(): LocalDatabase

}


class DefaultMainFactory(private val context: Context) : MainFactory {

	override fun provideActionExecutionRepository(): ActionExecutionRepository {
		return DBActionExecutionRepository(this.provideLocalDatabase())
	}


	override fun provideAllActionsRepository(): AllActionsRepository {
		return AllAppsRepository(this.context)
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


	override fun provideLocalDatabase(): LocalDatabase {
		return 	Databases.main(this.context)
	}

}


class Factories {

	companion object {
		fun main(context: Context): MainFactory = DefaultMainFactory(context)
	}

}
