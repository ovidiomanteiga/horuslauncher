
package org.paradaise.horussense.launcher.composition


import android.content.Context
import org.paradaise.horussense.launcher.domain.AllActionsRepository
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.infrastructure.AllAppsRepository


interface MainFactory {

	fun provideContext(): Context

	fun provideAllActionsRepository(): AllActionsRepository

	fun provideGetAllActionsInteractor(): GetAllActionsInteractor

}


class DefaultMainFactory(private val context: Context) : MainFactory {

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

}


class Factories {

	companion object {
		fun main(context: Context): MainFactory = DefaultMainFactory(context)
	}

}
