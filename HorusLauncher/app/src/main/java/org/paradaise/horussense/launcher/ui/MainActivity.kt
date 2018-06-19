
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.paradaise.horussense.launcher.R


class MainActivity : AppCompatActivity() {

	// region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.setSupportActionBar(toolbar)
    }


    override fun onBackPressed() { }

	// endregion

}

