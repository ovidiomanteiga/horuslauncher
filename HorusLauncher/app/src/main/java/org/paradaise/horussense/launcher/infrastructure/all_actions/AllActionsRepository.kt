
package org.paradaise.horussense.launcher.infrastructure.all_actions

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.Intent
import org.paradaise.horussense.launcher.domain.all_actions.AllActions
import org.paradaise.horussense.launcher.domain.all_actions.AllActionsRepository
import org.paradaise.horussense.launcher.infrastructure.action.OpenAppAction


open class AllAppsRepository: AllActionsRepository {

	// region Lifecycle

	constructor(context: Context?) {
		this.context = context
	}

	// endregion
	// region AllActionsRepository Override

	override fun get(): AllActions {
		val packageManager = this.context?.packageManager ?: return listOf()
		var apps = this.getAllInstalledApps(packageManager)
		apps = this.filterThisApp(apps)
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
	                      packageManager: PackageManager): List<OpenAppAction> {
		return packages.map {
			val icon = it.loadIcon(packageManager)
			val name = it.loadLabel(packageManager)?.toString() ?: it.packageName
			val intent = packageManager.getLaunchIntentForPackage(it.packageName)
			OpenAppAction(icon = icon, intent = intent, name = name)
		}
	}

	
	private fun filterThisApp(apps: List<ApplicationInfo>): List<ApplicationInfo> {
		val packageName = this.context?.packageName ?: return apps
		return apps.filter { it.packageName != packageName }
	}

	// endregion

}
