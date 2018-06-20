
package org.paradaise.horussense.launcher.infrastructure

import android.content.Intent
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.AllActionsRepository
import java.util.*


class DBActionExecutionRepository : ActionExecutionRepository {

	// region Lifecycle

	constructor(allActionsRepository: AllActionsRepository, db: LocalDatabase) {
		this.allActionsRepository = allActionsRepository
		this.db = db
	}

	// endregion
	// region ActionExecutionRepository Implementation

	override val all: List<ActionExecution>
		get() {
			return this.map(this.db.actionExecutionDAO().all)
		}


	override fun add(execution: ActionExecution?) {
		val actionExecution = execution ?: return
		val actionExecutionDTO = this.mapActionExecution(actionExecution)
		this.db.actionExecutionDAO().insertAll(actionExecutionDTO)
	}

	// endregion
	// region Private Properties

	private var allActionsRepository: AllActionsRepository
	private var db: LocalDatabase

	// endregion
	// region Private Methods

	private fun mapActionExecution(execution: ActionExecution): ActionExecutionDTO {
		val dto = ActionExecutionDTO()
		dto.identifier = UUID.randomUUID().toString()
		dto.moment = execution.moment
		dto.url = execution.action.url
		return dto
	}


	private fun map(executionDTOs: List<ActionExecutionDTO>): List<ActionExecution> {
		val apps = this.allActionsRepository.get()
		return executionDTOs.map { dto ->
			val app = apps.first { app -> app.url == dto.url }
			ActionExecution(app, dto.moment!!)
		}
	}

	// endregion

}
