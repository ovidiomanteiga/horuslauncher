package org.paradaise.horussense.launcher


import android.content.Context
import android.content.pm.PackageManager



class AllAppsService {

	constructor(context: Context) {
		this.context = context
	}


	fun get(): List<App> {
		val packageManager = this.context.packageManager
		val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
		return packages.map { it.packageName }
	}


	private var context: Context

}
