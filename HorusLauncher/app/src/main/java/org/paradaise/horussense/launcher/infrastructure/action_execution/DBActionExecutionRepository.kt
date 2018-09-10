
package org.paradaise.horussense.launcher.infrastructure.action_execution

import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionVO
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.all_actions.AllActionsRepository
import org.paradaise.horussense.launcher.infrastructure.database.LocalDatabase
import java.util.*


class DBActionExecutionRepository : ActionExecutionRepository {

	// region Lifecycle

	constructor(allActionsRepository: AllActionsRepository, db: LocalDatabase) {
		this.allActionsRepository = allActionsRepository
		this.db = db
	}

	// endregion
	// region ActionExecutionRepository Implementation

	override val all: List<ActionExecutionVO>
		get() {
			return this.map(this.db.actionExecutionDAO().all)
		}


	override fun add(item: ActionExecutionVO?) {
		val actionExecution = item ?: return
		val actionExecutionDTO = this.mapActionExecution(actionExecution)
		this.db.actionExecutionDAO().insertAll(actionExecutionDTO)
	}

	// endregion
	// region Private Properties

	private var allActionsRepository: AllActionsRepository
	private var db: LocalDatabase

	// endregion
	// region Private Methods

	private fun mapActionExecution(execution: ActionExecutionVO): ActionExecutionDTO {
		val dto = ActionExecutionDTO()
		dto.identifier = UUID.randomUUID().toString()
		dto.moment = execution.moment
		dto.url = execution.action.url
		return dto
	}


	private fun map(executionDTOs: List<ActionExecutionDTO>): List<ActionExecutionVO> {
		val apps = this.allActionsRepository.get()
		return executionDTOs.map { dto ->
			val app = apps.first { app -> app.url == dto.url }
			ActionExecutionVO(app, dto.moment!!)
		}
	}

	// endregion

}
