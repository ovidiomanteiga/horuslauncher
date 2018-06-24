
package org.paradaise.horussense.launcher.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.paradaise.horussense.launcher.R


class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.setContentView(R.layout.activity_main)
		this.setSupportActionBar(this.toolbar)
		this.mSectionsPagerAdapter = SectionsPagerAdapter(this.supportFragmentManager)
		container.adapter = this.mSectionsPagerAdapter
		container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
		tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
	}


	private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


	inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

		override fun getItem(position: Int): Fragment =
				if (position == 0) HorusListFragment() else AllActionsFragment()

		override fun getCount(): Int = 2

	}

}
