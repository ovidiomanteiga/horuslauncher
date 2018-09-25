package org.paradaise.horussense.launcher.infrastructure.stats

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "launcher_presentation")
class LauncherPresentationDTO {

	@PrimaryKey(autoGenerate = true)
	var uid: Int = 0

	@ColumnInfo(name = "identifier")
	var identifier: String? = null

	@ColumnInfo(name = "action_time")
	var actionTime: Date? = null

	@ColumnInfo(name = "launch_time")
	var launchTime: Date? = null

	@ColumnInfo(name = "result")
	var result: String? = null

	@ColumnInfo(name = "milliseconds_getting_horus_list")
	var millisecondsGettingHorusList: Long? = null

}