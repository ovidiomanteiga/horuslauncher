
package org.paradaise.horussense.launcher.infrastructure

import android.content.Intent
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import java.util.*


class DBActionExecutionRepository : ActionExecutionRepository {

	// region Lifecycle

	constructor(db: LocalDatabase) {
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
		return executionDTOs.map {
			val intent = Intent.parseUri(it.url, 0)
			val app = App(null, intent, "")
			ActionExecution(app, it.moment!!)
		}
	}

	// endregion

}
