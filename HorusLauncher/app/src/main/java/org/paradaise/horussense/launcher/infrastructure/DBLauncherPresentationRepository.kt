
package org.paradaise.horussense.launcher.infrastructure

import org.paradaise.horussense.launcher.domain.*
import java.util.*


class DBLauncherPresentationRepository : LauncherPresentationRepository {

	// region Lifecycle

	constructor(db: LocalDatabase) {
		this.db = db
	}

	// endregion
	// region ActionExecutionRepository Implementation

	override val all: List<LauncherPresentationVO>
		get() {
			return this.map(this.db.launcherPresentationDAO().all)
		}


	override fun add(item: LauncherPresentationVO?) {
		val launcherPresentation = item ?: return
		val launcherPresentationDTO = this.mapLauncherPresentation(launcherPresentation)
		this.db.launcherPresentationDAO().insertAll(launcherPresentationDTO)
	}

	// endregion
	// region Private Properties

	private var db: LocalDatabase

	// endregion
	// region Private Methods

	private fun mapLauncherPresentation(presentation: LauncherPresentationVO): LauncherPresentationDTO {
		val dto = LauncherPresentationDTO()
		dto.identifier = UUID.randomUUID().toString()
		dto.actionTime = presentation.actionTime
		dto.launchTime = presentation.launchTime
		dto.result = presentation.result?.name
		return dto
	}


	private fun map(presentationDTOs: List<LauncherPresentationDTO>): List<LauncherPresentationVO> {
		return presentationDTOs.map { dto ->
			val vo = LauncherPresentationVO()
			vo.actionTime = dto.actionTime
			vo.launchTime = dto.launchTime
			dto.result.let {
				vo.result = LauncherPresentationResult.valueOf(it!!)
			}
			vo
		}
	}

	// endregion

}
