
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
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.composition.MainFactory
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.domain.action.HorusAction
import org.paradaise.horussense.launcher.domain.action_execution.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.all_actions.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.horus_list.GetHorusListInteractor
import org.paradaise.horussense.launcher.domain.horus_list.HorusListItem
import org.paradaise.horussense.launcher.domain.promoted_actions.ExecutePromotedActionInteractor
import org.paradaise.horussense.launcher.domain.promoted_actions.GetPromotedActionsInteractor
import org.paradaise.horussense.launcher.domain.stats.DeviceLockingInteractor
import org.paradaise.horussense.launcher.domain.stats.GetStatsInteractor
import org.paradaise.horussense.launcher.ui.main.MainActivity


/**
 * UI test to check the main activity of the launcher.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

	// region Properties

	private lateinit var appContext: Context
	private lateinit var device: UiDevice

	private val appPackageName: String
		get() = this.appContext.packageName

	private val allActionsTabSelector: UiSelector
		get() {
			val text = this.appContext.getString(R.string.tab_text_all_actions)
			return UiSelector().text(text.toUpperCase())
		}
	private val horusListTabSelector: UiSelector
		get() {
			val text = this.appContext.getString(R.string.tab_text_horus_list)
			return UiSelector().text(text.toUpperCase())
		}

	// endregion
	// region Setup

	@get:Rule
	var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

	@Mock
	private lateinit var factory: MainFactory
	@Mock
	private lateinit var getAllActionInteractor: GetAllActionsInteractor
	@Mock
	private lateinit var deviceLockingInteractor: DeviceLockingInteractor
	@Mock
	private lateinit var executeActionInteractor: ExecuteActionInteractor
	@Mock
	private lateinit var executePromotedActionInteractor: ExecutePromotedActionInteractor
	@Mock
	private lateinit var getStatsInteractor: GetStatsInteractor
	@Mock
	private lateinit var getHorusListInteractor: GetHorusListInteractor
	@Mock
	private lateinit var getPromotedActionsInteractor: GetPromotedActionsInteractor
	@Mock
	private lateinit var action1: HorusListItem


	@Before
	fun setup() {
		this.device = UiDevice.getInstance(getInstrumentation())
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
		`when`(this.factory.provideExecutePromotedActionInteractor())
				.thenReturn(this.executePromotedActionInteractor)
		`when`(this.factory.provideGetPromotedActionsInteractor())
				.thenReturn(this.getPromotedActionsInteractor)
		`when`(this.factory.provideDeviceLockingInteractor())
				.thenReturn(this.deviceLockingInteractor)
		`when`(this.factory.provideGetStatsInteractor())
				.thenReturn(this.getStatsInteractor)
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
		this.activityRule.launchActivity(null)
		this.device.waitForIdle()
		val horusListTab = this.device.findObject(this.horusListTabSelector)
		val allActionsTab = this.device.findObject(this.allActionsTabSelector)
		assertTrue(horusListTab.exists())
		assertTrue(allActionsTab.exists())
		assertTrue(horusListTab.isSelected)
		assertFalse(allActionsTab.isSelected)
	}


	@Test
	fun testSwitchFromHorusListToAllActions() {
		this.activityRule.launchActivity(null)
		this.device.waitForIdle()
		val horusListTab = this.device.findObject(this.horusListTabSelector)
		val allActionsTab = this.device.findObject(this.allActionsTabSelector)
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
		this.activityRule.launchActivity(null)
		this.device.waitForIdle()
		val horusListTab = this.device.findObject(this.horusListTabSelector)
		val allActionsTab = this.device.findObject(this.allActionsTabSelector)
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
		this.activityRule.launchActivity(null)
		val horusListTab = this.device.findObject(this.horusListTabSelector)
		val allActionsTab = this.device.findObject(this.allActionsTabSelector)
		assertTrue(horusListTab.exists())
		assertTrue(allActionsTab.exists())
		this.device.waitForIdle()
		assertFalse(horusListTab.isSelected)
		assertTrue(allActionsTab.isSelected)
	}

	@Test
	fun testOpenStats() {
		this.activityRule.launchActivity(null)
		val actionStatsRID = this.appPackageName + ":id/action_stats"
		val actionStatsSelector = UiSelector().resourceId(actionStatsRID)
		val actionStatsButton = this.device.findObject(actionStatsSelector)
		assertTrue(actionStatsButton.exists())
		actionStatsButton.clickAndWaitForNewWindow()
		val statsTitle = this.appContext.getString(R.string.title_activity_stats)
		val title = this.device.findObject(UiSelector().textMatches(statsTitle))
		assertTrue(title.exists())
	}

	// endregion

}
