package org.paradaise.horussense.launcher.infrastructure


import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import org.paradaise.horussense.launcher.domain.*



open class AllAppsRepository: AllActionsRepository {

	// region Lifecycle

	constructor(context: Context?) {
		this.context = context
	}

	// endregion
	// region AllActionsRepository Override

	override fun get(): AllActions {
		val packageManager = this.context?.packageManager ?: return listOf()
		val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
		return this.mapToApps(packages, packageManager)
	}

	// endregion
	// region Private Properties

	private var context: Context?

	// endregion
	// region Private Methods

	private fun mapToApps(packages: MutableList<ApplicationInfo>,
	                      packageManager: PackageManager): List<App> {
		return packages.map {
			val icon = it.loadIcon(packageManager)
			var name = it.loadLabel(packageManager)?.toString() ?: it.packageName
			val intent = packageManager.getLaunchIntentForPackage(name)
			App(icon = icon, intent = intent, name = name)
		}
	}

	// endregion

}
