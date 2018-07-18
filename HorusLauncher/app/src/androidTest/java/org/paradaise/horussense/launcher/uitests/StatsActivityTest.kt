
package org.paradaise.horussense.launcher.uitests

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import android.support.v7.widget.RecyclerView
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.composition.MainFactory
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.domain.*
import org.paradaise.horussense.launcher.ui.StatsActivity
import java.util.*


/**
 * UI test to check stats presentation.
 */
@RunWith(AndroidJUnit4::class)
class StatsActivityTest {

	// region Properties

	private lateinit var context: Context
	private lateinit var device: UiDevice

	private val appPackageName: String
		get() = this.context.packageName

	@Mock
	private lateinit var factory: MainFactory
	@Mock
	private lateinit var getStatsInteractor: GetStatsInteractor
	@Mock
	private lateinit var stats: LauncherStatsVO

	// endregion
	// region Setup

	private val activityClass = StatsActivity::class.java

	@get:Rule
	var activityRule = ActivityTestRule(this.activityClass, true, false)


	@Before
	fun setup() {
		MockitoAnnotations.initMocks(this)
		MainInjector.setFactory(this.factory)
		val now = Calendar.getInstance().time
		`when`(this.stats.horusListAccuracy).thenReturn(44.4)
		`when`(this.stats.averageActionTime).thenReturn(4.4)
		`when`(this.stats.averageTimeGettingHorusList).thenReturn(444)
		`when`(this.getStatsInteractor.stats).thenReturn(this.stats)
		`when`(this.factory.provideGetStatsInteractor())
				.thenReturn(this.getStatsInteractor)
		this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
		this.context = InstrumentationRegistry.getTargetContext()
	}


	@After
	fun tearDown() {
		MainInjector.resetFactory()
	}

	// endregion
	// region Tests

	@Test
	fun testValuesAreShown() {
		// Arrange
		this.activityRule.launchActivity(null)
		// Act
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val statsItem = this.appPackageName + ":id/statsItem"
		val valueViewRID = this.appPackageName + ":id/valueView"
		val count = items.childCount
		val item0 = items.getChildByInstance(UiSelector().resourceId(statsItem), 0)
		val item0Value = item0.getChild(UiSelector().resourceId(valueViewRID))
		val item1 = items.getChildByInstance(UiSelector().resourceId(statsItem), 1)
		val item1Value = item1.getChild(UiSelector().resourceId(valueViewRID))
		val item2 = items.getChildByInstance(UiSelector().resourceId(statsItem), 2)
		val item2Value = item2.getChild(UiSelector().resourceId(valueViewRID))
		// Assert
		assertEquals(3, count)
		assertEquals("44.4", item0Value.text)
		assertEquals("4.4", item1Value.text)
		assertEquals("444", item2Value.text)
	}

	@Test
	fun testLabelsAreShown() {
		// Arrange
		this.activityRule.launchActivity(null)
		// Act
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val statsItem = this.appPackageName + ":id/statsItem"
		val titleViewRID = this.appPackageName + ":id/titleView"
		val count = items.childCount
		val item0 = items.getChildByInstance(UiSelector().resourceId(statsItem), 0)
		val item0Value = item0.getChild(UiSelector().resourceId(titleViewRID))
		val item1 = items.getChildByInstance(UiSelector().resourceId(statsItem), 1)
		val item1Value = item1.getChild(UiSelector().resourceId(titleViewRID))
		val item2 = items.getChildByInstance(UiSelector().resourceId(statsItem), 2)
		val item2Value = item2.getChild(UiSelector().resourceId(titleViewRID))
		// Assert
		val item0expected = this.context.getString(R.string.stats_horus_list_accuracy_title)
		val item1expected = this.context.getString(R.string.stats_average_action_time_title)
		val item2expected = this.context.getString(R.string.stats_average_time_getting_horus_list_title)
		assertEquals(3, count)
		assertEquals(item0expected, item0Value.text)
		assertEquals(item1expected, item1Value.text)
		assertEquals(item2expected, item2Value.text)
	}

	// endregion

}
