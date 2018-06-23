
package org.paradaise.horussense.launcher.uitests

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.paradaise.horussense.launcher.ui.MainActivity


/**
 * UI test to check the main activity of the launcher.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

	// region Properties

	private lateinit var appContext: Context
	private lateinit var mDevice: UiDevice

	private val appPackageName: String
		get() = this.appContext.packageName

	private val allActionsTabSelector =
			UiSelector().text("ALL ACTIONS")
	private val horusListTabSelector =
			UiSelector().text("HORUS LIST")

	// endregion
	// region Setup

	@get:Rule
	var mActivityRule = ActivityTestRule(MainActivity::class.java)


	@Before
	fun setup() {
		this.mDevice = UiDevice.getInstance(getInstrumentation())
		this.appContext = InstrumentationRegistry.getTargetContext()
	}

	// endregion
	// region Tests

	@Test
	fun testHorusListFirst() {
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		assertTrue(horusListTab.exists())
	}


	@Test
	fun testSwitchFromHorusListToAllActions() {
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		assertTrue(horusListTab.exists())
		horusListTab.click()
		val allActionsTab = this.mDevice.findObject(this.allActionsTabSelector)
		assertTrue(allActionsTab.exists())
	}


	@Test
	fun testSwitchFromHorusListToAllActionsAndBack() {
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		assertTrue(horusListTab.exists())
		horusListTab.click()
		val allActionsTab = this.mDevice.findObject(this.allActionsTabSelector)
		assertTrue(allActionsTab.exists())
		allActionsTab.click()
		val horusListTab2 = this.mDevice.findObject(this.horusListTabSelector)
		assertTrue(horusListTab2.exists())
	}

	// endregion

}
