package org.paradaise.horussense.launcher.infrastructure.action_execution

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface ActionExecutionDAO {

	@get:Query("SELECT * FROM action_execution")
	val all: List<ActionExecutionDTO>


	@Insert
	fun insertAll(vararg execution: ActionExecutionDTO)


	@Delete
	fun delete(execution: ActionExecutionDTO)

}