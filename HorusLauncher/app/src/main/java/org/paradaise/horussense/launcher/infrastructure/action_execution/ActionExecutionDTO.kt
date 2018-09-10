package org.paradaise.horussense.launcher.infrastructure.action_execution

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "action_execution")
class ActionExecutionDTO {

	@PrimaryKey(autoGenerate = true)
	var uid: Int = 0

	@ColumnInfo(name = "identifier")
	var identifier: String? = null

	@ColumnInfo(name = "moment")
	var moment: Date? = null

	@ColumnInfo(name = "url")
	var url: String? = null

}