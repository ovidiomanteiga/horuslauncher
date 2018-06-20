
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.paradaise.horussense.launcher.R


open class HorusListActivity : AppCompatActivity() {

	// region Lifecycle

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_horus_list)
	}


	override fun onBackPressed() { }

	// endregion

}
