
package org.paradaise.horussense.launcher.composition


import android.content.Context
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.AllActionsRepository
import org.paradaise.horussense.launcher.domain.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.infrastructure.AllAppsRepository
import org.paradaise.horussense.launcher.infrastructure.DBActionExecutionRepository


interface MainFactory {

	fun provideContext(): Context

	fun provideActionExecutionRepository(): ActionExecutionRepository

	fun provideAllActionsRepository(): AllActionsRepository

	fun provideExecuteActionInteractor(): ExecuteActionInteractor

	fun provideGetAllActionsInteractor(): GetAllActionsInteractor

}


class DefaultMainFactory(private val context: Context) : MainFactory {

	override fun provideActionExecutionRepository(): ActionExecutionRepository {
		return DBActionExecutionRepository(this.context)
	}


	override fun provideAllActionsRepository(): AllActionsRepository {
		return AllAppsRepository(this.context)
	}


	override fun provideContext(): Context {
		return this.context
	}


	override fun provideGetAllActionsInteractor(): GetAllActionsInteractor {
		val repository = this.provideAllActionsRepository()
		return GetAllActionsInteractor(repository)
	}


	override fun provideExecuteActionInteractor(): ExecuteActionInteractor {
		val repository = this.provideActionExecutionRepository()
		return ExecuteActionInteractor(repository)
	}

}


class Factories {

	companion object {
		fun main(context: Context): MainFactory = DefaultMainFactory(context)
	}

}
