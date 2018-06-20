
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.paradaise.horussense.launcher.R


open class AllAppsActivity : AppCompatActivity() {

	// region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_all_apps)
    }


	override fun onBackPressed() { }

	// endregion

}

