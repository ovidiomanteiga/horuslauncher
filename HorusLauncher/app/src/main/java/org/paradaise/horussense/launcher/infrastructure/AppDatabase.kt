
package org.paradaise.horussense.launcher.infrastructure

import android.arch.persistence.room.*
import android.content.Context
import java.util.*



class Databases {

	companion object {

		fun main(context: Context): AppDatabase {
			if (this.main != null) {
				return this.main!!
			}
			val dbClass = AppDatabase::class.java
			val name = "app-database"
			val dbBuilder = Room.databaseBuilder(context, dbClass, name)
			val main = dbBuilder.build()
			this.main = main
			return main
		}


		private var main: AppDatabase? = null

	}

}


@Database(entities = [(ActionExecutionDTO::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun actionExecutionDAO(): ActionExecutionDAO

}


@Dao
interface ActionExecutionDAO {

	@get:Query("SELECT * FROM actionexecutiondto")
	val all: List<ActionExecutionDTO>

	@Insert
	fun insertAll(vararg execution: ActionExecutionDTO)

	@Delete
	fun delete(execution: ActionExecutionDTO)

}


@Entity
class ActionExecutionDTO {

	@PrimaryKey(autoGenerate = true)
	var uid: Int = 0

	@ColumnInfo(name = "action_identifier")
	var actionIdentifier: String? = null

	@ColumnInfo(name = "moment")
	var moment: Date? = null

}


class Converters {

	@TypeConverter
	fun fromTimestamp(value: Long?): Date? {
		return if (value == null) null else Date(value)
	}

	@TypeConverter
	fun dateToTimestamp(date: Date?): Long? {
		return date?.time
	}

}
