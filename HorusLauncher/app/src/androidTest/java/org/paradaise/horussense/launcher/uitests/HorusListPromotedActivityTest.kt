
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
import junit.framework.Assert.assertTrue
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations
import org.paradaise.horussense.launcher.composition.MainFactory
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.domain.*
import org.paradaise.horussense.launcher.ui.HorusListPromotedActivity
import java.util.*


/**
 * UI test to check Horus List with promoted actions presentation.
 */
@RunWith(AndroidJUnit4::class)
class HorusListPromotedActivityTest {

	// region Properties

	private lateinit var device: UiDevice
	private lateinit var context: Context
	private val appPackageName: String
		get() = this.context.packageName

	@Mock
	private lateinit var factory: MainFactory
	@Mock
	private lateinit var executeActionInteractor: ExecuteActionInteractor
	@Mock
	private lateinit var getHorusListInteractor: GetHorusListInteractor
	@Mock
	private lateinit var predicted1: PredictedHorusListItem
	@Mock
	private lateinit var predicted2: PredictedHorusListItem
	@Mock
	private lateinit var predicted3: PredictedHorusListItem
	@Mock
	private lateinit var promoted: PromotedHorusListItem

	// endregion
	// region Setup

	private val activityClass = HorusListPromotedActivity::class.java

	@get:Rule
	var activityRule = ActivityTestRule(this.activityClass, true, false)


	@Before
	fun setup() {
		MockitoAnnotations.initMocks(this)
		MainInjector.setFactory(this.factory)
		val now = Calendar.getInstance().time
		`when`(this.predicted1.name).thenReturn("Calendar")
		`when`(this.predicted1.numberOfExecutionsLastWeek).thenReturn(3)
		`when`(this.predicted2.name).thenReturn("Phone")
		`when`(this.predicted2.numberOfExecutionsLastWeek).thenReturn(2)
		`when`(this.predicted2.lastExecutionMoment).thenReturn(now)
		`when`(this.predicted3.name).thenReturn("Facebook")
		`when`(this.predicted3.numberOfExecutionsLastWeek).thenReturn(1)
		`when`(this.promoted.name).thenReturn("English")
		`when`(this.promoted.numberOfExecutionsLastWeek).thenReturn(2)
		`when`(this.promoted.lastExecutionMoment).thenReturn(now)
		doNothing().`when`(this.getHorusListInteractor).perform()
		`when`(this.factory.provideGetHorusListInteractor()).thenReturn(this.getHorusListInteractor)
		`when`(this.factory.provideExecuteActionInteractor())
				.thenReturn(this.executeActionInteractor)
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
	fun testSomePredictedActionsNoPromoted() {
		// Arrange
		val horusList = listOf(this.predicted1, this.predicted2, this.predicted3)
		`when`(this.getHorusListInteractor.horusList).thenReturn(horusList)
		this.activityRule.launchActivity(null)
		// Act
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val horusItemRID = this.appPackageName + ":id/horusItem"
		val textViewRID = this.appPackageName + ":id/titleView"
		val count = items.childCount
		val item0 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 0)
		val item0Title = item0.getChild(UiSelector().resourceId(textViewRID))
		val item1 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 1)
		val item1Title = item1.getChild(UiSelector().resourceId(textViewRID))
		// Assert
		assertTrue(count > 0)
		assertEquals("Calendar", item0Title.text)
		assertEquals("Phone", item1Title.text)
	}


	@Test
	fun testSomePredictedActionsAndSomePromoted() {
		// Arrange
		val horusList = listOf(this.predicted1, this.promoted, this.predicted2, this.predicted3)
		`when`(this.getHorusListInteractor.horusList).thenReturn(horusList)
		this.activityRule.launchActivity(null)
		// Act
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val horusItemRID = this.appPackageName + ":id/horusItem"
		val textViewRID = this.appPackageName + ":id/titleView"
		val count = items.childCount
		val horusItemSelector = UiSelector().resourceId(horusItemRID)
		val textViewSelector = UiSelector().resourceId(textViewRID)
		var actualItems = listOf<String>()
		for (i in 0..(count-1)) {
			val item = items.getChildByInstance(horusItemSelector, i)
			val itemTitle = item.getChild(textViewSelector)
			actualItems += itemTitle.text
		}
		// Assert
		assertTrue(count > 0)
		assertTrue(actualItems.contains("Calendar"))
		assertTrue(actualItems.contains("Phone"))
		assertTrue(actualItems.contains("Facebook"))
		assertTrue(actualItems.contains("English"))
	}

	// endregion

}
