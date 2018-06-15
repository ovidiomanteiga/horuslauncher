
package org.paradaise.horussense.launcher.infrastructure

import android.content.Context
import android.os.AsyncTask
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import java.util.*


class DBActionExecutionRepository : ActionExecutionRepository {

	// region Lifecycle

	constructor(context: Context) {
		this.db = Databases.main(context)
	}

	// endregion
	// region ActionExecutionRepository Implementation

	override fun add(execution: ActionExecution?) {
		val execution = execution ?: return
		val actionExecutionDTO = this.mapActionExecution(execution)
		AsyncTask.execute {
			this.db.actionExecutionDAO().insertAll(actionExecutionDTO)
		}
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
		dto.url = execution.action.url?.toString()
		return dto
	}

	// endregion

}
