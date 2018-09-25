
package org.paradaise.horussense.launcher.infrastructure.database

import android.annotation.SuppressLint
import android.arch.persistence.room.*
import android.content.Context
import org.paradaise.horussense.launcher.infrastructure.action_execution.ActionExecutionDAO
import org.paradaise.horussense.launcher.infrastructure.action_execution.ActionExecutionDTO
import org.paradaise.horussense.launcher.infrastructure.stats.LauncherPresentationDAO
import org.paradaise.horussense.launcher.infrastructure.stats.LauncherPresentationDTO
import java.text.SimpleDateFormat
import java.util.*


@Database(
		entities = [ActionExecutionDTO::class, LauncherPresentationDTO::class],
		version = 1,
		exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

	abstract fun actionExecutionDAO(): ActionExecutionDAO
	abstract fun launcherPresentationDAO(): LauncherPresentationDAO

}


class Databases {

	companion object {

		fun main(context: Context): LocalDatabase {
			if (main != null) {
				return main!!
			}
			val dbClass = LocalDatabase::class.java
			val name = "local-database"
			val dbBuilder = Room.databaseBuilder(context, dbClass, name)
			val main = dbBuilder.build()
			Companion.main = main
			return main
		}


		private var main: LocalDatabase? = null

	}

}


class Converters {

	@TypeConverter
	fun dateFromString(value: String?): Date? {
		if (value == null) {
			return null
		}
		return this.formatter.parse(value)
	}


	@TypeConverter
	fun dateToString(date: Date?): String? {
		if (date == null) {
			return  null
		}
		return this.formatter.format(date)
	}


	@SuppressLint("SimpleDateFormat")
	private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

}
