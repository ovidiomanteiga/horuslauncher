
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*
import org.paradaise.horussense.launcher.R


class StatsActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_stats)
		this.setSupportActionBar(this.toolbar)
		this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
		this.toolbar.title = this.getString(R.string.title_activity_stats)
	}

}
