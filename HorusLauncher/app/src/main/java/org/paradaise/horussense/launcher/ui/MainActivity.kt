
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsGetPromotedActionsInteractor
import org.paradaise.horussense.launcher.domain.GetPromotedActions


class MainActivity : AppCompatActivity(), HorusListFragmentListener,
		NeedsGetPromotedActionsInteractor
{

	// region Injected Properties

	override lateinit var getPromotedActionsInteractor: GetPromotedActions

	// endregion
	// region Lifecycle

	override fun onCreate(savedInstanceState: Bundle?) {
		MainInjector.inject(this)
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_main)
		this.setSupportActionBar(this.toolbar)
		this.mSectionsPagerAdapter = SectionsPagerAdapter(this.supportFragmentManager)
		this.container.adapter = this.mSectionsPagerAdapter
		this.container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(this.tabs))
		this.tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(this.container))
	}


	override fun onBackPressed() { }

	// endregion
	// region HorusListFragmentListener Implementation

	override fun onEmptyHorusList() {
		this.tabs.getTabAt(this.allActionsTabIndex)?.select()
		val message = getString(R.string.empty_horus_list)
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}

	// endregion
	// region Private Properties

	private val allActionsTabIndex: Int = 1
	private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

	// endregion
	// region Inner Classes

	inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

		override fun getItem(position: Int): Fragment =
				if (position == this@MainActivity.allActionsTabIndex)
					AllActionsFragment()
				else
					HorusListPromotedFragment()

		override fun getCount(): Int = 2

	}

	// endregion

}
