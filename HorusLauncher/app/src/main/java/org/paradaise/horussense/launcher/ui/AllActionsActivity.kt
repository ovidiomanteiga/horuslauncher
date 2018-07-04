
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*
import org.paradaise.horussense.launcher.R


open class AllActionsActivity : AppCompatActivity() {

	// region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_all_actions)
	    this.setSupportActionBar(this.toolbar)
    }


	override fun onBackPressed() { }

	// endregion

}

