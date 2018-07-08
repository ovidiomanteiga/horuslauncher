
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import org.paradaise.horussense.launcher.R


class HorusListPromotedActivity: HorusListActivity() {

	// region Lifecycle

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_horus_list)
		this.setSupportActionBar(this.toolbar)
	}


	override fun onBackPressed() { }

	// endregion

}
