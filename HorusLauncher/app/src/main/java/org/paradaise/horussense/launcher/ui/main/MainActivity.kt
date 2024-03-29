
package org.paradaise.horussense.launcher.ui.main

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsDeviceLockingInteractor
import org.paradaise.horussense.launcher.composition.NeedsGetPromotedActionsInteractor
import org.paradaise.horussense.launcher.domain.stats.DeviceLockingInteractor
import org.paradaise.horussense.launcher.domain.promoted_actions.GetPromotedActionsInteractor
import android.view.MenuItem
import org.paradaise.horussense.launcher.ui.all_actions.AllActionsFragment
import org.paradaise.horussense.launcher.ui.horus_list.HorusListFragmentListener
import org.paradaise.horussense.launcher.ui.horus_list.HorusListPromotedFragment
import org.paradaise.horussense.launcher.ui.stats.SendStatsScheduler
import org.paradaise.horussense.launcher.ui.stats.StatsActivity


class MainActivity : AppCompatActivity(), HorusListFragmentListener,
		NeedsGetPromotedActionsInteractor, NeedsDeviceLockingInteractor
{

	// region Injected Properties

	override lateinit var deviceLockingInteractor: DeviceLockingInteractor
	override lateinit var getPromotedActionsInteractor: GetPromotedActionsInteractor

	// endregion
	// region Lifecycle

	override fun onCreate(savedInstanceState: Bundle?) {
		MainInjector.inject(this)
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_main)
		this.setSupportActionBar(this.toolbar)
		this.sectionsPagerAdapter = SectionsPagerAdapter(this.supportFragmentManager)
		this.container.adapter = this.sectionsPagerAdapter
		this.container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(this.tabs))
		this.tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(this.container))
	}

	override fun onStart() {
		super.onStart()
		AsyncTask.execute {
			this.deviceLockingInteractor.locking = false
			this.deviceLockingInteractor.perform()
		}
		AsyncTask.execute {
			this.sendStatsScheduler.scheduleIfNeeded()
		}
	}

	override fun onStop() {
		super.onStop()
		AsyncTask.execute {
			this.deviceLockingInteractor.locking = true
			this.deviceLockingInteractor.perform()
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		this.menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return when (item?.itemId) {
			R.id.action_stats -> {
				this.showStatsActivity()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
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
	private var sectionsPagerAdapter: SectionsPagerAdapter? = null
	private val sendStatsScheduler: SendStatsScheduler
		get() = SendStatsScheduler(this)

	// endregion
	// region Private Methods

	private fun showStatsActivity() {
		val statsIntent = Intent(this, StatsActivity::class.java)
		this.startActivity(statsIntent)
	}

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
