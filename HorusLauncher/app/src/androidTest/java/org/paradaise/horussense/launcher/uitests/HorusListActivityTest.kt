
package org.paradaise.horussense.launcher.uitests

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import org.paradaise.horussense.launcher.ui.HorusListActivity
import java.util.*


/**
 * UI test to check Horus list presentation.
 */
@RunWith(AndroidJUnit4::class)
class HorusListActivityTest {

	// region Properties

	private lateinit var mDevice: UiDevice
	private lateinit var appContext: Context
	private val appPackageName: String
		get() = this.appContext.packageName

	@Mock
	private lateinit var factory: MainFactory
	@Mock
	private lateinit var executeActionInteractor: ExecuteActionInteractor
	@Mock
	private lateinit var getHorusListInteractor: GetHorusListInteractor
	@Mock
	private lateinit var action1: HorusListItem
	@Mock
	private lateinit var action2: HorusListItem
	@Mock
	private lateinit var action3: HorusListItem
	@Mock
	private lateinit var action4: HorusListItem

	// endregion
	// region Setup

	@get:Rule
	var mActivityRule = ActivityTestRule(HorusListActivity::class.java)


	init {
		MockitoAnnotations.initMocks(this)
		MainInjector.setFactory(this.factory)
		val now = Calendar.getInstance().time
		val yesterdayCalendar = Calendar.getInstance()
		yesterdayCalendar.add(Calendar.DATE, -1)
		val yesterday = yesterdayCalendar.time
		`when`(this.action1.name).thenReturn("Calendar")
		`when`(this.action1.numberOfExecutionsLastWeek).thenReturn(3)
		`when`(this.action2.name).thenReturn("Phone")
		`when`(this.action2.numberOfExecutionsLastWeek).thenReturn(2)
		`when`(this.action2.lastExecutionMoment).thenReturn(yesterday)
		`when`(this.action3.name).thenReturn("Facebook")
		`when`(this.action3.numberOfExecutionsLastWeek).thenReturn(1)
		`when`(this.action4.name).thenReturn("Photos")
		`when`(this.action4.numberOfExecutionsLastWeek).thenReturn(2)
		`when`(this.action4.lastExecutionMoment).thenReturn(now)
		val horusList = listOf(this.action1, this.action4, this.action2, this.action3)
		doNothing().`when`(this.getHorusListInteractor).perform()
		`when`(this.getHorusListInteractor.horusList).thenReturn(horusList)
		`when`(this.executeActionInteractor.perform()).then {
			val intent = Intent(Intent.ACTION_VIEW,
					Uri.parse("http://paradaise.org"))
			this.appContext.startActivity(intent)
		}
		`when`(this.factory.provideGetHorusListInteractor()).thenReturn(this.getHorusListInteractor)
		`when`(this.factory.provideExecuteActionInteractor())
				.thenReturn(this.executeActionInteractor)
	}


	@Before
	fun setup() {
		this.mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
		this.appContext = InstrumentationRegistry.getTargetContext()
		MainInjector.setFactory(this.factory)
	}


	@After
	fun tearDown() {
		MainInjector.resetFactory()
	}

	// endregion
	// region Tests

	@Test
	fun testSomePredictedApps() {
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val horusItemRID = this.appPackageName + ":id/horusItem"
		val textViewRID = this.appPackageName + ":id/titleView"
		val count = items.childCount
		val item0 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 0)
		val item0Title = item0.getChild(UiSelector().resourceId(textViewRID))
		val item1 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 1)
		val item1Title = item1.getChild(UiSelector().resourceId(textViewRID))
		assertTrue(count > 0)
		assertEquals("Calendar", item0Title.text)
		assertEquals("Photos", item1Title.text)
	}


	@Test
	fun testAppNotLaunched() {
		Assert.assertEquals(this.appPackageName, this.mDevice.currentPackageName)
	}


	@Test
	fun testLaunchApp() {
		val resourceId = this.appPackageName + ":id/horusItem"
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val item = items.getChildByText(UiSelector().resourceId(resourceId), "Calendar")
		item.click()
		this.mDevice.waitForIdle()
		Assert.assertNotEquals(this.appPackageName, this.mDevice.currentPackageName)
	}


	@Test
	fun testListOrdering() {
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val horusItemRID = this.appPackageName + ":id/horusItem"
		val textViewRID = this.appPackageName + ":id/titleView"
		val item0 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 0)
		val item0Title = item0.getChild(UiSelector().resourceId(textViewRID))
		val item1 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 1)
		val item1Title = item1.getChild(UiSelector().resourceId(textViewRID))
		val item2 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 2)
		val item2Title = item2.getChild(UiSelector().resourceId(textViewRID))
		val item3 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 3)
		val item3Title = item3.getChild(UiSelector().resourceId(textViewRID))
		assertEquals("Calendar", item0Title.text)
		assertEquals("Photos", item1Title.text)
		assertEquals("Phone", item2Title.text)
		assertEquals("Facebook", item3Title.text)
	}

	// endregion

}
