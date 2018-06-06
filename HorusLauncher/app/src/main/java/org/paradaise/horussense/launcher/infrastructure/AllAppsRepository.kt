
package org.paradaise.horussense.launcher.infrastructure


import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import org.paradaise.horussense.launcher.domain.*
import android.content.Intent



open class AllAppsRepository: AllActionsRepository {

	// region Lifecycle

	constructor(context: Context?) {
		this.context = context
	}

	// endregion
	// region AllActionsRepository Override

	override fun get(): AllActions {
		val packageManager = this.context?.packageManager ?: return listOf()
		val apps = this.getAllInstalledApps(packageManager)
		return this.mapToApps(apps, packageManager)
	}

	// endregion
	// region Private Properties

	private var context: Context?

	// endregion
	// region Private Methods

	private fun getAllInstalledApps(packageManager: PackageManager): List<ApplicationInfo> {
		val main = Intent(Intent.ACTION_MAIN, null)
		main.addCategory(Intent.CATEGORY_LAUNCHER)
		val packages = packageManager.queryIntentActivities(main, 0)
		return packages.map { resolveInfo ->
				val packageName = resolveInfo.activityInfo.packageName
				packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
		}
	}


	private fun mapToApps(packages: List<ApplicationInfo>,
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
