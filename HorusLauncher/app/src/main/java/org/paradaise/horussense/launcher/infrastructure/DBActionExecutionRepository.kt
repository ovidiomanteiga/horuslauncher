
package org.paradaise.horussense.launcher.infrastructure

import android.content.Context
import android.os.AsyncTask
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository


class DBActionExecutionRepository : ActionExecutionRepository {

	constructor(context: Context) {
		this.db = Databases.main(context)
	}


	override fun add(execution: ActionExecution?) {
		val execution = execution ?: return
		val actionExecutionDTO = this.mapActionExecution(execution)
		AsyncTask.execute {
			this.db.actionExecutionDAO().insertAll(actionExecutionDTO)
		}
	}


	private var db: AppDatabase


	private fun mapActionExecution(execution: ActionExecution): ActionExecutionDTO {
		val dto = ActionExecutionDTO()
		dto.actionIdentifier = execution.action.uuid.toString()
		dto.moment = execution.moment
		return dto
	}

}
