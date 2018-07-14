
package org.paradaise.horussense.launcher.uitests

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations
import org.paradaise.horussense.launcher.composition.MainFactory
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.domain.*
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
	var mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)

	@Mock
	private lateinit var factory: MainFactory
	@Mock
	private lateinit var getAllActionInteractor: GetAllActionsInteractor
	@Mock
	private lateinit var executeActionInteractor: ExecuteActionInteractor
	@Mock
	private lateinit var getHorusListInteractor: GetHorusListInteractor
	@Mock
	private lateinit var getPromotedActionsInteractor: GetPromotedActionsInteractor
	@Mock
	private lateinit var action1: HorusListItem


	@Before
	fun setup() {
		this.mDevice = UiDevice.getInstance(getInstrumentation())
		this.appContext = InstrumentationRegistry.getTargetContext()
		MockitoAnnotations.initMocks(this)
		MainInjector.setFactory(this.factory)
		`when`(this.action1.name).thenReturn("Calendar")
		`when`(this.action1.numberOfExecutionsLastWeek).thenReturn(3)
		val allActions = listOf<HorusAction>()
		val horusList = listOf(this.action1)
		`when`(this.factory.provideGetAllActionsInteractor())
				.thenReturn(this.getAllActionInteractor)
		`when`(this.factory.provideGetHorusListInteractor())
				.thenReturn(this.getHorusListInteractor)
		`when`(this.factory.provideExecuteActionInteractor())
				.thenReturn(this.executeActionInteractor)
		`when`(this.factory.provideGetPromotedActionsInteractor())
				.thenReturn(this.getPromotedActionsInteractor)
		doNothing().`when`(this.getAllActionInteractor).perform()
		`when`(this.getAllActionInteractor.allActions).thenReturn(allActions)
		doNothing().`when`(this.getHorusListInteractor).perform()
		`when`(this.getHorusListInteractor.horusList).thenReturn(horusList)
		doNothing().`when`(this.executeActionInteractor).perform()
	}

	@After
	fun tearDown() {
		MainInjector.resetFactory()
	}

	// endregion
	// region Tests

	@Test
	fun testHorusListFirst() {
		this.mActivityRule.launchActivity(null)
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		val allActionsTab = this.mDevice.findObject(this.allActionsTabSelector)
		assertTrue(horusListTab.exists())
		assertTrue(allActionsTab.exists())
		assertTrue(horusListTab.isSelected)
		assertFalse(allActionsTab.isSelected)
	}


	@Test
	fun testSwitchFromHorusListToAllActions() {
		this.mActivityRule.launchActivity(null)
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		val allActionsTab = this.mDevice.findObject(this.allActionsTabSelector)
		assertTrue(horusListTab.exists())
		assertTrue(allActionsTab.exists())
		assertTrue(horusListTab.isSelected)
		assertFalse(allActionsTab.isSelected)
		allActionsTab.click()
		assertFalse(horusListTab.isSelected)
		assertTrue(allActionsTab.isSelected)
	}


	@Test
	fun testSwitchFromHorusListToAllActionsAndBack() {
		this.mActivityRule.launchActivity(null)
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		val allActionsTab = this.mDevice.findObject(this.allActionsTabSelector)
		assertTrue(horusListTab.exists())
		assertTrue(allActionsTab.exists())
		assertTrue(horusListTab.isSelected)
		allActionsTab.click()
		assertTrue(allActionsTab.isSelected)
		horusListTab.click()
		assertTrue(horusListTab.isSelected)
	}

	@Test
	fun testSwitchToAllActionsIfEmptyHorusList() {
		val emptyHorusList = listOf<HorusListItem>()
		`when`(this.getHorusListInteractor.horusList).thenReturn(emptyHorusList)
		this.mActivityRule.launchActivity(null)
		val horusListTab = this.mDevice.findObject(this.horusListTabSelector)
		val allActionsTab = this.mDevice.findObject(this.allActionsTabSelector)
		assertTrue(horusListTab.exists())
		assertTrue(allActionsTab.exists())
		this.mDevice.waitForIdle()
		assertFalse(horusListTab.isSelected)
		assertTrue(allActionsTab.isSelected)
	}

	// endregion

}
