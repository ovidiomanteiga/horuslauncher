package org.paradaise.horussense.launcher.infrastructure

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface LauncherPresentationDAO {

	@get:Query("SELECT * FROM launcher_presentation")
	val all: List<LauncherPresentationDTO>


	@Insert
	fun insertAll(vararg presentations: LauncherPresentationDTO)


	@Delete
	fun delete(presentation: LauncherPresentationDTO)

}